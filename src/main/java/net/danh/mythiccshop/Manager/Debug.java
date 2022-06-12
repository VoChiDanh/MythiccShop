package net.danh.mythiccshop.Manager;

import net.danh.dcore.Utils.Chat;
import net.danh.mythiccshop.File.Files;
import net.danh.mythiccshop.MythiccShop;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;

public class Debug {

    public static Set<Player> sell = new HashSet<>();
    public static Set<Player> buy = new HashSet<>();

    public static HashMap<Player, String> name = new HashMap<>();
    public static HashMap<Player, String> item = new HashMap<>();

    public static void debugMythiccShop(String msg) {
        if (Files.getconfigfile().getBoolean("DEBUG")) {
            MythiccShop.getInstance().getLogger().log(Level.INFO, Chat.colorize(msg));
        }
    }
}
