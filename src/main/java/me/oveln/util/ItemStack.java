package me.oveln.util;

import org.bukkit.Material;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class ItemStack {
    public static org.bukkit.inventory.ItemStack MakeItemStack(Material x , String name) {
        org.bukkit.inventory.ItemStack ret =new org.bukkit.inventory.ItemStack(x);
        ItemMeta meta = ret.getItemMeta();
        assert meta != null;
        meta.setDisplayName(name);
        ret.setItemMeta(meta);
        return ret;
    }

    public static org.bukkit.inventory.ItemStack MakeItemStack(Material x , String name , List<String> lore) {
        org.bukkit.inventory.ItemStack ret =new org.bukkit.inventory.ItemStack(x);
        ItemMeta meta = ret.getItemMeta();
        assert meta != null;
        meta.setDisplayName(name);
        meta.setLore(lore);
        ret.setItemMeta(meta);
        return ret;
    }
}
