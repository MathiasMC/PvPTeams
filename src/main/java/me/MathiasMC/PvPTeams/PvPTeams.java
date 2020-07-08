package me.MathiasMC.PvPTeams;

import com.google.common.io.ByteStreams;
import me.MathiasMC.PvPTeams.commands.PvPMenu_Command;
import me.MathiasMC.PvPTeams.commands.PvPTeams_Command;
import me.MathiasMC.PvPTeams.data.Database;
import me.MathiasMC.PvPTeams.data.PlayerConnect;
import me.MathiasMC.PvPTeams.data.TeamConnect;
import me.MathiasMC.PvPTeams.files.Config;
import me.MathiasMC.PvPTeams.files.GUIFolder;
import me.MathiasMC.PvPTeams.files.Language;
import me.MathiasMC.PvPTeams.gui.GUI;
import me.MathiasMC.PvPTeams.listeners.*;
import me.MathiasMC.PvPTeams.placeholders.InternalReplacer;
import me.MathiasMC.PvPTeams.utils.Metrics;
import me.MathiasMC.PvPTeams.utils.TextUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class PvPTeams extends JavaPlugin {

    public static PvPTeams call;

    public Config config;
    public Language language;

    public TextUtils textUtils;
    public InternalReplacer internalReplacer;

    public Database database;

    private final Map<String, PlayerConnect> playerConnect = new HashMap<>();

    private final Map<String, TeamConnect> teamConnect = new HashMap<>();
    public final HashMap<String, GUI> guiList = new HashMap<>();
    public final ConsoleCommandSender consoleCommandSender = Bukkit.getServer().getConsoleSender();

    @Override
    public void onEnable() {
        call = this;
        if (!getDataFolder().exists()) {
            getDataFolder().mkdir();
        }
        textUtils = new TextUtils(this);
        internalReplacer = new InternalReplacer(this);
        config = new Config(this);
        language = new Language(this);
        database = new Database(this);
        new GUIFolder(this);
        if (database.set()) {
            textUtils.info("Database ( Connected )");
            database.loadALL();
            database.loadAllTeams();
            getServer().getPluginManager().registerEvents(new PlayerLogin(this), this);
            getServer().getPluginManager().registerEvents(new PlayerQuit(this), this);
            getServer().getPluginManager().registerEvents(new InventoryClick(this), this);
            getServer().getPluginManager().registerEvents(new InventoryClose(this), this);
            getCommand("pvpteams").setExecutor(new PvPTeams_Command(this));
            getCommand("pvpmenu").setExecutor(new PvPMenu_Command(this));
            int pluginId = 8063;
            Metrics metrics = new Metrics(this, pluginId);
        } else {
            textUtils.error("Disabling plugin cannot connect to database");
            getServer().getPluginManager().disablePlugin(this);
        }
    }

    @Override
    public void onDisable() {
        try {
            database.close();
        } catch (SQLException exception) {
            textUtils.exception(exception.getStackTrace(), exception.getMessage());
        }
        call = null;
    }

    public void loadPlayer(String uuid) {
        PlayerConnect data = new PlayerConnect(uuid);
        playerConnect.put(uuid, data);
    }
    public PlayerConnect getPlayer(String uuid) {
        return playerConnect.get(uuid);
    }
    public Set<String> listPlayers() {
        return playerConnect.keySet();
    }
    public void loadTeam(String team) {
        TeamConnect data = new TeamConnect(team);
        teamConnect.put(team, data);
    }
    public void unloadTeam(String team) {
        TeamConnect data = teamConnect.remove(team);
        if (data != null) {
            data.save();
        }
    }
    public TeamConnect getTeam(String team) {
        return teamConnect.get(team);
    }
    public Set<String> listTeams() {
        return teamConnect.keySet();
    }

    public boolean isInt(String s) {
        try {
            Integer.parseInt(s);
        } catch(NumberFormatException e) {
            return false;
        }
        return true;
    }

    public void copy(String filename, File file) {
        try {
            ByteStreams.copy(getResource(filename), new FileOutputStream(file));
        } catch (IOException exception) {
            textUtils.exception(exception.getStackTrace(), exception.getMessage());
        }
    }

    public boolean versionID() {
        if (getServer().getVersion().contains("1.8")) { return true; }
        if (getServer().getVersion().contains("1.9")) { return true; }
        if (getServer().getVersion().contains("1.10")) { return true; }
        if (getServer().getVersion().contains("1.11")) { return true; }
        if (getServer().getVersion().contains("1.12")) { return true; }
        return false;
    }
}