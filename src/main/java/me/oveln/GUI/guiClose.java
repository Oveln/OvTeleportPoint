package me.oveln.GUI;

import me.oveln.ovteleportpoint.main;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class guiClose {
    public static void close(Player player) {
        new closetask(player).runTaskLater(main.getInstance() , 1);
    }

    public static class closetask extends BukkitRunnable {
        Player player;
        public closetask(Player player) {
            this.player = player;
        }
        public void run() {
            player.closeInventory();
        }
    }
}
