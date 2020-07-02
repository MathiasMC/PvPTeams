package me.MathiasMC.PvPTeams.files;

import me.MathiasMC.PvPTeams.PvPTeams;
import me.MathiasMC.PvPTeams.gui.GUI;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

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
            } catch (IOException exception) {
                plugin.textUtils.exception(exception.getStackTrace(), exception.getMessage());
            }
            FileConfiguration menu = YamlConfiguration.loadConfiguration(menuFile);
            FileConfiguration shop = YamlConfiguration.loadConfiguration(shopFile);
            FileConfiguration teamShop = YamlConfiguration.loadConfiguration(teamShopFile);
            FileConfiguration shop0 = YamlConfiguration.loadConfiguration(shop0File);
            FileConfiguration shop1 = YamlConfiguration.loadConfiguration(shop1File);
            FileConfiguration teamShop0 = YamlConfiguration.loadConfiguration(teamShop0File);
            FileConfiguration teamShop1 = YamlConfiguration.loadConfiguration(teamShop1File);
            menu(menu);
            shop(shop);
            teamShop(teamShop);
            shop0(shop0);
            shop1(shop1);
            teamShop0(teamShop0);
            teamShop1(teamShop1);
            try {
                menu.save(menuFile);
                shop.save(shopFile);
                teamShop.save(teamShopFile);
                shop0.save(shop0File);
                shop1.save(shop1File);
                teamShop0.save(teamShop0File);
                teamShop1.save(teamShop1File);
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

    private void menu(FileConfiguration fileConfiguration) {
        fileConfiguration.set("settings.name", "&f&lPvPTeams Menu");
        fileConfiguration.set("settings.size", 54);
        fileConfiguration.set("settings.team.require-team", true);
        ArrayList<String> noTeam = new ArrayList<>();
        noTeam.add("&7[&aPvPTeams&7] &cYou cannot use this you dont have a team!");
        fileConfiguration.set("settings.team.no-team", noTeam);
        ArrayList<String> noLevel = new ArrayList<>();
        noLevel.add("&7[&aPvPTeams&7] &cYour team is not the required level to use this!");
        fileConfiguration.set("settings.team.required-level", noLevel);
        fileConfiguration.set("teamInfo.NAME", "&6&lTeam Info");
        ArrayList<String> list = new ArrayList<>();
        list.add("&6Name: &f{pvpteams_team}");
        list.add("&6Owner: &f{pvpteams_team_owner}");
        list.add("&6Max Members: &f{pvpteams_team_members_max}");
        list.add("&6Level: &f&a{pvpteams_team_level}");
        fileConfiguration.set("teamInfo.LORES", list);
        if (plugin.versionID()) {
            fileConfiguration.set("teamInfo.MATERIAL", "340:0");
        } else {
            fileConfiguration.set("teamInfo.MATERIAL", "BOOK");
        }
        fileConfiguration.set("teamInfo.AMOUNT", 1);
        fileConfiguration.set("teamInfo.POSITION", 13);
        ArrayList<String> list1 = new ArrayList<>();
        list1.add("GLOW");
        fileConfiguration.set("teamInfo.OPTIONS", list1);
        fileConfiguration.set("teamMembers.NAME", "&6&lMembers");
        ArrayList<String> list2 = new ArrayList<>();
        list2.add("&f{pvpteams_team_members}");
        fileConfiguration.set("teamMembers.LORES", list2);
        if (plugin.versionID()) {
            fileConfiguration.set("teamMembers.MATERIAL", "276:0");
        } else {
            fileConfiguration.set("teamMembers.MATERIAL", "DIAMOND_SWORD");
        }
        fileConfiguration.set("teamMembers.AMOUNT", 1);
        fileConfiguration.set("teamMembers.POSITION", 24);
        ArrayList<String> list3 = new ArrayList<>();
        list3.add("GLOW");
        fileConfiguration.set("teamMembers.OPTIONS", list3);
        fileConfiguration.set("teamCoins.NAME", "&6&lTeam Coins");
        ArrayList<String> list4 = new ArrayList<>();
        list4.add("&f{pvpteams_team_coins}");
        list4.add("");
        list4.add("&6&lYour coins");
        list4.add("&f{pvpteams_coins}");
        fileConfiguration.set("teamCoins.LORES", list4);
        if (plugin.versionID()) {
            fileConfiguration.set("teamCoins.MATERIAL", "175:0");
        } else {
            fileConfiguration.set("teamCoins.MATERIAL", "SUNFLOWER");
        }
        fileConfiguration.set("teamCoins.AMOUNT", 1);
        fileConfiguration.set("teamCoins.POSITION", 20);
        ArrayList<String> list5 = new ArrayList<>();
        list5.add("GLOW");
        fileConfiguration.set("teamCoins.OPTIONS", list5);
        fileConfiguration.set("teamLimitations.NAME", "&6&lLimitations");
        ArrayList<String> list6 = new ArrayList<>();
        list6.add("&6Members: &a{pvpteams_team_members_size} &6out of &a{pvpteams_team_members_max}");
        fileConfiguration.set("teamLimitations.LORES", list6);
        if (plugin.versionID()) {
            fileConfiguration.set("teamLimitations.MATERIAL", "145:0");
        } else {
            fileConfiguration.set("teamLimitations.MATERIAL", "ANVIL");
        }
        fileConfiguration.set("teamLimitations.AMOUNT", 1);
        fileConfiguration.set("teamLimitations.POSITION", 16);
        ArrayList<String> list7 = new ArrayList<>();
        list7.add("GLOW");
        fileConfiguration.set("teamLimitations.OPTIONS", list7);
        fileConfiguration.set("none.NAME", "&6&lNone");
        ArrayList<String> list8 = new ArrayList<>();
        fileConfiguration.set("none.LORES", list8);
        if (plugin.versionID()) {
            fileConfiguration.set("none.MATERIAL", "145:0");
        } else {
            fileConfiguration.set("none.MATERIAL", "ANVIL");
        }
        fileConfiguration.set("none.AMOUNT", 1);
        fileConfiguration.set("none.POSITION", 10);
        ArrayList<String> list9 = new ArrayList<>();
        list9.add("GLOW");
        fileConfiguration.set("none.OPTIONS", list9);
        fileConfiguration.set("TeamLevel.NAME", "&6&lRequirement to level &a{pvpteams_team_level_next}");
        ArrayList<String> list10 = new ArrayList<>();
        list10.add("{pvpteams_team_level_quests}");
        fileConfiguration.set("TeamLevel.LORES", list10);
        if (plugin.versionID()) {
            fileConfiguration.set("TeamLevel.MATERIAL", "399:0");
        } else {
            fileConfiguration.set("TeamLevel.MATERIAL", "NETHER_STAR");
        }
        fileConfiguration.set("TeamLevel.AMOUNT", 1);
        fileConfiguration.set("TeamLevel.POSITION", 40);
        ArrayList<String> list11 = new ArrayList<>();
        list11.add("GLOW");
        list11.add("TEAMLEVEL");
        fileConfiguration.set("TeamLevel.OPTIONS", list11);
        fileConfiguration.set("Shop.NAME", "&6&lShops");
        ArrayList<String> list12 = new ArrayList<>();
        fileConfiguration.set("Shop.LORES", list12);
        if (plugin.versionID()) {
            fileConfiguration.set("Shop.MATERIAL", "138:0");
        } else {
            fileConfiguration.set("Shop.MATERIAL", "BEACON");
        }
        fileConfiguration.set("Shop.AMOUNT", 1);
        fileConfiguration.set("Shop.POSITION", 37);
        ArrayList<String> list13 = new ArrayList<>();
        list13.add("GLOW");
        list13.add("CLOSE");
        fileConfiguration.set("Shop.OPTIONS", list13);
        ArrayList<String> list16 = new ArrayList<>();
        list16.add("pvpteams gui open shop.yml {pvpteams_player}");
        fileConfiguration.set("Shop.COMMANDS", list16);
        fileConfiguration.set("TeamShop.NAME", "&6&lTeam Shops");
        ArrayList<String> list14 = new ArrayList<>();
        fileConfiguration.set("TeamShop.LORES", list14);
        if (plugin.versionID()) {
            fileConfiguration.set("TeamShop.MATERIAL", "138:0");
        } else {
            fileConfiguration.set("TeamShop.MATERIAL", "BEACON");
        }
        fileConfiguration.set("TeamShop.AMOUNT", 1);
        fileConfiguration.set("TeamShop.POSITION", 43);
        ArrayList<String> list15 = new ArrayList<>();
        list15.add("GLOW");
        list15.add("CLOSE");
        fileConfiguration.set("TeamShop.OPTIONS", list15);
        ArrayList<String> list17 = new ArrayList<>();
        list17.add("pvpteams gui open teamShop.yml {pvpteams_player}");
        fileConfiguration.set("TeamShop.COMMANDS", list17);
    }

    private void shop(FileConfiguration fileConfiguration) {
        fileConfiguration.set("settings.name", "&f&lShops");
        fileConfiguration.set("settings.size", 54);
        fileConfiguration.set("settings.team.require-team", true);
        ArrayList<String> noTeam = new ArrayList<>();
        noTeam.add("&7[&aPvPTeams&7] &cYou cannot use this you dont have a team!");
        fileConfiguration.set("settings.team.no-team", noTeam);
        ArrayList<String> noLevel = new ArrayList<>();
        noLevel.add("&7[&aPvPTeams&7] &cYour team is not the required level to use this!");
        fileConfiguration.set("settings.team.required-level", noLevel);
        fileConfiguration.set("back.NAME", "&6&lBack to team menu");
        fileConfiguration.set("back.LORES", new ArrayList<>());
        if (plugin.versionID()) {
            fileConfiguration.set("back.MATERIAL", "340:0");
        } else {
            fileConfiguration.set("back.MATERIAL", "BOOK");
        }
        fileConfiguration.set("back.AMOUNT", 1);
        fileConfiguration.set("back.POSITION", 45);
        ArrayList<String> list1 = new ArrayList<>();
        list1.add("GLOW");
        fileConfiguration.set("back.OPTIONS", list1);
        ArrayList<String> list2 = new ArrayList<>();
        list2.add("pvpteams gui open menu.yml {pvpteams_player}");
        fileConfiguration.set("back.COMMANDS", list2);



        fileConfiguration.set("shop0.NAME", "&6&lStarter Shop");
        ArrayList<String> listShop = new ArrayList<>();
        listShop.add("&aRequire team level 0");
        fileConfiguration.set("shop0.LORES", listShop);
        if (plugin.versionID()) {
            fileConfiguration.set("shop0.MATERIAL", "138:0");
        } else {
            fileConfiguration.set("shop0.MATERIAL", "BEACON");
        }
        fileConfiguration.set("shop0.AMOUNT", 1);
        fileConfiguration.set("shop0.POSITION", 11);
        ArrayList<String> list3 = new ArrayList<>();
        list3.add("GLOW");
        fileConfiguration.set("shop0.OPTIONS", list3);
        ArrayList<String> list4 = new ArrayList<>();
        list4.add("pvpteams gui open shop0.yml {pvpteams_player}");
        fileConfiguration.set("shop0.COMMANDS", list4);

        fileConfiguration.set("shop1.NAME", "&6&lBetter Shop");
        ArrayList<String> listShop1 = new ArrayList<>();
        listShop1.add("&aRequire team level 1");
        fileConfiguration.set("shop1.LORES", listShop1);
        if (plugin.versionID()) {
            fileConfiguration.set("shop1.MATERIAL", "138:0");
        } else {
            fileConfiguration.set("shop1.MATERIAL", "BEACON");
        }
        fileConfiguration.set("shop1.AMOUNT", 1);
        fileConfiguration.set("shop1.POSITION", 13);
        ArrayList<String> list5 = new ArrayList<>();
        list5.add("GLOW");
        fileConfiguration.set("shop1.OPTIONS", list5);
        ArrayList<String> list6 = new ArrayList<>();
        list6.add("pvpteams gui open shop1.yml {pvpteams_player}");
        fileConfiguration.set("shop1.COMMANDS", list6);
    }

    private void teamShop(FileConfiguration fileConfiguration) {
        fileConfiguration.set("settings.name", "&f&lTeam Shops");
        fileConfiguration.set("settings.size", 54);
        fileConfiguration.set("settings.team.require-team", true);
        ArrayList<String> noTeam = new ArrayList<>();
        noTeam.add("&7[&aPvPTeams&7] &cYou cannot use this you dont have a team!");
        fileConfiguration.set("settings.team.no-team", noTeam);
        ArrayList<String> noLevel = new ArrayList<>();
        noLevel.add("&7[&aPvPTeams&7] &cYour team is not the required level to use this!");
        fileConfiguration.set("settings.team.required-level", noLevel);
        fileConfiguration.set("back.NAME", "&6&lBack to team menu");
        fileConfiguration.set("back.LORES", new ArrayList<>());
        if (plugin.versionID()) {
            fileConfiguration.set("back.MATERIAL", "340:0");
        } else {
            fileConfiguration.set("back.MATERIAL", "BOOK");
        }
        fileConfiguration.set("back.AMOUNT", 1);
        fileConfiguration.set("back.POSITION", 45);
        ArrayList<String> list1 = new ArrayList<>();
        list1.add("GLOW");
        fileConfiguration.set("back.OPTIONS", list1);
        ArrayList<String> list2 = new ArrayList<>();
        list2.add("pvpteams gui open menu.yml {pvpteams_player}");
        fileConfiguration.set("back.COMMANDS", list2);


        fileConfiguration.set("shop0.NAME", "&6&lStarter Shop");
        ArrayList<String> listShop = new ArrayList<>();
        listShop.add("&aRequire team level 0");
        fileConfiguration.set("shop0.LORES", listShop);
        if (plugin.versionID()) {
            fileConfiguration.set("shop0.MATERIAL", "138:0");
        } else {
            fileConfiguration.set("shop0.MATERIAL", "BEACON");
        }
        fileConfiguration.set("shop0.AMOUNT", 1);
        fileConfiguration.set("shop0.POSITION", 11);
        ArrayList<String> list3 = new ArrayList<>();
        list3.add("GLOW");
        fileConfiguration.set("shop0.OPTIONS", list3);
        ArrayList<String> list4 = new ArrayList<>();
        list4.add("pvpteams gui open teamShop0.yml {pvpteams_player}");
        fileConfiguration.set("shop0.COMMANDS", list4);

        fileConfiguration.set("shop1.NAME", "&6&lBetter Shop");
        ArrayList<String> listShop1 = new ArrayList<>();
        listShop1.add("&aRequire team level 1");
        fileConfiguration.set("shop1.LORES", listShop1);
        if (plugin.versionID()) {
            fileConfiguration.set("shop1.MATERIAL", "138:0");
        } else {
            fileConfiguration.set("shop1.MATERIAL", "BEACON");
        }
        fileConfiguration.set("shop1.AMOUNT", 1);
        fileConfiguration.set("shop1.POSITION", 13);
        ArrayList<String> list5 = new ArrayList<>();
        list5.add("GLOW");
        fileConfiguration.set("shop1.OPTIONS", list5);
        ArrayList<String> list6 = new ArrayList<>();
        list6.add("pvpteams gui open teamShop1.yml {pvpteams_player}");
        fileConfiguration.set("shop1.COMMANDS", list6);
    }

    private void shop0(FileConfiguration fileConfiguration) {
        fileConfiguration.set("settings.name", "&f&lShop Level 0");
        fileConfiguration.set("settings.size", 54);
        fileConfiguration.set("settings.team.require-team", true);
        ArrayList<String> noTeam = new ArrayList<>();
        noTeam.add("&7[&aPvPTeams&7] &cYou cannot use this you dont have a team!");
        fileConfiguration.set("settings.team.no-team", noTeam);
        ArrayList<String> noLevel = new ArrayList<>();
        noLevel.add("&7[&aPvPTeams&7] &cYour team is not the required level to use this!");
        fileConfiguration.set("settings.team.required-level", noLevel);
        fileConfiguration.set("back.NAME", "&6&lBack to shop menu");
        fileConfiguration.set("back.LORES", new ArrayList<>());
        if (plugin.versionID()) {
            fileConfiguration.set("back.MATERIAL", "340:0");
        } else {
            fileConfiguration.set("back.MATERIAL", "BOOK");
        }
        fileConfiguration.set("back.AMOUNT", 1);
        fileConfiguration.set("back.POSITION", 45);
        ArrayList<String> list1 = new ArrayList<>();
        list1.add("GLOW");
        fileConfiguration.set("back.OPTIONS", list1);
        ArrayList<String> list2 = new ArrayList<>();
        list2.add("pvpteams gui open shop.yml {pvpteams_player}");
        fileConfiguration.set("back.COMMANDS", list2);
        fileConfiguration.set("dirt.NAME", "&6&lDirt");
        ArrayList<String> lore = new ArrayList<>();
        lore.add("&6Cost &a10");
        fileConfiguration.set("dirt.LORES", lore);
        if (plugin.versionID()) {
            fileConfiguration.set("dirt.MATERIAL", "3:0");
        } else {
            fileConfiguration.set("dirt.MATERIAL", "DIRT");
        }
        fileConfiguration.set("dirt.AMOUNT", 1);
        fileConfiguration.set("dirt.POSITION", 10);
        fileConfiguration.set("dirt.SHOP.COST", 10);
        ArrayList<String> list3 = new ArrayList<>();
        list3.add("&7[&aPvPTeams&7] &cYou dont have enough coins to buy that!");
        fileConfiguration.set("dirt.SHOP.ENOUGH", list3);
        ArrayList<String> list4 = new ArrayList<>();
        list4.add("pvpteams message {pvpteams_player} &7[&aPvPTeams&7] &eBuy dirt");
        fileConfiguration.set("dirt.SHOP.COMMANDS", list4);
    }

    private void shop1(FileConfiguration fileConfiguration) {
        fileConfiguration.set("settings.name", "&f&lShop Level 1");
        fileConfiguration.set("settings.size", 54);
        fileConfiguration.set("settings.team.require-team", true);
        fileConfiguration.set("settings.team.require-level", 1);
        ArrayList<String> noTeam = new ArrayList<>();
        noTeam.add("&7[&aPvPTeams&7] &cYou cannot use this you dont have a team!");
        fileConfiguration.set("settings.team.no-team", noTeam);
        ArrayList<String> noLevel = new ArrayList<>();
        noLevel.add("&7[&aPvPTeams&7] &cYour team is not the required level to use this!");
        fileConfiguration.set("settings.team.required-level", noLevel);
        fileConfiguration.set("back.NAME", "&6&lBack to shop menu");
        fileConfiguration.set("back.LORES", new ArrayList<>());
        if (plugin.versionID()) {
            fileConfiguration.set("back.MATERIAL", "340:0");
        } else {
            fileConfiguration.set("back.MATERIAL", "BOOK");
        }
        fileConfiguration.set("back.AMOUNT", 1);
        fileConfiguration.set("back.POSITION", 45);
        ArrayList<String> list1 = new ArrayList<>();
        list1.add("GLOW");
        fileConfiguration.set("back.OPTIONS", list1);
        ArrayList<String> list2 = new ArrayList<>();
        list2.add("pvpteams gui open shop.yml {pvpteams_player}");
        fileConfiguration.set("back.COMMANDS", list2);
        fileConfiguration.set("dirt.NAME", "&6&lDirt");
        ArrayList<String> lore = new ArrayList<>();
        lore.add("&6Cost &a10");
        fileConfiguration.set("dirt.LORES", lore);
        if (plugin.versionID()) {
            fileConfiguration.set("dirt.MATERIAL", "3:0");
        } else {
            fileConfiguration.set("dirt.MATERIAL", "DIRT");
        }
        fileConfiguration.set("dirt.AMOUNT", 1);
        fileConfiguration.set("dirt.POSITION", 10);
        fileConfiguration.set("dirt.SHOP.COST", 10);
        ArrayList<String> list3 = new ArrayList<>();
        list3.add("&7[&aPvPTeams&7] &cYou dont have enough coins to buy that!");
        fileConfiguration.set("dirt.SHOP.ENOUGH", list3);
        ArrayList<String> list4 = new ArrayList<>();
        list4.add("pvpteams message {pvpteams_player} &7[&aPvPTeams&7] &eBuy dirt");
        fileConfiguration.set("dirt.SHOP.COMMANDS", list4);
    }

    private void teamShop0(FileConfiguration fileConfiguration) {
        fileConfiguration.set("settings.name", "&f&lTeam Shop Level 0");
        fileConfiguration.set("settings.size", 54);
        fileConfiguration.set("settings.team.require-team", true);
        ArrayList<String> noTeam = new ArrayList<>();
        noTeam.add("&7[&aPvPTeams&7] &cYou cannot use this you dont have a team!");
        fileConfiguration.set("settings.team.no-team", noTeam);
        ArrayList<String> noLevel = new ArrayList<>();
        noLevel.add("&7[&aPvPTeams&7] &cYour team is not the required level to use this!");
        fileConfiguration.set("settings.team.required-level", noLevel);
        fileConfiguration.set("back.NAME", "&6&lBack to team shop menu");
        fileConfiguration.set("back.LORES", new ArrayList<>());
        if (plugin.versionID()) {
            fileConfiguration.set("back.MATERIAL", "340:0");
        } else {
            fileConfiguration.set("back.MATERIAL", "BOOK");
        }
        fileConfiguration.set("back.AMOUNT", 1);
        fileConfiguration.set("back.POSITION", 45);
        ArrayList<String> list1 = new ArrayList<>();
        list1.add("GLOW");
        fileConfiguration.set("back.OPTIONS", list1);
        ArrayList<String> list2 = new ArrayList<>();
        list2.add("pvpteams gui open teamShop.yml {pvpteams_player}");
        fileConfiguration.set("back.COMMANDS", list2);
        fileConfiguration.set("dirt.NAME", "&6&lDirt");
        ArrayList<String> lore = new ArrayList<>();
        lore.add("&6Cost &a10");
        fileConfiguration.set("dirt.LORES", lore);
        if (plugin.versionID()) {
            fileConfiguration.set("dirt.MATERIAL", "3:0");
        } else {
            fileConfiguration.set("dirt.MATERIAL", "DIRT");
        }
        fileConfiguration.set("dirt.AMOUNT", 1);
        fileConfiguration.set("dirt.POSITION", 10);
        fileConfiguration.set("dirt.TEAMSHOP.COST", 10);
        ArrayList<String> list3 = new ArrayList<>();
        list3.add("&7[&aPvPTeams&7] &cYour team does not have enough coins to buy that!");
        fileConfiguration.set("dirt.TEAMSHOP.ENOUGH", list3);
        ArrayList<String> list4 = new ArrayList<>();
        list4.add("pvpteams message {pvpteams_player} &7[&aPvPTeams&7] &eBuy dirt");
        fileConfiguration.set("dirt.TEAMSHOP.COMMANDS", list4);

    }

    private void teamShop1(FileConfiguration fileConfiguration) {
        fileConfiguration.set("settings.name", "&f&lTeam Shop Level 1");
        fileConfiguration.set("settings.size", 54);
        fileConfiguration.set("settings.team.require-team", true);
        fileConfiguration.set("settings.team.require-level", 1);
        ArrayList<String> noTeam = new ArrayList<>();
        noTeam.add("&7[&aPvPTeams&7] &cYou cannot use this you dont have a team!");
        fileConfiguration.set("settings.team.no-team", noTeam);
        ArrayList<String> noLevel = new ArrayList<>();
        noLevel.add("&7[&aPvPTeams&7] &cYour team is not the required level to use this!");
        fileConfiguration.set("settings.team.required-level", noLevel);
        fileConfiguration.set("back.NAME", "&6&lBack to team shop menu");
        fileConfiguration.set("back.LORES", new ArrayList<>());
        if (plugin.versionID()) {
            fileConfiguration.set("back.MATERIAL", "340:0");
        } else {
            fileConfiguration.set("back.MATERIAL", "BOOK");
        }
        fileConfiguration.set("back.AMOUNT", 1);
        fileConfiguration.set("back.POSITION", 45);
        ArrayList<String> list1 = new ArrayList<>();
        list1.add("GLOW");
        fileConfiguration.set("back.OPTIONS", list1);
        ArrayList<String> list2 = new ArrayList<>();
        list2.add("pvpteams gui open teamShop.yml {pvpteams_player}");
        fileConfiguration.set("back.COMMANDS", list2);
        fileConfiguration.set("dirt.NAME", "&6&lDirt");
        ArrayList<String> lore = new ArrayList<>();
        lore.add("&6Cost &a10");
        fileConfiguration.set("dirt.LORES", lore);
        if (plugin.versionID()) {
            fileConfiguration.set("dirt.MATERIAL", "3:0");
        } else {
            fileConfiguration.set("dirt.MATERIAL", "DIRT");
        }
        fileConfiguration.set("dirt.AMOUNT", 1);
        fileConfiguration.set("dirt.POSITION", 10);
        fileConfiguration.set("dirt.TEAMSHOP.COST", 10);
        ArrayList<String> list3 = new ArrayList<>();
        list3.add("&7[&aPvPTeams&7] &cYour team does not have enough coins to buy that!");
        fileConfiguration.set("dirt.TEAMSHOP.ENOUGH", list3);
        ArrayList<String> list4 = new ArrayList<>();
        list4.add("pvpteams message {pvpteams_player} &7[&aPvPTeams&7] &eBuy dirt");
        fileConfiguration.set("dirt.TEAMSHOP.COMMANDS", list4);
    }
}
