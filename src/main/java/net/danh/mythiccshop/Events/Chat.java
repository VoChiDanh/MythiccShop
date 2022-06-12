package net.danh.mythiccshop.Events;

import net.danh.mythiccshop.Data.Item;
import net.danh.mythiccshop.File.Shop;
import net.danh.mythiccshop.Manager.Debug;
import net.danh.mythiccshop.Manager.Shops;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;

import static net.danh.dcore.Random.Number.isInteger;

public class Chat implements Listener {

    @EventHandler
    public void onChat(PlayerChatEvent e) {
        Player p = e.getPlayer();
        String msg = ChatColor.stripColor(e.getMessage());
        if (Debug.sell.contains(p)) {
            Shop shop = new Shop(Debug.name.get(p));
            if (isInteger(msg)) {
                Item.sellMythiccItem(p, shop.getConfig().getString("ITEMS." + Debug.item.get(p) + ".MYTHICC_TYPE"), shop.getConfig().getInt("ITEMS." + Debug.item.get(p) + ".SELL_PRICE"), Integer.parseInt(msg));
                Debug.sell.remove(p);
                Debug.name.remove(p, shop.getName());
                Shops.openShop(p, shop);
            }
            if (msg.equalsIgnoreCase("exit")) {
                Debug.sell.remove(p);
                Debug.item.remove(p);
                Debug.name.remove(p, shop.getName());
                Shops.openShop(p, shop);
            }
            e.setCancelled(true);
        }
        if (Debug.buy.contains(p)) {
            Shop shop = new Shop(Debug.name.get(p));
            if (isInteger(msg)) {
                Item.buyMythiccItem(p, shop.getConfig().getString("ITEMS." + Debug.item.get(p) + ".MYTHICC_TYPE"), shop.getConfig().getInt("ITEMS." + Debug.item.get(p) + ".BUY_PRICE"), Integer.parseInt(msg));
                Debug.buy.remove(p);
                Debug.name.remove(p, shop.getName());
                Shops.openShop(p, shop);
            }
            if (msg.equalsIgnoreCase("exit")) {
                Debug.buy.remove(p);
                Debug.item.remove(p);
                Debug.name.remove(p, shop.getName());
                Shops.openShop(p, shop);
            }
            e.setCancelled(true);
        }
    }
}

