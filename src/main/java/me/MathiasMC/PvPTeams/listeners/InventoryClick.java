package me.MathiasMC.PvPTeams.listeners;

import me.MathiasMC.PvPTeams.PvPTeams;
import me.MathiasMC.PvPTeams.data.PlayerConnect;
import me.MathiasMC.PvPTeams.data.TeamConnect;
import me.MathiasMC.PvPTeams.gui.GUI;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.InventoryHolder;

import java.util.ArrayList;
import java.util.List;

public class InventoryClick implements Listener {

    private final PvPTeams plugin;

    public InventoryClick(final PvPTeams plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if (e.getInventory().getHolder() instanceof GUI) {
            e.setCancelled(true);
            if (e.getCurrentItem() == null) {
                return;
            }
            if (e.getCurrentItem().getItemMeta() == null) {
                return;
            }
            if (e.getAction() == InventoryAction.MOVE_TO_OTHER_INVENTORY) {
                e.setCancelled(true);
            }
            if (e.getClickedInventory().getType() == InventoryType.PLAYER) {
                e.setCancelled(true);
                return;
            }
            if (e.isShiftClick()) {
                e.setCancelled(true);
            }
            Player player = (Player) e.getWhoClicked();
            player.updateInventory();
            InventoryHolder inventoryHolder = e.getInventory().getHolder();
            for (String key : plugin.guiList.keySet()) {
                if (plugin.guiList.get(key) == inventoryHolder) {
                    FileConfiguration fileConfiguration = plugin.guiList.get(key).fileConfiguration;
                    for (String keyItem : fileConfiguration.getConfigurationSection("").getKeys(false)) {
                        if (!keyItem.equalsIgnoreCase("settings")) {
                            if (e.getSlot() == fileConfiguration.getInt(keyItem + ".POSITION")) {
                                if (fileConfiguration.contains(keyItem + ".OPTIONS") && fileConfiguration.getStringList(keyItem + ".OPTIONS").contains("CLOSE")) { player.closeInventory(); }
                                if (fileConfiguration.contains(keyItem + ".COMMANDS")) {
                                    for (String command : fileConfiguration.getStringList(keyItem + ".COMMANDS")) {
                                        plugin.getServer().dispatchCommand(plugin.consoleCommandSender, command.replace("{pvpteams_player}", player.getName()));
                                    }
                                }
                                if (fileConfiguration.contains(keyItem + ".TEAMSHOP")) {
                                    PlayerConnect playerConnect = plugin.getPlayer(player.getUniqueId().toString());
                                    if (playerConnect.hasTeam()) {
                                        TeamConnect teamConnect = plugin.getTeam(playerConnect.getTeam());
                                        Long cost = fileConfiguration.getLong(keyItem + ".TEAMSHOP.COST");
                                        Long back = teamConnect.getCoins() - cost;
                                        if (back >= 0L) {
                                            teamConnect.setCoins(back);
                                            for (String command : fileConfiguration.getStringList(keyItem + ".TEAMSHOP.COMMANDS")) {
                                                plugin.getServer().dispatchCommand(plugin.consoleCommandSender, command.replace("{pvpteams_player}", player.getName()));
                                            }
                                        } else {
                                            for (String message : fileConfiguration.getStringList(keyItem + ".TEAMSHOP.ENOUGH")) {
                                                player.sendMessage(ChatColor.translateAlternateColorCodes('&', message.replace("{pvpteams_player}", player.getName())));
                                            }
                                        }
                                    }
                                }
                                if (fileConfiguration.contains(keyItem + ".SHOP")) {
                                    PlayerConnect playerConnect = plugin.getPlayer(player.getUniqueId().toString());
                                    if (playerConnect.hasTeam()) {
                                        Long cost = fileConfiguration.getLong(keyItem + ".SHOP.COST");
                                        Long back = playerConnect.getCoins() - cost;
                                        if (back >= 0L) {
                                            playerConnect.setCoins(back);
                                            for (String command : fileConfiguration.getStringList(keyItem + ".SHOP.COMMANDS")) {
                                                plugin.getServer().dispatchCommand(plugin.consoleCommandSender, command.replace("{pvpteams_player}", player.getName()));
                                            }
                                        } else {
                                            for (String message : fileConfiguration.getStringList(keyItem + ".SHOP.ENOUGH")) {
                                                player.sendMessage(ChatColor.translateAlternateColorCodes('&', message.replace("{pvpteams_player}", player.getName())));
                                            }
                                        }
                                    }
                                }
                                if (fileConfiguration.contains(keyItem + ".OPTIONS") && fileConfiguration.getStringList(keyItem + ".OPTIONS").contains("TEAMLEVEL")) {
                                    PlayerConnect playerConnect = plugin.getPlayer(player.getUniqueId().toString());
                                    if (playerConnect.hasTeam()) {
                                        TeamConnect teamConnect = plugin.getTeam(playerConnect.getTeam());
                                        boolean canLevelUP = true;
                                        boolean maxLevel = false;
                                        boolean error = false;
                                        if (plugin.config.get.contains("levels." + (teamConnect.getLevel() + 1))) {
                                            for (String loop : plugin.config.get.getStringList("levels." + (teamConnect.getLevel() + 1))) {
                                                String currentQuest = teamConnect.getQuest(loop);
                                                if (currentQuest != null) {
                                                    String[] current = teamConnect.getQuest(loop).split("=")[0].split(":");
                                                    if (Long.parseLong(current[1]) < plugin.config.get.getLong("quests." + current[0] + ".amount")) {
                                                        canLevelUP = false;
                                                    }
                                                } else {
                                                    error = true;
                                                }
                                            }
                                        } else {
                                            maxLevel = true;
                                        }
                                        if (!error) {
                                            if (!maxLevel) {
                                                if (canLevelUP) {
                                                    teamConnect.setLevel(teamConnect.getLevel() + 1L);
                                                    if (plugin.config.get.contains("levelup.level." + (teamConnect.getLevel() + 1L))) {
                                                        for (String command : plugin.config.get.getStringList("levelup.level." + (teamConnect.getLevel() + 1L))) {
                                                            plugin.getServer().dispatchCommand(plugin.consoleCommandSender, plugin.internalReplacer.replace(player, command, 0));
                                                        }
                                                    }
                                                } else {
                                                    if (plugin.config.get.contains("levelup.requirements." + (teamConnect.getLevel() + 1L))) {
                                                        for (String command : plugin.config.get.getStringList("levelup.requirements." + (teamConnect.getLevel() + 1L))) {
                                                            plugin.getServer().dispatchCommand(plugin.consoleCommandSender, plugin.internalReplacer.replace(player, command, 0));
                                                        }
                                                    }
                                                }
                                            } else {
                                                for (String command : plugin.config.get.getStringList("levelup.max")) {
                                                    plugin.getServer().dispatchCommand(plugin.consoleCommandSender, plugin.internalReplacer.replace(player, command, 0));
                                                }
                                            }
                                        } else {
                                            for (String command : plugin.config.get.getStringList("levelup.error")) {
                                                plugin.getServer().dispatchCommand(plugin.consoleCommandSender, plugin.internalReplacer.replace(player, command, 0));
                                            }
                                        }
                                    }
                                }
                                break;
                            }
                        }
                    }
                    break;
                }
            }
        }
    }
}