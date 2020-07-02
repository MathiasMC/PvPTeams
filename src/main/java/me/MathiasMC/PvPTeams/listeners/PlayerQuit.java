package me.MathiasMC.PvPTeams.listeners;

import me.MathiasMC.PvPTeams.PvPTeams;
import me.MathiasMC.PvPTeams.data.PlayerConnect;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuit implements Listener {

    private final PvPTeams plugin;

    public PlayerQuit(final PvPTeams plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onQuit(PlayerQuitEvent e) {
        String uuid = e.getPlayer().getUniqueId().toString();
        if (plugin.listPlayers().contains(uuid)) {
            PlayerConnect playerConnect = plugin.getPlayer(uuid);
            playerConnect.save();
            if (playerConnect.hasTeam()) {
                plugin.getTeam(playerConnect.getTeam()).save();
            }
        }
    }
}