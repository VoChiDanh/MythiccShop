package net.danh.mythiccshop.Commands;

import net.danh.dcore.Commands.CMDBase;
import net.danh.mythiccshop.File.Shop;
import net.danh.mythiccshop.Manager.Shops;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import static net.danh.dcore.List.Contain.inList;
import static net.danh.dcore.Utils.Player.sendConsoleMessage;
import static net.danh.dcore.Utils.Player.sendPlayerMessage;
import static net.danh.mythiccshop.File.Files.*;

public class CMD extends CMDBase {

    public CMD(JavaPlugin core) {
        super(core, "mythiccshop");
    }

    @Override
    public void playerexecute(Player p, String[] args) {
        if (args.length == 1) {
            if (p.hasPermission("MythiccShop.admin")) {
                if (args[0].equalsIgnoreCase("reload")) {
                    reloadfiles();
                    sendPlayerMessage(p, "&aReloaded");
                }
            }
            if (args[0].equalsIgnoreCase("help")) {
                sendPlayerMessage(p, getlanguagefile().getStringList("HELP.DEFAULT"));
                if (p.hasPermission("mythiccshop.admin")) {
                    sendPlayerMessage(p, getlanguagefile().getStringList("HELP.ADMIN"));
                }
            }
        }
        if (args.length == 2) {
            if (args[0].equalsIgnoreCase("shop")) {
                if (inList(getconfigfile().getStringList("SHOP"), args[1])) {
                    if (p.hasPermission("mythiccshop.shop." + args[1]) || p.hasPermission("mythiccshop.shop.*")) {
                        Shop shop = new Shop(args[1]);
                        if (shop.getConfig().getKeys(false).size() == 0) {
                            sendPlayerMessage(p, "&cShop " + args[1] + " is empty, let's see example.yml in plugin folder to config new shop!");
                            return;
                        }
                        Shops.openShop(p, new Shop(args[1]));
                    }
                }
            }
        }
    }

    @Override
    public void consoleexecute(ConsoleCommandSender c, String[] args) {
        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("reload")) {
                reloadfiles();
            }
            if (args[0].equalsIgnoreCase("help")) {
                sendConsoleMessage(c, getlanguagefile().getStringList("HELP.DEFAULT"));
                sendConsoleMessage(c, getlanguagefile().getStringList("HELP.ADMIN"));
            }
        }
        if (args.length == 3) {
            Player p = Bukkit.getPlayer(args[2]);
            if (p == null) {
                return;
            }
            if (args[0].equalsIgnoreCase("shop")) {
                if (inList(getconfigfile().getStringList("SHOP"), args[1])) {
                    Shop shop = new Shop(args[1]);
                    if (shop.getConfig().getKeys(false).size() == 0) {
                        sendPlayerMessage(p, "&cShop " + args[1] + " is empty, let's see example.yml in plugin folder to config new shop!");
                        return;
                    }
                    Shops.openShop(p, new Shop(args[1]));
                }
            }
        }
    }
}
