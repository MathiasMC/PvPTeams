package me.MathiasMC.PvPTeams.listeners;

import me.MathiasMC.PvPTeams.PvPTeams;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

public class PlayerLogin implements Listener {

    private final PvPTeams plugin;

    public PlayerLogin(final PvPTeams plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onLogin(PlayerLoginEvent e) {
        String uuid = e.getPlayer().getUniqueId().toString();
        plugin.database.insertPlayer(uuid);
        if (!plugin.listPlayers().contains(uuid)) {
            plugin.loadPlayer(uuid);
        }
    }
}