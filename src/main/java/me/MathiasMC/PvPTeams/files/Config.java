package me.MathiasMC.PvPTeams.files;

import me.MathiasMC.PvPTeams.PvPTeams;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class Config {

    public FileConfiguration get;
    private final File file;

    private final PvPTeams plugin;

    public Config(final PvPTeams plugin) {
        this.plugin = plugin;
        file = new File(plugin.getDataFolder(), "config.yml");
        if (!file.exists()) {
            try {
                file.createNewFile();
                plugin.copy("config.yml", file);
                plugin.textUtils.info("config.yml ( A change was made )");
            } catch (IOException exception) {
                plugin.textUtils.exception(exception.getStackTrace(), exception.getMessage());
            }
        } else {
            plugin.textUtils.info("config.yml ( Loaded )");
        }
        load();
    }

    public void load() {
        get = YamlConfiguration.loadConfiguration(file);
    }
}
