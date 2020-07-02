package me.MathiasMC.PvPTeams.listeners;

import me.MathiasMC.PvPTeams.PvPTeams;
import me.MathiasMC.PvPTeams.gui.GUI;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class InventoryClose implements Listener {

    private final PvPTeams plugin;

    public InventoryClose(final PvPTeams plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void on(InventoryCloseEvent e) {
        if (e.getInventory().getHolder() instanceof GUI) {
            Player player = (Player) e.getPlayer();
            player.updateInventory();
            plugin.getServer().getScheduler().runTaskLater(plugin, player::updateInventory, 3L);
        }
    }
}
