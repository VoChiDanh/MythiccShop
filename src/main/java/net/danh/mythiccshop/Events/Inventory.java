package net.danh.mythiccshop.Events;

import net.danh.dcore.Utils.Chat;
import net.danh.mythiccshop.File.Files;
import net.danh.mythiccshop.File.Shop;
import net.danh.mythiccshop.Manager.Debug;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.Objects;

import static net.danh.dcore.Utils.Player.sendPlayerMessage;

public class Inventory implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        for (String name : Files.getconfigfile().getStringList("SHOP")) {
            Shop shop = new Shop(name);
            if (e.getView().getTitle().equals(Chat.colorize(Objects.requireNonNull(shop.getConfig().getString("NAME"))))) {
                e.setCancelled(true);
                if (e.getClick() == ClickType.LEFT) {
                    int slot = e.getSlot();
                    for (String names : Objects.requireNonNull(shop.getConfig().getConfigurationSection("ITEMS")).getKeys(false)) {
                        if (shop.getConfig().contains("ITEMS." + names + ".MYTHICC_TYPE")) {
                            if (shop.getConfig().getInt("ITEMS." + names + ".SLOT") == slot) {
                                if (shop.getConfig().getInt("ITEMS." + names + ".SELL_PRICE") < 0) {
                                    sendPlayerMessage(p, Files.getlanguagefile().getString("CAN_NOT_SELL"));
                                    return;
                                }
                                Debug.sell.add(p);
                                Debug.name.put(p, shop.getName());
                                Debug.item.put(p, shop.getConfig().getString("ITEMS." + names + ".MYTHICC_TYPE"));
                                p.closeInventory();
                                sendPlayerMessage(p, Files.getlanguagefile().getString("CHAT_AMOUNT"));
                            }
                        }
                    }
                }
                if (e.getClick() == ClickType.RIGHT) {
                    int slot = e.getSlot();
                    for (String names : Objects.requireNonNull(shop.getConfig().getConfigurationSection("ITEMS")).getKeys(false)) {
                        if (shop.getConfig().contains("ITEMS." + names + ".MYTHICC_TYPE")) {
                            if (shop.getConfig().getInt("ITEMS." + names + ".SLOT") == slot) {
                                if (shop.getConfig().getInt("ITEMS." + names + ".SELL_PRICE") < 0) {
                                    sendPlayerMessage(p, Files.getlanguagefile().getString("CAN_NOT_BUY"));
                                    return;
                                }
                                Debug.buy.add(p);
                                Debug.name.put(p, shop.getName());
                                Debug.item.put(p, shop.getConfig().getString("ITEMS." + names + ".MYTHICC_TYPE"));
                                p.closeInventory();
                                sendPlayerMessage(p, Files.getlanguagefile().getString("CHAT_AMOUNT"));
                            }
                        }
                    }
                }
            }
        }
    }
}
