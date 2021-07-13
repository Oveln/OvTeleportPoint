package me.oveln.GUI;

import me.oveln.ovteleportpoint.main;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.scheduler.BukkitRunnable;

public class guiOpen {
    public static void open(Player player , Inventory gui) {
        new guiOpen.opentask(player , gui).runTaskLater(main.getInstance() , 1);
    }

    public static class opentask extends BukkitRunnable {
        Player player;
        Inventory gui;
        public opentask(Player player , Inventory gui) {
            this.player = player;
            this.gui = gui ;
        }
        public void run() {
            player.openInventory(gui);
        }
    }
}
