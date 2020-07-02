package me.MathiasMC.PvPTeams.gui;

import me.MathiasMC.PvPTeams.PvPTeams;
import me.MathiasMC.PvPTeams.data.PlayerConnect;
import me.MathiasMC.PvPTeams.data.TeamConnect;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class GUI implements InventoryHolder {

    private final PvPTeams plugin;

    private Inventory inventory;

    public FileConfiguration fileConfiguration;

    public GUI(final PvPTeams plugin, FileConfiguration fileConfiguration) {
        this.plugin = plugin;
        this.fileConfiguration = fileConfiguration;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void open(Player player) {
        if (fileConfiguration.contains("settings.team.require-team") && fileConfiguration.getBoolean("settings.team.require-team")) {
            PlayerConnect playerConnect = plugin.getPlayer(player.getUniqueId().toString());
            if (playerConnect.hasTeam()) {
                if (fileConfiguration.contains("settings.team.require-level")) {
                    TeamConnect teamConnect = plugin.getTeam(playerConnect.getTeam());
                    if (teamConnect.getLevel() >= fileConfiguration.getInt("settings.team.require-level")) {
                        loadGUI(player);
                    } else {
                        for (String message : fileConfiguration.getStringList("settings.team.required-level")) {
                            player.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                        }
                    }
                    return;
                }
                loadGUI(player);
            } else {
                for (String message : fileConfiguration.getStringList("settings.team.no-team")) {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                }
            }
            return;
        }
        loadGUI(player);
    }

    private void loadGUI(Player player) {
        inventory = plugin.getServer().createInventory(this, fileConfiguration.getInt("settings.size"), ChatColor.translateAlternateColorCodes('&', fileConfiguration.getString("settings.name")));
        BukkitRunnable r = new BukkitRunnable() {
            public void run() {
                index(player);
            }
        };
        r.runTaskAsynchronously(plugin);
        player.openInventory(inventory);
    }

    private void index(Player player) {
        for (String key : fileConfiguration.getConfigurationSection("").getKeys(false)) {
            if (!key.equalsIgnoreCase("settings")) {
                inventory.setItem(fileConfiguration.getInt(key + ".POSITION"), getItem(fileConfiguration.getString(key + ".MATERIAL"), fileConfiguration.getInt(key + ".AMOUNT"), fileConfiguration.getString(key + ".NAME"), fileConfiguration.getStringList(key + ".LORES"), player, key));
            }
        }
    }

    private ItemStack getItem(final String material, final int amount, final String name, final List<String> lore, final Player player, final String key) {
        ItemStack itemStack = getID(material, amount);
        setItemMeta(itemStack, player, name, lore, key);
        return itemStack;
    }

    public void setItemMeta(ItemStack itemStack, Player player, String name, List<String> lore, String key) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (fileConfiguration.contains(key + ".OPTIONS") && fileConfiguration.getStringList(key + ".OPTIONS").contains("GLOW")) {
            itemStack.addUnsafeEnchantment(Enchantment.ARROW_INFINITE, 0);
            itemMeta = itemStack.getItemMeta();
            itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }
        itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', plugin.internalReplacer.replace(player, name, 0)));
        ArrayList<String> list = new ArrayList<>();
        if (fileConfiguration.contains(key + ".OPTIONS") && fileConfiguration.getStringList(key + ".OPTIONS").contains("TEAMLEVEL")) {
            int start = 0;
            for (String level : plugin.config.get.getStringList("levels." + (plugin.getTeam(plugin.getPlayer(player.getUniqueId().toString()).getTeam()).getLevel() + 1))) {
                list.add(ChatColor.translateAlternateColorCodes('&', plugin.internalReplacer.replace(player, "{pvpteams_team_level_quests}", start)));
                start++;
            }
        } else {
            for (String lores : lore) {
                list.add(ChatColor.translateAlternateColorCodes('&', plugin.internalReplacer.replace(player, lores, 0)));
            }
        }
        itemMeta.setLore(list);
        itemStack.setItemMeta(itemMeta);
    }

    private ItemStack getID(String bb, int amount) {
        if (plugin.versionID()) {
            try {
                String[] parts = bb.split(":");
                int matId = Integer.parseInt(parts[0]);
                if (parts.length == 2) {
                    short data = Short.parseShort(parts[1]);
                    return new ItemStack(Material.getMaterial(matId), amount, data);
                }
                return new ItemStack(Material.getMaterial(matId));
            } catch (Exception e) {
                return null;
            }
        } else {
            try {
                return new ItemStack(Material.getMaterial(bb), amount);
            } catch (Exception e) {
                return null;
            }
        }
    }
}