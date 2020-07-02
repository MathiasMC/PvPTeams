package me.MathiasMC.PvPTeams.commands;

import me.MathiasMC.PvPTeams.PvPTeams;
import me.MathiasMC.PvPTeams.data.PlayerConnect;
import me.MathiasMC.PvPTeams.data.TeamConnect;
import me.MathiasMC.PvPTeams.gui.GUI;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;

import java.util.HashMap;
import java.util.UUID;
import java.util.regex.Pattern;

public class PvPTeams_Command implements CommandExecutor {

    private final PvPTeams plugin;

    public PvPTeams_Command(final PvPTeams plugin) {
        this.plugin = plugin;
    }

    HashMap<String, String> invite = new HashMap<>();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (cmd.getName().equalsIgnoreCase("pvpteams")) {
            if (sender.hasPermission("pvpteams.command")) {
                boolean unknown = true;
                String path;
                if (sender instanceof Player) {
                    path = "player";
                } else {
                    path = "console";
                }
                if (args.length == 0) {
                    for (String message : plugin.language.get.getStringList(path + ".pvpteams.command.message")) {
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message.replace("{pvpteams_version}", plugin.getDescription().getVersion())));
                    }
                } else {
                    if (args[0].equalsIgnoreCase("help")) {
                        unknown = false;
                        if (sender.hasPermission("pvpteams.command.help")) {
                            for (String message : plugin.language.get.getStringList(path + ".pvpteams.help.message")) {
                                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                            }
                        } else {
                            for (String message : plugin.language.get.getStringList("player.pvpteams.help.permission")) {
                                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                            }
                        }
                    } else if (args[0].equalsIgnoreCase("save")) {
                        unknown = false;
                        if (sender.hasPermission("pvpteams.command.save")) {
                            for (String uuid : plugin.listPlayers()) {
                                plugin.getPlayer(uuid).save();
                            }
                            for (String team : plugin.listTeams()) {
                                plugin.getTeam(team).save();
                            }
                            for (String message : plugin.language.get.getStringList(path + ".pvpteams.save.saved")) {
                                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                            }
                        } else {
                            for (String message : plugin.language.get.getStringList("player.pvpteams.save.permission")) {
                                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                            }
                        }
                    } else if (args[0].equalsIgnoreCase("reload")) {
                        unknown = false;
                        if (sender.hasPermission("pvpteams.command.reload")) {
                            plugin.config.load();
                            plugin.language.load();
                            for (String message : plugin.language.get.getStringList(path + ".pvpteams.reload.reloaded")) {
                                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                            }
                        } else {
                            for (String message : plugin.language.get.getStringList("player.pvpteams.reload.permission")) {
                                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                            }
                        }
                    } else if (args[0].equalsIgnoreCase("message")) {
                        unknown = false;
                        if (sender.hasPermission("pvpteams.command.message")) {
                            if (args.length <= 2) {
                                for (String message : plugin.language.get.getStringList(path + ".pvpteams.message.usage")) {
                                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                                }
                            } else {
                                Player target = plugin.getServer().getPlayer(args[1]);
                                if (target != null) {
                                    StringBuilder sb = new StringBuilder();
                                    for (int i = 2; i < args.length; i++) {
                                        sb.append(args[i]).append(" ");
                                    }
                                    String text = sb.toString().trim();
                                    if (!text.contains("\\n")) {
                                        target.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.internalReplacer.replace(target, text, 0)));
                                    } else {
                                        for (String message : text.split(Pattern.quote("\\n"))) {
                                            target.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.internalReplacer.replace(target, message, 0)));
                                        }
                                    }
                                } else {
                                    for (String message : plugin.language.get.getStringList(path + ".pvpteams.message.online")) {
                                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                                    }
                                }
                            }
                        } else {
                            for (String message : plugin.language.get.getStringList("player.pvpteams.message.permission")) {
                                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                            }
                        }
                    } else if (args[0].equalsIgnoreCase("create")) {
                        unknown = false;
                        if (sender.hasPermission("pvpteams.command.create")) {
                            if (path.equalsIgnoreCase("player")) {
                                if (args.length == 2) {
                                    Player player = (Player) sender;
                                    String uuid = player.getUniqueId().toString();
                                    PlayerConnect playerConnect = plugin.getPlayer(uuid);
                                    if (!playerConnect.hasTeam()) {
                                        if (!plugin.listTeams().contains(args[1])) {
                                            if (checkText(args[1])) {
                                                plugin.database.insertTeam(args[1], uuid);
                                                for (String message : plugin.language.get.getStringList("player.pvpteams.create.created")) {
                                                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message.replace("{pvpteams_team}", args[1])));
                                                }
                                            } else {
                                                for (String message : plugin.language.get.getStringList("player.pvpteams.create.name")) {
                                                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                                                }
                                            }
                                        } else {
                                            for (String message : plugin.language.get.getStringList("player.pvpteams.create.taken")) {
                                                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                                            }
                                        }
                                    } else {
                                        if (plugin.getTeam(playerConnect.getTeam()).getMembers().contains(uuid)) {
                                            for (String message : plugin.language.get.getStringList("player.pvpteams.create.member")) {
                                                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message.replace("{pvpteams_team}", playerConnect.getTeam())));
                                            }
                                        } else {
                                            for (String message : plugin.language.get.getStringList("player.pvpteams.create.already")) {
                                                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                                            }
                                        }
                                    }
                                } else {
                                    for (String message : plugin.language.get.getStringList("player.pvpteams.create.usage")) {
                                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                                    }
                                }
                            } else {
                                for (String message : plugin.language.get.getStringList("console.pvpteams.create.usage")) {
                                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                                }
                            }
                        } else {
                            for (String message : plugin.language.get.getStringList("player.pvpteams.create.permission")) {
                                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                            }
                        }
                    } else if (args[0].equalsIgnoreCase("delete")) {
                        unknown = false;
                        if (sender.hasPermission("pvpteams.command.delete")) {
                            if (path.equalsIgnoreCase("player")) {
                                if (args.length == 1) {
                                    Player player = (Player) sender;
                                    String uuid = player.getUniqueId().toString();
                                    PlayerConnect playerConnect = plugin.getPlayer(uuid);
                                    if (playerConnect.hasTeam()) {
                                        String currentTeam = playerConnect.getTeam();
                                        TeamConnect teamConnect = plugin.getTeam(currentTeam);
                                        if (uuid.equalsIgnoreCase(teamConnect.getOwner())) {
                                            for (String member : teamConnect.getMembers()) {
                                                PlayerConnect memberConnect = plugin.getPlayer(member);
                                                memberConnect.setTeam(null);
                                                memberConnect.save();
                                                OfflinePlayer offlinePlayer = plugin.getServer().getOfflinePlayer(UUID.fromString(member));
                                                if (offlinePlayer.isOnline()) {
                                                    for (String message : plugin.language.get.getStringList("player.pvpteams.delete.member")) {
                                                        offlinePlayer.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                                                    }
                                                }
                                            }
                                            plugin.database.deleteTeam(currentTeam, uuid);
                                            for (String message : plugin.language.get.getStringList("player.pvpteams.delete.deleted")) {
                                                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message.replace("{pvpteams_team}", currentTeam)));
                                            }
                                        } else {
                                            for (String message : plugin.language.get.getStringList("player.pvpteams.delete.owner")) {
                                                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                                            }
                                        }
                                    } else {
                                        for (String message : plugin.language.get.getStringList("player.pvpteams.delete.no-team")) {
                                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                                        }
                                    }
                                } else {
                                    for (String message : plugin.language.get.getStringList("player.pvpteams.delete.usage")) {
                                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                                    }
                                }
                            } else {
                                for (String message : plugin.language.get.getStringList("console.pvpteams.delete.usage")) {
                                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                                }
                            }
                        } else {
                            for (String message : plugin.language.get.getStringList("player.pvpteams.delete.permission")) {
                                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                            }
                        }
                    } else if (args[0].equalsIgnoreCase("invite")) {
                        unknown = false;
                        if (sender.hasPermission("pvpteams.command.invite")) {
                            if (path.equalsIgnoreCase("player")) {
                                if (args.length == 2) {
                                    Player target = plugin.getServer().getPlayer(args[1]);
                                    if (target != null) {
                                        Player player = (Player) sender;
                                        String uuid = player.getUniqueId().toString();
                                        PlayerConnect playerConnect = plugin.getPlayer(uuid);
                                        PlayerConnect playerConnectTarget = plugin.getPlayer(target.getUniqueId().toString());
                                        if (playerConnect.hasTeam()) {
                                            if (!playerConnectTarget.hasTeam()) {
                                                String currentTeam = playerConnect.getTeam();
                                                TeamConnect teamConnect = plugin.getTeam(currentTeam);
                                                if (uuid.equalsIgnoreCase(teamConnect.getOwner())) {
                                                    if (!teamConnect.getMembers().contains(target.getUniqueId().toString())) {
                                                        if (teamConnect.getMembers().size() < teamConnect.getMax_members()) {
                                                            if (!invite.containsKey(target.getUniqueId().toString())) {
                                                                invite.put(target.getUniqueId().toString(), teamConnect.getTeam());
                                                                plugin.getServer().getScheduler().runTaskLaterAsynchronously(plugin, () -> {
                                                                    if (invite.containsKey(target.getUniqueId().toString())) {
                                                                        invite.remove(target.getUniqueId().toString());
                                                                        for (String message : plugin.language.get.getStringList("player.pvpteams.invite.dont")) {
                                                                            target.sendMessage(ChatColor.translateAlternateColorCodes('&', message.replace("{pvpteams_team}", currentTeam)));
                                                                        }
                                                                    }
                                                                }, 20L * 10L);
                                                                for (String message : plugin.language.get.getStringList("player.pvpteams.invite.invited-owner")) {
                                                                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message.replace("{pvpteams_team_player}", target.getName()).replace("{pvpteams_team}", currentTeam)));
                                                                }
                                                                for (String message : plugin.language.get.getStringList("player.pvpteams.invite.invited")) {
                                                                    target.sendMessage(ChatColor.translateAlternateColorCodes('&', message.replace("{pvpteams_team}", currentTeam)));
                                                                }
                                                            } else {
                                                                for (String message : plugin.language.get.getStringList("player.pvpteams.invite.already")) {
                                                                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                                                                }
                                                            }
                                                        } else {
                                                            sender.sendMessage("you cannot invite more");
                                                        }
                                                    } else {
                                                        for (String message : plugin.language.get.getStringList("player.pvpteams.invite.player")) {
                                                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                                                        }
                                                    }
                                                } else {
                                                    for (String message : plugin.language.get.getStringList("player.pvpteams.invite.only")) {
                                                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                                                    }
                                                }
                                            } else {
                                                for (String message : plugin.language.get.getStringList("player.pvpteams.invite.already-team")) {
                                                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                                                }
                                            }
                                        } else {
                                            for (String message : plugin.language.get.getStringList("player.pvpteams.invite.team")) {
                                                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                                            }
                                        }
                                    } else {
                                        for (String message : plugin.language.get.getStringList("player.pvpteams.invite.online")) {
                                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                                        }
                                    }
                                } else {
                                    for (String message : plugin.language.get.getStringList("player.pvpteams.invite.usage")) {
                                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                                    }
                                }
                            } else {
                                for (String message : plugin.language.get.getStringList("console.pvpteams.invite.usage")) {
                                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                                }
                            }
                        } else {
                            for (String message : plugin.language.get.getStringList("player.pvpteams.invite.permission")) {
                                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                            }
                        }
                    } else if (args[0].equalsIgnoreCase("accept")) {
                        unknown = false;
                        if (sender.hasPermission("pvpteams.command.accept")) {
                            if (path.equalsIgnoreCase("player")) {
                                if (args.length == 1) {
                                    Player player = (Player) sender;
                                    PlayerConnect playerConnect = plugin.getPlayer(player.getUniqueId().toString());
                                    if (!playerConnect.hasTeam()) {
                                        if (invite.containsKey(player.getUniqueId().toString())) {
                                            playerConnect.setTeam(invite.get(player.getUniqueId().toString()));
                                            playerConnect.save();
                                            String currentTeam = playerConnect.getTeam();
                                            TeamConnect teamConnect = plugin.getTeam(currentTeam);
                                            teamConnect.addMember(player.getUniqueId().toString());
                                            teamConnect.save();
                                            invite.remove(player.getUniqueId().toString());
                                            for (String message : plugin.language.get.getStringList("player.pvpteams.accept.accepted")) {
                                                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message.replace("{pvpteams_team}", currentTeam)));
                                            }
                                            OfflinePlayer owner = plugin.getServer().getOfflinePlayer(UUID.fromString(teamConnect.getOwner()));
                                            if (owner.isOnline()) {
                                                Player ownerPlayer = owner.getPlayer();
                                                for (String message : plugin.language.get.getStringList("player.pvpteams.accept.accepted-owner")) {
                                                    ownerPlayer.sendMessage(ChatColor.translateAlternateColorCodes('&', message.replace("{pvpteams_team_player}", player.getName())));
                                                }
                                            }
                                        } else {
                                            for (String message : plugin.language.get.getStringList("player.pvpteams.accept.invite")) {
                                                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                                            }
                                        }
                                    } else {
                                        for (String message : plugin.language.get.getStringList("player.pvpteams.accept.team")) {
                                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                                        }
                                    }
                                } else {
                                    for (String message : plugin.language.get.getStringList("player.pvpteams.accept.usage")) {
                                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                                    }
                                }
                            } else {
                                for (String message : plugin.language.get.getStringList("console.pvpteams.accept.usage")) {
                                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                                }
                            }
                        } else {
                            for (String message : plugin.language.get.getStringList("player.pvpteams.accept.permission")) {
                                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                            }
                        }
                    } else if (args[0].equalsIgnoreCase("kick")) {
                        unknown = false;
                        if (sender.hasPermission("pvpteams.command.kick")) {
                            if (path.equalsIgnoreCase("player")) {
                                if (args.length == 2) {
                                    Player player = (Player) sender;
                                    String uuid = player.getUniqueId().toString();
                                    PlayerConnect playerConnect = plugin.getPlayer(uuid);
                                    if (playerConnect.hasTeam()) {
                                        String currentTeam = playerConnect.getTeam();
                                        TeamConnect teamConnect = plugin.getTeam(currentTeam);
                                        if (uuid.equalsIgnoreCase(teamConnect.getOwner())) {
                                            boolean hasMember = false;
                                            for (OfflinePlayer offlinePlayer : teamConnect.getOfflineMembers()) {
                                                if (args[1].equalsIgnoreCase(offlinePlayer.getName())) {
                                                    teamConnect.removeMember(offlinePlayer.getUniqueId().toString());
                                                    teamConnect.save();
                                                    PlayerConnect targetConnect = plugin.getPlayer(offlinePlayer.getUniqueId().toString());
                                                    targetConnect.setTeam(null);
                                                    targetConnect.save();
                                                    if (offlinePlayer.isOnline()) {
                                                        for (String message : plugin.language.get.getStringList("player.pvpteams.kick.kicked-member")) {
                                                            offlinePlayer.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                                                        }
                                                    }
                                                    hasMember = true;
                                                    for (String message : plugin.language.get.getStringList("player.pvpteams.kick.kicked-owner")) {
                                                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message.replace("{pvpteams_team_player}", offlinePlayer.getName())));
                                                    }
                                                    break;
                                                }
                                            }
                                            if (!hasMember) {
                                                for (String message : plugin.language.get.getStringList("player.pvpteams.kick.player")) {
                                                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                                                }
                                            }
                                        } else {
                                            for (String message : plugin.language.get.getStringList("player.pvpteams.kick.only")) {
                                                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                                            }
                                        }
                                    } else {
                                        for (String message : plugin.language.get.getStringList("player.pvpteams.kick.team")) {
                                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                                        }
                                    }
                                } else {
                                    for (String message : plugin.language.get.getStringList("player.pvpteams.kick.usage")) {
                                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                                    }
                                }
                            } else {
                                for (String message : plugin.language.get.getStringList("console.pvpteams.kick.usage")) {
                                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                                }
                            }
                        } else {
                            for (String message : plugin.language.get.getStringList("player.pvpteams.kick.permission")) {
                                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                            }
                        }
                    } else if (args[0].equalsIgnoreCase("gui")) {
                        unknown = false;
                        if (sender.hasPermission("pvpteams.command.gui")) {
                            if (args.length > 1) {
                                if (args[1].equalsIgnoreCase("open")) {
                                    if (sender.hasPermission("pvpteams.command.gui.open")) {
                                        if (args.length == 3) {
                                            if (path.equalsIgnoreCase("player")) {
                                                Player player = (Player) sender;
                                                GUI gui = plugin.guiList.get(args[2]);
                                                if (gui != null) {
                                                    gui.open(player);
                                                } else {
                                                    for (String message : plugin.language.get.getStringList("player.pvpteams.gui.open.found")) {
                                                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message.replace("{pvpteams_gui_file}", args[2])));
                                                    }
                                                }
                                            } else {
                                                for (String message : plugin.language.get.getStringList("console.pvpteams.gui.open.usage")) {
                                                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                                                }
                                            }
                                        } else if (args.length == 4) {
                                            Player target = plugin.getServer().getPlayer(args[3]);
                                            if (target != null) {
                                                GUI gui = plugin.guiList.get(args[2]);
                                                if (gui != null) {
                                                    gui.open(target);
                                                } else {
                                                    for (String message : plugin.language.get.getStringList(path + ".pvpteams.gui.open.found")) {
                                                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message.replace("{pvpteams_gui_file}", args[2])));
                                                    }
                                                }
                                            } else {
                                                for (String message : plugin.language.get.getStringList(path + ".pvpteams.gui.open.online")) {
                                                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                                                }
                                            }
                                        } else {
                                            for (String message : plugin.language.get.getStringList(path + ".pvpteams.gui.open.usage")) {
                                                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                                            }
                                        }
                                    } else {
                                        for (String message : plugin.language.get.getStringList("player.pvpteams.gui.open.permission")) {
                                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                                        }
                                    }
                                } else {
                                    for (String message : plugin.language.get.getStringList(path + ".pvpteams.gui.usage")) {
                                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                                    }
                                }
                            } else {
                                for (String message : plugin.language.get.getStringList(path + ".pvpteams.gui.usage")) {
                                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                                }
                            }
                        } else {
                            for (String message : plugin.language.get.getStringList("player.pvpteams.gui.permission")) {
                                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                            }
                        }
                    } else if (args[0].equalsIgnoreCase("quest")) {
                        unknown = false;
                        if (sender.hasPermission("pvpteams.command.quest")) {
                            if (args.length == 1) {
                                for (String message : plugin.language.get.getStringList(path + ".pvpteams.quest.usage")) {
                                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                                }
                            } else {
                                if (plugin.config.get.contains("quests." + args[1])) {
                                    if (args.length == 2) {
                                        for (String message : plugin.language.get.getStringList(path + ".pvpteams.quest.usage")) {
                                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                                        }
                                    } else {
                                        if (args[2].equalsIgnoreCase("add")) {
                                            if (args.length == 5) {
                                                Player target = PvPTeams.call.getServer().getPlayer(args[4]);
                                                if (target != null) {
                                                    PlayerConnect playerConnect = plugin.getPlayer(target.getUniqueId().toString());
                                                    if (playerConnect.hasTeam()) {
                                                        if (plugin.isInt(args[3])) {
                                                            TeamConnect teamConnect = plugin.getTeam(playerConnect.getTeam());
                                                            String quest = teamConnect.getQuest(args[1]);
                                                            if (quest != null) {
                                                                String[] split = quest.split("=");
                                                                String[] split2 = split[0].split(":");
                                                                Long value = Long.parseLong(split2[1]) + Long.parseLong(args[3]);
                                                                teamConnect.setQuest(split2[0] + ":" + value, Integer.parseInt(split[1]));
                                                            }
                                                            for (String message : plugin.language.get.getStringList(path + ".pvpteams.quest.add.message")) {
                                                                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message.replace("{pvpteams_quest_add}", args[3]).replace("{pvpteams_team}", teamConnect.getTeam())));
                                                            }
                                                        } else {
                                                            for (String message : plugin.language.get.getStringList(path + ".pvpteams.quest.add.number")) {
                                                                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                                                            }
                                                        }
                                                    } else {
                                                        for (String message : plugin.language.get.getStringList(path + ".pvpteams.quest.add.team")) {
                                                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                                                        }
                                                    }
                                                } else {
                                                    for (String message : plugin.language.get.getStringList(path + ".pvpteams.quest.add.online")) {
                                                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                                                    }
                                                }
                                            } else {
                                                for (String message : plugin.language.get.getStringList(path + ".pvpteams.quest.add.usage")) {
                                                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                                                }
                                            }
                                        } else {
                                            for (String message : plugin.language.get.getStringList(path + ".pvpteams.quest.usage")) {
                                                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                                            }
                                        }
                                    }
                                } else {
                                    for (String message : plugin.language.get.getStringList(path + ".pvpteams.quest.find")) {
                                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                                    }
                                }
                            }
                        } else {
                            for (String message : plugin.language.get.getStringList("player.pvpteams.quest.permission")) {
                                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                            }
                        }
                    } else if (args[0].equalsIgnoreCase("member") || args[0].equalsIgnoreCase("coins") || args[0].equalsIgnoreCase("teamcoins")) {
                        unknown = false;
                        if (sender.hasPermission("pvpteams.command." + args[0])) {
                            if (args.length == 1) {
                                for (String message : plugin.language.get.getStringList(path + ".pvpteams.add-remove-set.usage")) {
                                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                                }
                            } else {
                                if (args.length == 4) {
                                    Player target = PvPTeams.call.getServer().getPlayer(args[3]);
                                    if (target != null) {
                                        if (plugin.isInt(args[2])) {
                                            PlayerConnect playerConnect = plugin.getPlayer(target.getUniqueId().toString());
                                            if (args[1].equalsIgnoreCase("add")) {
                                                if (args[0].equalsIgnoreCase("coins")) {
                                                    playerSet(playerConnect, playerConnect.getCoins() + Integer.parseInt(args[2]), sender, args[0], path, target);
                                                } else if (args[0].equalsIgnoreCase("teamcoins")) {
                                                    if (playerConnect.hasTeam()) {
                                                        TeamConnect teamConnect = plugin.getTeam(playerConnect.getTeam());
                                                        teamSet(teamConnect, teamConnect.getCoins() + Integer.parseInt(args[2]), sender, args[0], path, target);
                                                    } else {
                                                        for (String message : plugin.language.get.getStringList(path + ".pvpteams.add-remove-set.team")) {
                                                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                                                        }
                                                    }
                                                } else if (args[0].equalsIgnoreCase("member")) {
                                                    if (playerConnect.hasTeam()) {
                                                        TeamConnect teamConnect = plugin.getTeam(playerConnect.getTeam());
                                                        teamSet(teamConnect, teamConnect.getMax_members() + Integer.parseInt(args[2]), sender, args[0], path, target);
                                                    } else {
                                                        for (String message : plugin.language.get.getStringList(path + ".pvpteams.add-remove-set.team")) {
                                                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                                                        }
                                                    }
                                                }
                                            } else if (args[1].equalsIgnoreCase("remove")) {
                                                if (args[0].equalsIgnoreCase("coins")) {
                                                    playerSet(playerConnect, playerConnect.getCoins() - Integer.parseInt(args[2]), sender, args[0], path, target);
                                                } else if (args[0].equalsIgnoreCase("teamcoins")) {
                                                    if (playerConnect.hasTeam()) {
                                                        TeamConnect teamConnect = plugin.getTeam(playerConnect.getTeam());
                                                        teamSet(teamConnect, teamConnect.getCoins() - Integer.parseInt(args[2]), sender, args[0], path, target);
                                                    } else {
                                                        for (String message : plugin.language.get.getStringList(path + ".pvpteams.add-remove-set.team")) {
                                                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                                                        }
                                                    }
                                                } else if (args[0].equalsIgnoreCase("member")) {
                                                    if (playerConnect.hasTeam()) {
                                                        TeamConnect teamConnect = plugin.getTeam(playerConnect.getTeam());
                                                        teamSet(teamConnect, teamConnect.getMax_members() - Integer.parseInt(args[2]), sender, args[0], path, target);
                                                    } else {
                                                        for (String message : plugin.language.get.getStringList(path + ".pvpteams.add-remove-set.team")) {
                                                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                                                        }
                                                    }
                                                }
                                            } else if (args[1].equalsIgnoreCase("set")) {
                                                if (args[0].equalsIgnoreCase("coins")) {
                                                    playerSet(playerConnect, Integer.parseInt(args[2]), sender, args[0], path, target);
                                                } else {
                                                    if (playerConnect.hasTeam()) {
                                                        teamSet(plugin.getTeam(playerConnect.getTeam()), Integer.parseInt(args[2]), sender, args[0], path, target);
                                                    } else {
                                                        for (String message : plugin.language.get.getStringList(path + ".pvpteams.add-remove-set.team")) {
                                                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                                                        }
                                                    }
                                                }
                                            } else {
                                                for (String message : plugin.language.get.getStringList(path + ".pvpteams.add-remove-set.usage")) {
                                                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                                                }
                                            }
                                        } else {
                                            for (String message : plugin.language.get.getStringList(path + ".pvpteams.add-remove-set.number")) {
                                                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                                            }
                                        }
                                    } else {
                                        for (String message : plugin.language.get.getStringList(path + ".pvpteams.add-remove-set.online")) {
                                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                                        }
                                    }
                                } else {
                                    for (String message : plugin.language.get.getStringList(path + ".pvpteams.add-remove-set.usage")) {
                                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                                    }
                                }
                            }
                        } else {
                            for (String message : plugin.language.get.getStringList("player.pvpteams.add-remove-set.permission")) {
                                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                            }
                        }
                    }
                    if (unknown) {
                        for (String message : plugin.language.get.getStringList(path + ".pvpteams.command.unknown")) {
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message.replace("{pvpteams_command}", args[0])));
                        }
                    }
                }
            } else {
                for (String message : plugin.language.get.getStringList("player.pvpteams.command.permission")) {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                }
            }
        }
        return true;
    }

    private boolean checkText(String text) {
        return text.matches("^[a-zA-Z]*$");
    }

    private void teamSet(final TeamConnect teamConnect, final long set, final CommandSender sender, final String type, final String path, final Player target) {
        if (type.equalsIgnoreCase("member")) {
            if (set >= 0) {
                teamConnect.setMax_members(set);
                if (sender instanceof Player) {
                    for (String message : plugin.language.get.getStringList(path + ".pvpteams.add-remove-set.members")) {
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message.replace("{pvpteams_team}", teamConnect.getTeam()).replace("{pvpteams_members}", String.valueOf(set))));
                    }
                } else {
                    for (String message : plugin.language.get.getStringList(path + ".pvpteams.add-remove-set.members")) {
                        target.sendMessage(ChatColor.translateAlternateColorCodes('&', message.replace("{pvpteams_team}", teamConnect.getTeam()).replace("{pvpteams_members}", String.valueOf(set))));
                    }
                }
            } else {
                for (String message : plugin.language.get.getStringList(path + ".pvpteams.add-remove-set.0")) {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                }
            }
        } else if (type.equalsIgnoreCase("teamcoins")) {
            if (set >= 0) {
                teamConnect.setCoins(set);
                if (sender instanceof Player) {
                    for (String message : plugin.language.get.getStringList(path + ".pvpteams.add-remove-set.teamcoins")) {
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message.replace("{pvpteams_team}", teamConnect.getTeam()).replace("{pvpteams_teamcoins}", String.valueOf(set))));
                    }
                } else {
                    for (String message : plugin.language.get.getStringList(path + ".pvpteams.add-remove-set.teamcoins")) {
                        target.sendMessage(ChatColor.translateAlternateColorCodes('&', message.replace("{pvpteams_team}", teamConnect.getTeam()).replace("{pvpteams_teamcoins}", String.valueOf(set))));
                    }
                }
            } else {
                for (String message : plugin.language.get.getStringList(path + ".pvpteams.add-remove-set.0")) {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                }
            }
        }
    }

    private void playerSet(final PlayerConnect playerConnect, final long set, final CommandSender sender, final String type, final String path, final Player target) {
        if (type.equalsIgnoreCase("coins")) {
            if (set >= 0) {
                playerConnect.setCoins(set);
                if (sender instanceof Player) {
                    for (String message : plugin.language.get.getStringList(path + ".pvpteams.add-remove-set.coins")) {
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message.replace("{pvpteams_player}", target.getName()).replace("{pvpteams_coins}", String.valueOf(set))));
                    }
                } else {
                    for (String message : plugin.language.get.getStringList(path + ".pvpteams.add-remove-set.coins")) {
                        target.sendMessage(ChatColor.translateAlternateColorCodes('&', message.replace("{pvpteams_coins}", String.valueOf(set))));
                    }
                }
            } else {
                for (String message : plugin.language.get.getStringList(path + ".pvpteams.add-remove-set.0")) {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                }
            }
        }
    }
}