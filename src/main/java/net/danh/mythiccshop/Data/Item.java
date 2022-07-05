package net.danh.mythiccshop.Data;

import io.lumine.mythic.bukkit.MythicBukkit;
import net.danh.mythiccshop.MythiccShop;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.Objects;

import static net.danh.dcore.Utils.Player.sendPlayerMessage;
import static net.danh.mythiccshop.File.Files.getlanguagefile;

public class Item {

    public static int getPlayerAmount(HumanEntity player, ItemStack item) {
        final PlayerInventory inv = player.getInventory();
        final ItemStack[] items = inv.getContents();
        int c = 0;
        for (final ItemStack is : items) {
            if (is != null) {
                if (is.isSimilar(item)) {
                    c += is.getAmount();
                }
            }
        }
        return c;
    }

    public static void removeItems(Player player, ItemStack item, long amount) {
        item = item.clone();
        final PlayerInventory inv = player.getInventory();
        final ItemStack[] items = inv.getContents();
        int c = 0;
        for (int i = 0; i < items.length; ++i) {
            final ItemStack is = items[i];
            if (is != null) {
                if (is.isSimilar(item)) {
                    if (c + is.getAmount() > amount) {
                        final long canDelete = amount - c;
                        is.setAmount((int) (is.getAmount() - canDelete));
                        items[i] = is;
                        break;
                    }
                    c += is.getAmount();
                    items[i] = null;
                }
            }
        }
        inv.setContents(items);
        player.updateInventory();
    }

    public static void sellMythiccItem(Player p, String type, Double price, Integer amount) {
        int a = getPlayerAmount(p, MythicBukkit.inst().getItemManager().getItemStack(type).asQuantity(amount));
        if (a >= amount) {
            removeItems(p, MythicBukkit.inst().getItemManager().getItemStack(type), amount);
            EconomyResponse e = MythiccShop.getEconomy().depositPlayer(p, price * amount);
            if (e.transactionSuccess()) {
                sendPlayerMessage(p, Objects.requireNonNull(getlanguagefile().getString("SELL_ITEMS")).replaceAll("%item%", MythicBukkit.inst().getItemManager().getItemStack(type).getItemMeta().getDisplayName()).replaceAll("%price%", String.valueOf(price * amount)).replaceAll("%amount%", String.format("%,d", amount)));
            }
        } else {
            sendPlayerMessage(p, Objects.requireNonNull(getlanguagefile().getString("NOT_ENOUGH_ITEM")).replaceAll("%item%", MythicBukkit.inst().getItemManager().getItemStack(type).getItemMeta().getDisplayName()));
        }
    }

    public static void buyMythiccItem(Player p, String type, Double price, Integer amount) {
        if (MythiccShop.getEconomy().getBalance(p) >= price * amount) {
            EconomyResponse e = MythiccShop.getEconomy().withdrawPlayer(p, price * amount);
            p.getInventory().addItem(MythicBukkit.inst().getItemManager().getItemStack(type).asQuantity(amount));
            if (e.transactionSuccess()) {
                sendPlayerMessage(p, Objects.requireNonNull(getlanguagefile().getString("BUY_ITEMS")).replaceAll("%item%", MythicBukkit.inst().getItemManager().getItemStack(type).getItemMeta().getDisplayName()).replaceAll("%price%", String.valueOf(price * amount)).replaceAll("%amount%", String.format("%,d", amount)));
            }
        } else {
            sendPlayerMessage(p, Objects.requireNonNull(getlanguagefile().getString("NOT_ENOUGH_MONEY")).replaceAll("%money%", String.valueOf(price * amount)));
        }
    }

}
