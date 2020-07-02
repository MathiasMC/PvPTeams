package me.MathiasMC.PvPTeams.data;

import me.MathiasMC.PvPTeams.PvPTeams;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;

public class Database {

    private final PvPTeams plugin;

    private Connection connection;
    private final boolean debug_database;

    public Database(final PvPTeams plugin) {
        this.plugin = plugin;
        debug_database = plugin.config.get.getBoolean("debug.database");
        (new BukkitRunnable() {
            @Override
            public void run() {
                try {
                    if (connection != null && !connection.isClosed()) {
                        connection.createStatement().execute("SELECT 1");
                        if (debug_database) { plugin.textUtils.debug("[Database] connection is still valid"); }
                    }
                } catch (SQLException e) {
                    connection = get();
                    if (debug_database) { plugin.textUtils.debug("[Database] connection is not valid creating new"); }
                }
            }
        }).runTaskTimerAsynchronously(plugin, 60 * 20, 60 * 20);
    }

    private Connection get() {
        try {
            if (plugin.config.get.getBoolean("mysql.use")) {
                Class.forName("com.mysql.jdbc.Driver");
                if (debug_database) { plugin.textUtils.debug("[Database] Getting connection (MySQL)"); }
                return DriverManager.getConnection("jdbc:mysql://" + plugin.config.get.getString("mysql.host") + ":" + plugin.config.get.getString("mysql.port") + "/" + plugin.config.get.getString("mysql.database"), plugin.config.get.getString("mysql.username"), plugin.config.get.getString("mysql.password"));
            } else {
                Class.forName("org.sqlite.JDBC");
                if (debug_database) { plugin.textUtils.debug("[Database] Getting connection (SQLite)"); }
                return DriverManager.getConnection("jdbc:sqlite:" + new File(plugin.getDataFolder(), "data.db"));
            }
        } catch (ClassNotFoundException | SQLException e) {
            plugin.textUtils.exception(e.getStackTrace(), e.getMessage());
            return null;
        }
    }

    public void close() throws SQLException {
        if (connection != null) {
            connection.close();
            if (debug_database) { plugin.textUtils.debug("[Database] Closing connection"); }
        }
    }

