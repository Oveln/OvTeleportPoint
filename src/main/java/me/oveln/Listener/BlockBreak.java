package me.oveln.Listener;

import me.oveln.ovteleportpoint.main;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BlockBreak implements Listener {
    @EventHandler
    public boolean onBlockBreak(BlockBreakEvent event) {
        if (event.getBlock().getType() == Material.OAK_SIGN && main.getInstance().getSigners().contains(event.getBlock().getLocation())) {
            event.setCancelled(true);
        }
        return true;
    }
}
