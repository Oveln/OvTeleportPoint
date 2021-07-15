package me.oveln.Listener;

import me.oveln.GUI.guiHold;
import me.oveln.ovteleportpoint.main;
import me.oveln.ovteleportpoint.teleporter;
import me.oveln.util.Chat;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;
import java.util.Objects;

public class BlockClick implements Listener {
    @EventHandler
    public Boolean onBlockClick(PlayerInteractEvent event) {
        if (event.getClickedBlock() == null) return true;
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK && main.getInstance().getCreater().contains(event.getPlayer())) {
            if (event.getClickedBlock().getType() != Material.OAK_SIGN) {
                event.getPlayer().sendMessage("&c这不是个告示牌");
            }
            Sign sign = (Sign) event.getClickedBlock().getState();
            main.getInstance().getCreater().remove(event.getPlayer());
            if (!main.getInstance().getSigners().contains(event.getClickedBlock().getLocation()))
                main.getInstance().getSigners().add(event.getClickedBlock().getLocation());
            sign.setLine(1, Chat.t("&c[目标]"));
            sign.update();
            event.getPlayer().sendMessage(Chat.t("&2创建一个传送牌"));
            return true;
        }
        if (event.getClickedBlock().getType() == Material.OAK_SIGN) {
            Sign sign = (Sign) event.getClickedBlock().getState();
            if (!main.getInstance().getSigners().contains(event.getClickedBlock().getLocation())) return true;
            if (event.getAction() == Action.RIGHT_CLICK_BLOCK && event.getPlayer().isSneaking()) {
                int ID = -1;
                String name = sign.getLine(2);
                List<teleporter> teleporterList = main.getInstance().getTeleportlist();
                for (int i=0;i<teleporterList.size();i++)
                    if (teleporterList.get(i).getName().equals(name))
                        ID = i;
                if (ID == -1) {
                    event.getPlayer().sendMessage(Chat.t("&c请设置传送目标"));
                    return true;
                }
                Player player =event.getPlayer();
//                player.setVelocity(new Vector(0,1000,0));
                new teleporttask(event.getPlayer() , main.getInstance().getTeleportlist().get(ID).getLocation().clone().setDirection(player.getLocation().getDirection())).runTaskLater(main.getInstance(),1);
                return true;
            }
            if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                int ID = main.getInstance().getSigners().indexOf(event.getClickedBlock().getLocation());
                guiHold gui = new guiHold(Objects.requireNonNull(Chat.t(main.getInstance().getConfig().getString("GUITitle"))), 1 , 3 , ID);
                gui.setGUI();
                event.getPlayer().openInventory(gui.getInventory());
                return true;
            }
        }
        return true;
    }
    public static class teleporttask extends BukkitRunnable {
        private final Player player;
        private final Location location;
        public teleporttask(Player player , Location location) {
            this.player = player;
            this.location = location;
        }
        public void run() {
            player.teleport(location.clone().setDirection(player.getLocation().getDirection()));
        }
    }
}
