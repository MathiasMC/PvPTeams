package me.MathiasMC.PvPTeams.data;

import me.MathiasMC.PvPTeams.PvPTeams;

public class PlayerConnect {

    private final String uuid;

    private String team;

    private long coins;

    public PlayerConnect(String uuid) {
        this.uuid = uuid;
        String[] data = PvPTeams.call.database.getValuesPlayers(uuid);
        team = data[0];
        coins = Long.parseLong(data[1]);
    }

    public void save() {
        PvPTeams.call.database.setValuesPlayers(uuid, team, coins);
    }

    public String getTeam() {
        return team;
    }

    public boolean hasTeam() {
        if (team != null && !team.isEmpty()) {
            return true;
        }
        return false;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public long getCoins() {
        return coins;
    }

    public void setCoins(long coins) {
        this.coins = coins;
    }
}