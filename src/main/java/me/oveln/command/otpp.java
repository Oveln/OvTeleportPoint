package me.oveln.command;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import me.oveln.ovteleportpoint.main;
import me.oveln.ovteleportpoint.teleporter;
import me.oveln.util.Chat;

import java.util.ArrayList;
import java.util.List;

public class otpp implements CommandExecutor , TabCompleter {

    private final String[] HelpMessage = {"/otpp signadd                               创建一个传送牌",
                                          "/otpp signdel                               删除一个传送牌",
                                          "/otpp signlist                              传送牌列表",
                                          "/otpp otpplist                              传送点列表",
                                          "/otpp otppdel <ID>                          删除传送点",
                                          "/otpp otppadd <传送点名称> <描述1> <描述2> ..   创建一个传送点"};

    @Override
    public boolean onCommand(CommandSender sender , Command cmd , String label , String[] args) {
        if (sender instanceof Player player) {
//            if (!player.hasPermission("ovteleportpoint.*")) return false;
            if (args.length == 0) {
                Chat.Sendstrings(player , HelpMessage);
                return true;
            }
            if (args.length == 1) {
                if (args[0].equals("otpplist") && player.isOp()) {
                    List<teleporter> teleporterList = main.getInstance().getTeleportlist();
                    for (int i = 0;i<teleporterList.size();i++)
                        player.sendMessage(Chat.t("ID:&2"+i+"&f            "+teleporterList.get(i).getName()));
                    return true;
                }
                if (args[0].equals("signlist") && player.isOp()) {
                    List<Location> signs = main.getInstance().getSigners();
                    for (int i=0;i<signs.size();i++)
                        player.sendMessage(Chat.t("ID:&2"+i+"&f            "+signs.get(i).getBlockX()+" "+signs.get(i).getBlockY()+" "+signs.get(i).getBlockZ()));
                    return true;
                }
                if (args[0].equals("signadd") && player.isOp()) {
                    if (!main.getInstance().getCreater().contains(player)) {
                        main.getInstance().getCreater().add(player);
                        player.sendMessage(Chat.t("&c右键&f设置传送牌，&c再次输入命令&f取消"));
                    } else {
                        main.getInstance().getCreater().remove(player);
                        player.sendMessage(Chat.t("&c取消成功"));
                    }
                    return true;
                }
                return false;
            }
            if (args.length == 2) {
                if (args[0].equals("signdel") && player.isOp()) {
                    int ID = Integer.parseInt(args[1]);
                    List<Location> signs = main.getInstance().getSigners();
                    if (ID>=signs.size()) {
                        player.sendMessage(Chat.t("&c好屑，根本没有这个传送牌"));
                        return true;
                    }
                    signs.remove(ID);
                    player.sendMessage(Chat.t("&2删除成功"));
                    return true;
                }
                if (args[0].equals("otppdel") && player.isOp()) {
                    int ID = Integer.parseInt(args[1]);
                    List<teleporter> teleporterList = main.getInstance().getTeleportlist();
                    if (ID>=teleporterList.size()) {
                        player.sendMessage(Chat.t("&c好屑，根本没有这个传送点"));
                        return true;
                    }
                    teleporterList.remove(ID);
                    player.sendMessage(Chat.t("&2删除成功"));
                    return true;
                }
            }
            if (args[0].equals("otppadd") && player.isOp()) {
                PlayerInventory bag = player.getInventory();
                if (bag.getItem(bag.getHeldItemSlot()) == null) {
                    player.sendMessage(Chat.t("请拿着物品"));
                    return true;
                }
                String name = args[1];
                List<String> lores = new ArrayList<>();
                for (int i = 2; i<args.length; i++)
                    lores.add(Chat.t(args[i]));
                lores.add("X:"+player.getLocation().getBlockX()+"Y:"+player.getLocation().getBlockY()+"Z:"+player.getLocation().getBlockZ());
                ItemStack item = bag.getItem(bag.getHeldItemSlot()).clone();assert item != null;
                ItemMeta meta = item.getItemMeta();assert meta != null;
                meta.setLore(lores);
                meta.setDisplayName(Chat.t(name));
                item.setItemMeta(meta);
                teleporter t = new teleporter(player.getLocation() , item);
                if (!main.getInstance().getTeleportlist().contains(t)) {
                    main.getInstance().getTeleportlist().add(t);
                    player.sendMessage(Chat.t("创建成功"));
                } else {
                    player.sendMessage(Chat.t("&c已经有相同的坐标点了"));
                }
                return true;
            }
            return false;
        }
        sender.sendMessage(Chat.t("&c该命令只能由玩家发出"));
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender , Command cmd , String label , String[] args) {
        List<String> ret = new ArrayList<>();
        if (args.length == 1) {
            ret.add("signadd");
            ret.add("otppadd");
            ret.add("otppdel");
            ret.add("otpplist");
        }
        if (args.length ==2 ) {
            if (args[0].equals("otppadd")) {
                ret.add("<传送点名称>");
            }
            if (args[0].equals("otppdel")) {
                ret.add("<传送点ID>");
            }
        }
        if (args.length >2) {
            if (args[0].equals("otppadd")) {
                ret.add("<描述>");
            }
        }
        return ret;
    }
}
