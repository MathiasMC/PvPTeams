package me.MathiasMC.PvPTeams.data;

import me.MathiasMC.PvPTeams.PvPTeams;
import org.bukkit.OfflinePlayer;

import java.util.*;

public class TeamConnect {

    private final String team;

    private String owner;

    private List<String> members;

    private long max_members;

    private long coins;

    private long level;

    private List<String> quests;

    public TeamConnect(String team) {
        this.team = team;
        String[] data = PvPTeams.call.database.getValuesTeams(team);
        owner = data[0];
        if (data[1].isEmpty()) {
            members = new ArrayList<>();
        } else {
            members = new LinkedList<>(Arrays.asList(data[1].split("\\s*,\\s*")));
        }
        max_members = Long.parseLong(data[2]);
        coins = Long.parseLong(data[3]);
        level = Long.parseLong(data[4]);
        if (data[5].isEmpty()) {
            quests = new ArrayList<>();
        } else {
            quests = new LinkedList<>(Arrays.asList(data[5].split("\\s*,\\s*")));
        }
        List<String> newlist = new ArrayList<>();
        for (String questted : quests) {
            newlist.add(questted.split(",")[0].split(":")[0]);
        }
        List<String> conpare = new ArrayList<>(PvPTeams.call.config.get.getConfigurationSection("quests").getKeys(false));
        if (!changed(newlist, conpare)) {
            PvPTeams.call.textUtils.info("Team " + team + " quests is not up to date! updating...");
            quests.clear();
            for (String quest : PvPTeams.call.config.get.getConfigurationSection("quests").getKeys(false)) {
                quests.add(quest + ":" + 0);
            }
        }
    }

    public void save() {
        PvPTeams.call.database.setValuesTeams(team, owner, joinMembers(), max_members, coins, level, joinQuests());
    }

    public String getTeam() {
        return team;
    }

    public String getOwner() {
        return owner;
    }

    public List<String> getMembers() {
        return members;
    }

    public int getCurrentMembersSize() {
        return members.size();
    }

    public List<OfflinePlayer> getOfflineMembers() {
        ArrayList<OfflinePlayer> list = new ArrayList<>();
        for (String uuid : members) {
            list.add(PvPTeams.call.getServer().getOfflinePlayer(UUID.fromString(uuid)));
        }
        return list;
    }

    public long getMax_members() {
        return max_members;
    }

    public void setMax_members(long set) {
        max_members = set;
    }

    public Long getCoins() {
        return coins;
    }

    public void setCoins(long set) {
        this.coins = set;
    }

    public void addMember(String uuid) {
        members.add(uuid);
    }

    public void removeMember(String uuid) {
        members.remove(uuid);
    }

    private String joinMembers() {
        StringJoiner stringJoiner = new StringJoiner(",");
        for (String member : members) {
            stringJoiner.add(member);
        }
        return stringJoiner.toString();
    }

    private String joinQuests() {
        StringJoiner stringJoiner = new StringJoiner(",");
        for (String quest : quests) {
            stringJoiner.add(quest);
        }
        return stringJoiner.toString();
    }

    public long getLevel() {
        return level;
    }

    public void setLevel(long set) {
        level = set;
    }

    public String getQuest(String quest) {
        int start = 0;
        for (String list : quests) {
            if (list.split(":")[0].equalsIgnoreCase(quest)) {
                return list + "=" + start;
            }
            start++;
        }
        return null;
    }

    public void setQuest(String quest, int index) {
        quests.set(index, quest);
    }

    public  boolean changed(List<String> list, List<String> list1){
        if (list == null && list1 == null){
            return true;
        }
        if(list == null || list1 == null || list.size() != list1.size()){
            return false;
        }
        list = new ArrayList<>(list);
        list1 = new ArrayList<>(list1);
        Collections.sort(list);
        Collections.sort(list1);
        return list.equals(list1);
    }
}