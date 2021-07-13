package me.oveln.LS;

import me.oveln.ovteleportpoint.main;
import me.oveln.ovteleportpoint.teleporter;
import org.bukkit.Location;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LS {
    public static void Saveteleporters(List<teleporter> data, String path) throws IOException {
        List<Map<String , Object>> file = new ArrayList<>();
        for (int i=0;i<data.size();i++) {
            file.add(data.get(i).LocationSerialize());
            file.add(data.get(i).ItemSerialize());
        }
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(path));
        out.writeObject(file);
        out.flush();
        out.close();
        main.getInstance().getLogger().info("存入了"+data.size()+"个传送点");
    }
    public static void Loadteleporters(List<teleporter> data ,String path) throws IOException {
        List<Map<String , Object>> file = new ArrayList<>();
        ObjectInputStream in = new ObjectInputStream(new FileInputStream(path));
        try {
            file = (List<Map<String , Object>>) in.readObject();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        for (int i=0;i<file.size();i+=2)
            data.add(new teleporter(file.get(i) , file.get(i+1)));
        in.close();
        main.getInstance().getLogger().info("读入了"+data.size()+"个传送点");
    }
    public static void SaveSigns(List<Location> data, String path) throws IOException {
        List<Map<String , Object>> file = new ArrayList<>();
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(path));
        for (Location datum : data) file.add(datum.serialize());
        out.writeObject(file);
        out.flush();
        out.close();
    }
    public static void LoadSigns(List<Location> data , String path) throws IOException {
        List<Map<String , Object>> file = new ArrayList<>();
        ObjectInputStream in = new ObjectInputStream(new FileInputStream(path));
        try {
            file = (List<Map<String , Object>>) in.readObject();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        for (Map<String , Object> datum : file) data.add(Location.deserialize(datum));
    }
}
