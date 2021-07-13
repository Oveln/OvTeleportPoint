package me.oveln.Listener;

import me.oveln.ovteleportpoint.main;
import me.oveln.util.Chat;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import me.oveln.GUI.guiHold;

import static me.oveln.GUI.guiClose.close;
import static me.oveln.GUI.guiOpen.open;

public class InventoryClick implements Listener {
    @EventHandler
    public Boolean onInventoryClick(InventoryClickEvent event) {
        guiHold holder = guiHold.getHold(event.getInventory());
        if (holder == null) return true; else event.setCancelled(true);
        if (!(event.getWhoClicked() instanceof Player)) return true;
        if (event.getSlot()<0 || event.getCurrentItem() == null || event.getSlotType() == InventoryType.SlotType.QUICKBAR) return true;
        if (guiHold.getHold(event.getInventory()).getName().equals(Chat.t(main.getInstance().getConfig().getString("GUITitle")))) {
            Inventory gui = event.getInventory();
            ItemStack item = event.getCurrentItem();
            if (item.getItemMeta().getDisplayName().equals("下一页")) {
                close((Player) event.getWhoClicked());
                guiHold g = new guiHold(guiHold.getHold(event.getInventory()) , 1);
                g.setGUI();
                open((Player) event.getWhoClicked() , g.getInventory());
                return true;
            }
            if (item.getItemMeta().getDisplayName().equals("上一页")) {
                close((Player) event.getWhoClicked());
                guiHold g = new guiHold(guiHold.getHold(event.getInventory()) , -1);
                g.setGUI();
                open((Player) event.getWhoClicked() , g.getInventory());
                return true;
            }
            if (event.getSlot()<9 && !item.getItemMeta().getDisplayName().equals(" ") && item.getType() != Material.SPRUCE_SIGN) {
                int ID = holder.getID() ;
//                main.getInstance().getSigners().get(ID).Change((holder.getPage()-1)*9+event.getSlot());
                Sign sign = (Sign) main.getInstance().getSigners().get(ID).getBlock().getState();
                sign.setLine(2 , event.getCurrentItem().getItemMeta().getDisplayName());
                sign.update();
                close((Player) event.getWhoClicked());
                event.getWhoClicked().sendMessage("改变目标地点为" + event.getCurrentItem().getItemMeta().getDisplayName());
                return true;
            }
        }
        return true;
    }
}
