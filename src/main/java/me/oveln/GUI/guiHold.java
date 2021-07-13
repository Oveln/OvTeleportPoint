package me.oveln.GUI;

import me.oveln.ovteleportpoint.main;
import me.oveln.ovteleportpoint.teleporter;
import me.oveln.util.Chat;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.util.List;

import static me.oveln.util.ItemStack.MakeItemStack;

public class guiHold implements InventoryHolder {
    protected String name; //GUI标签
    protected int Page , size , id; //页数 ， 高度 ， 当前GUI对应的SignerID
    Inventory gui;
    public guiHold(String name , int Page , int size ,int  id) {
        this.name = name;
        this.Page = Page;
        this.size = size;
        this.id = id;
        gui = Bukkit.createInventory(this , size*9 , name);
    }

    public guiHold(guiHold pre , int x) {
        this.name = pre.name;
        this.Page = pre.Page+x;
        this.size = pre.size;
        this.id = pre.id;
        gui = Bukkit.createInventory(this , size*9 , name);
    }

    public Inventory getInventory() {
        return this.gui;
    }

    public void setGUI() {
        if (name.equals(Chat.t(main.getInstance().getConfig().getString(Chat.t("GUITitle"))))) {
            List<teleporter> TeleporterList = main.getInstance().getTeleportlist();
            int start = (Page-1)*9 , end = Math.min(start + 9 , TeleporterList.size()) , Slot = 0;
            for (int i = start; i<end; i++) {
                gui.setItem(Slot, TeleporterList.get(i).getItem());
                Slot = Slot +1;
            }
            ItemStack item;
            for (int i=9;i<18;i++) {
                item = me.oveln.util.ItemStack.MakeItemStack(Material.BLACK_STAINED_GLASS_PANE, " ");
                gui.setItem(i, item);
            }
            if (Page*9<TeleporterList.size()) {
                item = me.oveln.util.ItemStack.MakeItemStack(Material.PAPER, "下一页");
                gui.setItem(26, item);
            }
            if (Page != 1) {
                item = me.oveln.util.ItemStack.MakeItemStack(Material.PAPER, "上一页");
                gui.setItem(18, item);
            }
            item = me.oveln.util.ItemStack.MakeItemStack(Material.SPRUCE_SIGN , "第"+Page+"页");
            gui.setItem(22 , item);
        }
    }

    public static guiHold getHold(Inventory inv) {
        InventoryHolder ret = inv.getHolder();
        if (!(ret instanceof guiHold)) return null;
        return (guiHold) ret;
    }

    public String getName() {return name;}
    public int getPage() {return Page;}
    public int getID() {return id;}
}
