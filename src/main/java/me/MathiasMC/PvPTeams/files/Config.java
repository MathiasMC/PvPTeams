package me.MathiasMC.PvPTeams.files;

import me.MathiasMC.PvPTeams.PvPTeams;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

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
            } catch (IOException exception) {
                plugin.textUtils.exception(exception.getStackTrace(), exception.getMessage());
            }
        }
        load();
        update();
    }

    public void load() {
        get = YamlConfiguration.loadConfiguration(file);
    }

    private void update() {
        boolean change = false;
        plugin.textUtils.fileHeader(get);
        if (!get.contains("debug.database")) {
            get.set("debug.database", false);
            change = true;
        }
        if (!get.contains("mysql")) {
            get.set("mysql.use", false);
            get.set("mysql.host", "localhost");
            get.set("mysql.port", 3306);
            get.set("mysql.database", "database");
            get.set("mysql.username", "username");
            get.set("mysql.password", "password");
            change = true;
        }
        if (!get.contains("levels")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("blockbreak_iron_ore_1");
            list.add("blockbreak_coal_ore_1");
            list.add("location_1");
            get.set("levels.1", list);
            ArrayList<String> list1 = new ArrayList<>();
            list1.add("blockbreak_iron_ore_2");
            list1.add("blockbreak_coal_ore_2");
            list1.add("location_2");
            get.set("levels.2", list1);
            ArrayList<String> list2 = new ArrayList<>();
            list2.add("blockbreak_diamond_ore_3");
            list2.add("location_3");
            get.set("levels.3", list2);
            change = true;
        }
        if (!get.contains("placeholders")) {
            get.set("placeholders.quests_question.yes", "&2Yes");
            get.set("placeholders.quests_question.no", "&cNo");
            change = true;
        }
        if (!get.contains("quests")) {
            get.set("quests.blockbreak_iron_ore_1.amount", 25);
            get.set("quests.blockbreak_iron_ore_1.prefix", "&6Iron Ore &a{pvpteams_quests_current} &6out of &a{pvpteams_quests_value}");
            get.set("quests.blockbreak_coal_ore_1.amount", 25);
            get.set("quests.blockbreak_coal_ore_1.prefix", "&6Coal Ore &a{pvpteams_quests_current} &6out of &a{pvpteams_quests_value}");
            get.set("quests.blockbreak_iron_ore_2.amount", 50);
            get.set("quests.blockbreak_iron_ore_2.prefix", "&6Iron Ore &a{pvpteams_quests_current} &6out of &a{pvpteams_quests_value}");
            get.set("quests.blockbreak_coal_ore_2.amount", 50);
            get.set("quests.blockbreak_coal_ore_2.prefix", "&6Coal Ore &a{pvpteams_quests_current} &6out of &a{pvpteams_quests_value}");
            get.set("quests.blockbreak_diamond_ore_3.amount", 10);
            get.set("quests.blockbreak_diamond_ore_3.prefix", "&6Diamond Ore &a{pvpteams_quests_current} &6out of &a{pvpteams_quests_value}");
            get.set("quests.location_1.amount", 1);
            get.set("quests.location_1.prefix", "&6Location &a{pvpteams_quests_question}");
            get.set("quests.location_2.amount", 1);
            get.set("quests.location_2.prefix", "&6Location &a{pvpteams_quests_question}");
            get.set("quests.location_3.amount", 1);
            get.set("quests.location_3.prefix", "&6Location &a{pvpteams_quests_question}");
            change = true;
        }

        if (!get.contains("levelup")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("pvpteams message {pvpteams_player} &7[&aPvPTeams&7] &eYour team is now level {pvpteams_level}");
            get.set("levelup.level.1", list);
            ArrayList<String> listlevel = new ArrayList<>();
            listlevel.add("pvpteams message {pvpteams_player} &7[&aPvPTeams&7] &eYour team is now level {pvpteams_level}");
            get.set("levelup.level.2", listlevel);
            ArrayList<String> listlevel1 = new ArrayList<>();
            listlevel1.add("pvpteams message {pvpteams_player} &7[&aPvPTeams&7] &eYour team is now level {pvpteams_level}");
            get.set("levelup.level.3", listlevel1);
            ArrayList<String> list1 = new ArrayList<>();
            list1.add("pvpteams message {pvpteams_player} &7[&aPvPTeams&7] &cYou don't have the required requirements!");
            get.set("levelup.requirements.1", list1);
            ArrayList<String> listrequirements = new ArrayList<>();
            listrequirements.add("pvpteams message {pvpteams_player} &7[&aPvPTeams&7] &cYou don't have the required requirements!");
            get.set("levelup.requirements.2", listrequirements);
            ArrayList<String> listrequirements1 = new ArrayList<>();
            listrequirements1.add("pvpteams message {pvpteams_player} &7[&aPvPTeams&7] &cYou don't have the required requirements!");
            get.set("levelup.requirements.3", listrequirements1);
            ArrayList<String> list2 = new ArrayList<>();
            list2.add("pvpteams message {pvpteams_player} &7[&aPvPTeams&7] &cYour team is at max level!");
            get.set("levelup.max", list2);
            ArrayList<String> list3 = new ArrayList<>();
            list3.add("pvpteams message {pvpteams_player} &7[&aPvPTeams&7] &cSomething went wrong try again later...");
            get.set("levelup.error", list3);
            change = true;
        }
        if (change) {
            try {
                get.save(file);
            } catch (IOException exception) {
                plugin.textUtils.exception(exception.getStackTrace(), exception.getMessage());
            }
            plugin.textUtils.info("config.yml ( A change was made )");
        } else {
            plugin.textUtils.info("config.yml ( Loaded )");
        }
    }
}
