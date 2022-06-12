package net.danh.mythiccshop.Data;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public interface ShopManger {

    boolean consumeItem(Player player, int count, ItemStack mat);

    void sellMythiccItem(Player p, String type, Integer price, Integer amount);

    void buyMythiccItem(Player p, String type, Integer price, Integer amount);
}
