package me.oveln.ovteleportpoint;

import me.oveln.LS.LS;
import me.oveln.Listener.BlockBreak;
import me.oveln.command.otpp;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import me.oveln.Listener.BlockClick;
import me.oveln.Listener.InventoryClick;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

public class main extends JavaPlugin {

    public Logger logger = getLogger();
    private final List<teleporter> TeleporterList = new ArrayList<>();
    private final List<Location> signers = new ArrayList<>();
    private final Set<Player> creater = new HashSet<>();
    private final PluginDescriptionFile descriptionFile = getDescription();
    private static main Instance;

    private static final String signerPath = "plugins/OvTeleportPoint/signer.bin";
    private static final String teleporterPath = "plugins/OvTeleportPoint/teleporter.bin";

    @Override
    public void onEnable() {
        Instance = this;

        saveDefaultConfig();
        CheckFile();
        try {
            LS.Loadteleporters(TeleporterList , teleporterPath);
            LS.LoadSigns(signers , signerPath);
        } catch(IOException e) {
            e.printStackTrace();
        }

        getServer().getPluginManager().registerEvents(new InventoryClick() , this);
        getServer().getPluginManager().registerEvents(new BlockClick() , this);
        getServer().getPluginManager().registerEvents(new BlockBreak() , this);
        getCommand("otpp").setExecutor(new otpp());

        String string =descriptionFile.getName()
                +descriptionFile.getVersion()
                +"开启成功    "
                +"作者"
                +descriptionFile.getAuthors().get(0);
        logger.info(string);
    }

    public void onDisable() {
        try {
            LS.Saveteleporters(TeleporterList , teleporterPath);
            LS.SaveSigns(signers , signerPath);
        } catch(IOException e) {
            e.printStackTrace();
        }

        String string =descriptionFile.getName()
                +descriptionFile.getVersion()
                +"关闭成功    "
                +"作者"
                +descriptionFile.getAuthors().get(0);
        logger.info(string);
    }

    public List<teleporter> getTeleportlist() {
        return TeleporterList;
    }
    public List<Location> getSigners() {return signers; }
    public Set<Player> getCreater() {return creater; }

    public static main getInstance() {
        return Instance;
    }

    public void CheckFile() {
        try {
            File file = new File(signerPath);
            file.createNewFile();
            file = new File(teleporterPath);
            file.createNewFile();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }


}
