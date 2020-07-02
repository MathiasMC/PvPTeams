package me.MathiasMC.PvPTeams.placeholders;

import me.MathiasMC.PvPTeams.PvPTeams;
import me.MathiasMC.PvPTeams.data.PlayerConnect;
import me.MathiasMC.PvPTeams.data.TeamConnect;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;

import java.util.ArrayList;
import java.util.UUID;

public class InternalReplacer {

    private final PvPTeams plugin;

    public InternalReplacer(final PvPTeams plugin) {
        this.plugin = plugin;
    }

    public String replace(OfflinePlayer offlinePlayer, String message, int get) {
        String uuid = offlinePlayer.getUniqueId().toString();
        PlayerConnect playerConnect = plugin.getPlayer(uuid);
        if (playerConnect.hasTeam()) {
            message = message
                    .replace("{pvpteams_team}", getTeam(playerConnect))
                    .replace("{pvpteams_team_owner}", getOwner(playerConnect))
                    .replace("{pvpteams_team_members}", getMembers(playerConnect))
                    .replace("{pvpteams_team_members_size}", getMembersSize(playerConnect))
                    .replace("{pvpteams_team_members_max}", getMaxMembers(playerConnect))
                    .replace("{pvpteams_team_coins}", getTeamCoins(playerConnect))
                    .replace("{pvpteams_team_level}", String.valueOf(getTeamLevel(playerConnect)))
                    .replace("{pvpteams_team_level_next}", String.valueOf(getTeamNextLevel(playerConnect)))
                    .replace("{pvpteams_team_level_quests}", requirements(playerConnect, get));
        }
        message = message.replace("{pvpteams_player}", offlinePlayer.getName()).replace("{pvpteams_coins}", String.valueOf(playerConnect.getCoins()));
        return message;
    }

    public String getTeam(PlayerConnect playerConnect) {
        return playerConnect.getTeam();
    }

    public String getOwner(PlayerConnect playerConnect) {
        return plugin.getServer().getOfflinePlayer(UUID.fromString(plugin.getTeam(playerConnect.getTeam()).getOwner())).getName();
    }

    public String getMembers(PlayerConnect playerConnect) {
        TeamConnect teamConnect = plugin.getTeam(playerConnect.getTeam());
        ArrayList<String> names = new ArrayList<>();
        for (OfflinePlayer offlinePlayer : teamConnect.getOfflineMembers()) {
            names.add(offlinePlayer.getName());
        }
        return names.toString().replace("[", "").replace("]", "");
    }

    public String getMaxMembers(PlayerConnect playerConnect) {
        return String.valueOf(plugin.getTeam(playerConnect.getTeam()).getMax_members());
    }

    public String getMembersSize(PlayerConnect playerConnect) {
        return String.valueOf(plugin.getTeam(playerConnect.getTeam()).getCurrentMembersSize());
    }

    public String getTeamCoins(PlayerConnect playerConnect) {
        return String.valueOf(plugin.getTeam(playerConnect.getTeam()).getCoins());
    }

    public long getTeamLevel(PlayerConnect playerConnect) {
        return plugin.getTeam(playerConnect.getTeam()).getLevel();
    }

    public long getTeamNextLevel(PlayerConnect playerConnect) {
        return getTeamLevel(playerConnect) + 1;
    }

    public String requirements(PlayerConnect playerConnect, int get) {
        ArrayList<String> list = new ArrayList<>();
        TeamConnect teamConnect = plugin.getTeam(playerConnect.getTeam());
        for (String level : plugin.config.get.getStringList("levels." + (teamConnect.getLevel() + 1))) {
            String quest = teamConnect.getQuest(level);
            String value = "";
            String has = ChatColor.translateAlternateColorCodes('&', plugin.config.get.getString("placeholders.quests_question.no"));
            if (quest != null) {
                value = quest.split("=")[0].split(":")[1];
                if (Long.parseLong(quest.split("=")[0].split(":")[1]) >= plugin.config.get.getLong("quests." + level + ".amount")) {
                    has = ChatColor.translateAlternateColorCodes('&', plugin.config.get.getString("placeholders.quests_question.yes"));
                }
            }
            list.add(plugin.config.get.getString("quests." + level + ".prefix").replace("{pvpteams_quests_value}", plugin.config.get.getString("quests." + level + ".amount")).replace("{pvpteams_quests_current}", value).replace("{pvpteams_quests_question}", has));
        }
        return list.get(get).replace("[", "").replace("]", "");
    }
}