    private boolean check() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = get();
            if (connection == null || connection.isClosed()) {
                return false;
            }
            connection.createStatement().execute("CREATE TABLE IF NOT EXISTS `pvpteams_players` (`uuid` varchar(255) PRIMARY KEY, `team` longtext(255), `coins` bigint(255));");
            connection.createStatement().execute("CREATE TABLE IF NOT EXISTS `pvpteams_teams` (`team` varchar(255) PRIMARY KEY, `owner` varchar(255), `members` longtext(255), `max_members` bigint(255), `coins` bigint(255), `level` bigint(255), `quests` longtext(255));");
            if (debug_database) { plugin.textUtils.debug("[Database] Creating table if not exists"); }
        }
        return true;
    }

    public boolean set() {
        try {
            return check();
        } catch (SQLException e) {
            return false;
        }
    }


    public void insertPlayer(final String uuid) {
        if (set()) {
            BukkitRunnable r = new BukkitRunnable() {
                @Override
                public void run() {
                    PreparedStatement preparedStatement = null;
                    ResultSet resultSet = null;
                    try {
                        resultSet = connection.createStatement().executeQuery("SELECT * FROM pvpteams_players WHERE uuid= '" + uuid + "';");
                        if (!resultSet.next()) {
                            preparedStatement = connection.prepareStatement("INSERT INTO pvpteams_players (uuid, team, coins) VALUES(?, ?, ?);");
                            preparedStatement.setString(1, uuid);
                            preparedStatement.setString(2, "");
                            preparedStatement.setLong(3, 0L);
                            preparedStatement.executeUpdate();
                            if (debug_database) { plugin.textUtils.debug("[Database] Inserting default values for UUID: " + uuid); }
                        }
                    } catch (SQLException exception) {
                        plugin.textUtils.exception(exception.getStackTrace(), exception.getMessage());
                    } finally {
                        if (resultSet != null)
                            try {
                                resultSet.close();
                            } catch (SQLException exception) {
                                plugin.textUtils.exception(exception.getStackTrace(), exception.getMessage());
                            }
                        if (preparedStatement != null)
                            try {
                                preparedStatement.close();
                            } catch (SQLException exception) {
                                plugin.textUtils.exception(exception.getStackTrace(), exception.getMessage());
                            }
                    }
                }
            };
            r.runTaskAsynchronously(plugin);
        }
    }

    public void insertTeam(final String team, final String owner) {
        if (set()) {
            BukkitRunnable r = new BukkitRunnable() {
                @Override
                public void run() {
                    PreparedStatement preparedStatement = null;
                    ResultSet resultSet = null;
                    try {
                        resultSet = connection.createStatement().executeQuery("SELECT * FROM pvpteams_teams WHERE team= '" + team + "';");
                        if (!resultSet.next()) {
                            preparedStatement = connection.prepareStatement("INSERT INTO pvpteams_teams (team, owner, members, max_members, coins, level, quests) VALUES(?, ?, ?, ?, ?, ?, ?);");
                            preparedStatement.setString(1, team);
                            preparedStatement.setString(2, owner);
                            preparedStatement.setString(3, "");
                            preparedStatement.setLong(4, 1L);
                            preparedStatement.setLong(5, 0L);
                            preparedStatement.setLong(6, 0L);
                            preparedStatement.setString(7, "");
                            preparedStatement.executeUpdate();
                            plugin.loadTeam(team);
                            PlayerConnect playerConnect = plugin.getPlayer(owner);
                            playerConnect.setTeam(team);
                            playerConnect.save();
                            if (debug_database) { plugin.textUtils.debug("[Database] Inserting default values for Team: " + team); }
                        }
                    } catch (SQLException exception) {
                        plugin.textUtils.exception(exception.getStackTrace(), exception.getMessage());
                    } finally {
                        if (resultSet != null)
                            try {
                                resultSet.close();
                            } catch (SQLException exception) {
                                plugin.textUtils.exception(exception.getStackTrace(), exception.getMessage());
                            }
                        if (preparedStatement != null)
                            try {
                                preparedStatement.close();
                            } catch (SQLException exception) {
                                plugin.textUtils.exception(exception.getStackTrace(), exception.getMessage());
                            }
                    }
                }
            };
            r.runTaskAsynchronously(plugin);
        }
    }

    public void setValuesPlayers(final String uuid, String team, long coins) {
        if (set()) {
            BukkitRunnable r = new BukkitRunnable() {
                public void run() {
                    PreparedStatement preparedStatement = null;
                    ResultSet resultSet = null;
                    try {
                        resultSet = connection.createStatement().executeQuery("SELECT * FROM pvpteams_players WHERE uuid= '" + uuid + "';");
                        if (resultSet.next()) {
                            preparedStatement = connection.prepareStatement("UPDATE pvpteams_players SET team = ?, coins = ? WHERE uuid = ?");
                            preparedStatement.setString(1, team);
                            preparedStatement.setLong(2, coins);
                            preparedStatement.setString(3, uuid);
                            preparedStatement.executeUpdate();
                            if (debug_database) { plugin.textUtils.debug("[Database] Updating values for UUID: " + uuid); }
                        }
                    } catch (SQLException exception) {
                        plugin.textUtils.exception(exception.getStackTrace(), exception.getMessage());
                    } finally {
                        if (resultSet != null)
                            try {
                                resultSet.close();
                            } catch (SQLException exception) {
                                plugin.textUtils.exception(exception.getStackTrace(), exception.getMessage());
                            }
                        if (preparedStatement != null)
                            try {
                                preparedStatement.close();
                            } catch (SQLException exception) {
                                plugin.textUtils.exception(exception.getStackTrace(), exception.getMessage());
                            }
                    }
                }
            };
            r.runTaskAsynchronously(plugin);
        }
    }

    public void setValuesTeams(final String team, final String owner, String members, long max_members, long coins, long level, String quests) {
        if (set()) {
            BukkitRunnable r = new BukkitRunnable() {
                public void run() {
                    PreparedStatement preparedStatement = null;
                    ResultSet resultSet = null;
                    try {
                        resultSet = connection.createStatement().executeQuery("SELECT * FROM pvpteams_teams WHERE team= '" + team + "';");
                        if (resultSet.next()) {
                            preparedStatement = connection.prepareStatement("UPDATE pvpteams_teams SET team = ?, owner = ?, members = ?, max_members = ?, coins = ?, level = ?, quests = ? WHERE team = ?");
                            preparedStatement.setString(1, team);
                            preparedStatement.setString(2, owner);
                            preparedStatement.setString(3, members);
                            preparedStatement.setLong(4, max_members);
                            preparedStatement.setLong(5, coins);
                            preparedStatement.setLong(6, level);
                            preparedStatement.setString(7, quests);
                            preparedStatement.setString(8, team);
                            preparedStatement.executeUpdate();
                            if (debug_database) { plugin.textUtils.debug("[Database] Updating values for team: " + team); }
                        }
                    } catch (SQLException exception) {
                        plugin.textUtils.exception(exception.getStackTrace(), exception.getMessage());
                    } finally {
                        if (resultSet != null)
                            try {
                                resultSet.close();
                            } catch (SQLException exception) {
                                plugin.textUtils.exception(exception.getStackTrace(), exception.getMessage());
                            }
                        if (preparedStatement != null)
                            try {
                                preparedStatement.close();
                            } catch (SQLException exception) {
                                plugin.textUtils.exception(exception.getStackTrace(), exception.getMessage());
                            }
                    }
                }
            };
            r.runTaskAsynchronously(plugin);
        }
    }


    public void deleteTeam(final String team, final String owner) {
        if (set()) {
            BukkitRunnable r = new BukkitRunnable() {
                public void run() {
                    PreparedStatement preparedStatement = null;
                    ResultSet resultSet = null;
                    try {
                        resultSet = connection.createStatement().executeQuery("SELECT * FROM pvpteams_teams WHERE team= '" + team + "';");
                        if (resultSet.next()) {
                            preparedStatement = connection.prepareStatement("DELETE FROM pvpteams_teams WHERE team = ?");
                            preparedStatement.setString(1, team);
                            preparedStatement.executeUpdate();
                            plugin.unloadTeam(team);
                            PlayerConnect playerConnect = plugin.getPlayer(owner);
                            playerConnect.setTeam(null);
                            playerConnect.save();
                            if (debug_database) { plugin.textUtils.debug("[Database] Deleting values for team: " + team); }
                        }
                    } catch (SQLException exception) {
                        plugin.textUtils.exception(exception.getStackTrace(), exception.getMessage());
                    } finally {
                        if (resultSet != null)
                            try {
                                resultSet.close();
                            } catch (SQLException exception) {
                                plugin.textUtils.exception(exception.getStackTrace(), exception.getMessage());
                            }
                        if (preparedStatement != null)
                            try {
                                preparedStatement.close();
                            } catch (SQLException exception) {
                                plugin.textUtils.exception(exception.getStackTrace(), exception.getMessage());
                            }
                    }
                }
            };
            r.runTaskAsynchronously(plugin);
        }
    }

    public String[] getValuesPlayers(String uuid) {
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM pvpteams_players WHERE uuid= '" + uuid + "';");
            if (resultSet.next()) {
                if (debug_database) { plugin.textUtils.debug("[Database] Getting values for UUID: " + uuid); }
                return new String[]{ resultSet.getString("team"), String.valueOf(resultSet.getLong("coins")) };
            }
        } catch (SQLException exception) {
            plugin.textUtils.exception(exception.getStackTrace(), exception.getMessage());
        } finally {
            if (resultSet != null)
                try {
                    resultSet.close();
                } catch (SQLException exception) {
                    plugin.textUtils.exception(exception.getStackTrace(), exception.getMessage());
                }
            if (statement != null)
                try {
                    statement.close();
                } catch (SQLException exception) {
                    plugin.textUtils.exception(exception.getStackTrace(), exception.getMessage());
                }
        }
        return new String[] { "", String.valueOf(0L) };
    }

    public String[] getValuesTeams(String team) {
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM pvpteams_teams WHERE team= '" + team + "';");
            if (resultSet.next()) {
                if (debug_database) { plugin.textUtils.debug("[Database] Getting values for team: " + team); }
                return new String[]{ resultSet.getString("owner"), resultSet.getString("members"), resultSet.getString("max_members"), String.valueOf(resultSet.getLong("coins")), String.valueOf(resultSet.getLong("level")), resultSet.getString("quests") };
            }
        } catch (SQLException exception) {
            plugin.textUtils.exception(exception.getStackTrace(), exception.getMessage());
        } finally {
            if (resultSet != null)
                try {
                    resultSet.close();
                } catch (SQLException exception) {
                    plugin.textUtils.exception(exception.getStackTrace(), exception.getMessage());
                }
            if (statement != null)
                try {
                    statement.close();
                } catch (SQLException exception) {
                    plugin.textUtils.exception(exception.getStackTrace(), exception.getMessage());
                }
        }
        return new String[] { "", "", String.valueOf(0L), String.valueOf(0L), String.valueOf(0L), ""};
    }

    private ArrayList<String> getListPlayers() {
        if (set()) {
            ArrayList<String> array = new ArrayList<>();
            Statement statement = null;
            ResultSet resultSet = null;
            try {
                statement = connection.createStatement();
                resultSet = statement.executeQuery("SELECT * FROM pvpteams_players");
                while (resultSet.next()) {
                    array.add(resultSet.getString("uuid"));
                }
            } catch (SQLException exception) {
                plugin.textUtils.exception(exception.getStackTrace(), exception.getMessage());
            } finally {
                if (resultSet != null)
                    try {
                        resultSet.close();
                    } catch (SQLException exception) {
                        plugin.textUtils.exception(exception.getStackTrace(), exception.getMessage());
                    }
                if (statement != null)
                    try {
                        statement.close();
                    } catch (SQLException exception) {
                        plugin.textUtils.exception(exception.getStackTrace(), exception.getMessage());
                    }
            }
            if (debug_database) { plugin.textUtils.debug("[Database] Getting list of UUID in the table"); }
            return array;
        }
        return null;
    }

    private ArrayList<String> getListTeams() {
        if (set()) {
            ArrayList<String> array = new ArrayList<>();
            Statement statement = null;
            ResultSet resultSet = null;
            try {
                statement = connection.createStatement();
                resultSet = statement.executeQuery("SELECT * FROM pvpteams_teams");
                while (resultSet.next()) {
                    array.add(resultSet.getString("team"));
                }
            } catch (SQLException exception) {
                plugin.textUtils.exception(exception.getStackTrace(), exception.getMessage());
            } finally {
                if (resultSet != null)
                    try {
                        resultSet.close();
                    } catch (SQLException exception) {
                        plugin.textUtils.exception(exception.getStackTrace(), exception.getMessage());
                    }
                if (statement != null)
                    try {
                        statement.close();
                    } catch (SQLException exception) {
                        plugin.textUtils.exception(exception.getStackTrace(), exception.getMessage());
                    }
            }
            if (debug_database) { plugin.textUtils.debug("[Database] Getting list of team in the table"); }
            return array;
        }
        return null;
    }

    public void loadALL() {
        if (set()) {
            for (String list : getListPlayers()) {
                if (!plugin.listPlayers().contains(list))
                    plugin.loadPlayer(list);
            }
        }
        if (debug_database) { plugin.textUtils.debug("[Database] Loading all players into cache"); }
    }

    public void loadAllTeams() {
        if (set()) {
            for (String list : getListTeams()) {
                if (!plugin.listTeams().contains(list))
                    plugin.loadTeam(list);
            }
        }
        if (debug_database) { plugin.textUtils.debug("[Database] Loading all teams into cache"); }
    }
}
