package me.MathiasMC.PvPTeams.commands;

import me.MathiasMC.PvPTeams.PvPTeams;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PvPMenu_Command implements CommandExecutor {

    private final PvPTeams plugin;

    public PvPMenu_Command(final PvPTeams plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (cmd.getName().equalsIgnoreCase("pvpmenu")) {
            if (sender.hasPermission("pvpmenu.command")) {
                String path;
                if (sender instanceof Player) {
                    path = "player";
                } else {
                    path = "console";
                }
                if (args.length == 0) {
                    if (path.equalsIgnoreCase("player")) {
                        Player player = (Player) sender;
                        for (String command : plugin.language.get.getStringList("player.pvpmenu.commands")) {
                            plugin.getServer().dispatchCommand(plugin.consoleCommandSender, command.replace("{pvpteams_player}", player.getName()));
                        }
                    } else {
                        for (String message : plugin.language.get.getStringList("console.pvpmenu.usage")) {
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                        }
                    }
                } else if (args.length == 1) {
                    Player target = plugin.getServer().getPlayer(args[0]);
                    if (target != null) {
                        for (String command : plugin.language.get.getStringList(path + ".pvpmenu.commands")) {
                            plugin.getServer().dispatchCommand(plugin.consoleCommandSender, command.replace("{pvpteams_player}", target.getName()));
                        }
                    } else {
                        for (String message : plugin.language.get.getStringList(path + ".pvpmenu.online")) {
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                        }
                    }
                } else {
                    for (String message : plugin.language.get.getStringList(path + ".pvpmenu.usage")) {
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                    }
                }
            } else {
                for (String message : plugin.language.get.getStringList("player.pvpmenu.permission")) {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                }
            }
        }
        return true;
    }
}
