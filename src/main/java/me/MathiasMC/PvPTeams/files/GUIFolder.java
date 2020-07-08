package me.MathiasMC.PvPTeams.files;

import me.MathiasMC.PvPTeams.PvPTeams;
import me.MathiasMC.PvPTeams.gui.GUI;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class GUIFolder {

    private final PvPTeams plugin;

    public GUIFolder(final PvPTeams plugin) {
        this.plugin = plugin;
        File folder = new File(plugin.getDataFolder() + File.separator + "gui");
        if (!folder.exists()) {
            folder.mkdir();
            File menuFile = new File(folder, "menu.yml");
            File shopFile = new File(folder, "shop.yml");
            File shop0File = new File(folder, "shop0.yml");
            File shop1File = new File(folder, "shop1.yml");
            File teamShopFile = new File(folder, "teamShop.yml");
            File teamShop0File = new File(folder, "teamShop0.yml");
            File teamShop1File = new File(folder, "teamShop1.yml");
            try {
                menuFile.createNewFile();
                shopFile.createNewFile();
                teamShopFile.createNewFile();
                shop0File.createNewFile();
                shop1File.createNewFile();
                teamShop0File.createNewFile();
                teamShop1File.createNewFile();
                if (!plugin.versionID()) {
                    plugin.copy("gui/menu.yml", menuFile);
                    plugin.copy("gui/shop.yml", shopFile);
                    plugin.copy("gui/shop0.yml", shop0File);
                    plugin.copy("gui/shop1.yml", shop1File);
                    plugin.copy("gui/teamShop.yml", teamShopFile);
                    plugin.copy("gui/teamShop0.yml", teamShop0File);
                    plugin.copy("gui/teamShop1.yml", teamShop1File);
                } else {
                    plugin.copy("old/gui/menu.yml", menuFile);
                    plugin.copy("old/gui/shop.yml", shopFile);
                    plugin.copy("old/gui/shop0.yml", shop0File);
                    plugin.copy("old/gui/shop1.yml", shop1File);
                    plugin.copy("old/gui/teamShop.yml", teamShopFile);
                    plugin.copy("old/gui/teamShop0.yml", teamShop0File);
                    plugin.copy("old/gui/teamShop1.yml", teamShop1File);
                }
            } catch (IOException exception) {
                plugin.textUtils.exception(exception.getStackTrace(), exception.getMessage());
            }
        }
        for (File file : folder.listFiles()) {
            String fileName = file.getName();
            if (fileName.endsWith(".yml")) {
                plugin.guiList.put(fileName, new GUI(plugin, YamlConfiguration.loadConfiguration(file)));
                plugin.textUtils.info("[GUI] " + fileName + " ( Loaded )");
            } else {
                plugin.textUtils.error("[GUI] " + fileName + " ( Error Loading )");
            }
        }
    }
}