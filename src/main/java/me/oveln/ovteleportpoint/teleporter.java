package me.oveln.ovteleportpoint;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class teleporter {

    private final Location location;
    private final ItemStack item;

    public teleporter(Location location , ItemStack item) {
        this.location = location;
        this.item = item;
    }
    public teleporter(Map<String , Object> dataL , Map<String , Object> dataI) {
        this.location = LocationDeserialize(dataL);
        this.item = ItemDeSerialize(dataI);
    }

    public ItemStack getItem() {
        return item;
    }
    public Location getLocation() { return location;}

    public Map<String , Object> LocationSerialize(){
        return location.serialize();
    }
    public Map<String , Object> ItemSerialize(){
        Map<String , Object> ret = new HashMap<String,Object>();
        ret.put("name" , item.getItemMeta().getDisplayName());
        ret.put("lore" , item.getItemMeta().getLore());
        ret.put("type" , item.getType());
        return ret;
    }
    public static ItemStack ItemDeSerialize(Map<String , Object> data) {
        ItemStack item = new ItemStack((Material) data.get("type"));
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName((String) data.get("name"));
        meta.setLore((List<String>)data.get("lore"));
        item.setItemMeta(meta);
        return item;
    }
    public static Location LocationDeserialize(Map<String , Object> data) {
        Location ret = Location.deserialize(data);
        return ret;
    }

    public String getName() {
        return item.getItemMeta().getDisplayName();
    }
}
