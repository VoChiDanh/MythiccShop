package net.danh.mythiccshop;

import net.danh.dcore.DCore;
import net.danh.dcore.Utils.File;
import net.danh.mythiccshop.Commands.CMD;
import net.danh.mythiccshop.Data.ShopManger;
import net.danh.mythiccshop.Events.Chat;
import net.danh.mythiccshop.Events.Inventory;
import net.danh.mythiccshop.File.Files;
import net.danh.mythiccshop.File.Shop;
import net.danh.mythiccshop.Manager.Debug;
import net.danh.mythiccshop.Support.v4;
import net.danh.mythiccshop.Support.v5;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;
import java.util.logging.Level;

import static net.danh.mythiccshop.File.Files.*;

public final class MythiccShop extends JavaPlugin {

    private static MythiccShop inst;
    private static Economy econ;
    private static ShopManger shopManger;

    public static Economy getEconomy() {
        return econ;
    }

    public static MythiccShop getInstance() {
        return inst;
    }

    public static ShopManger getShopManger() {
        return shopManger;
    }

    public static void setShopManger(ShopManger shopManger) {
        MythiccShop.shopManger = shopManger;
    }

    public static void loadShop() {
        for (String name : Files.getconfigfile().getStringList("SHOP")) {
            Shop shop = new Shop(name);
            shop.save();
            MythiccShop.getInstance().getLogger().log(Level.INFO, "Loading shop " + name);
            shop.load();
        }
        MythiccShop.getInstance().getLogger().log(Level.INFO, "Loaded " + Files.getconfigfile().getStringList("SHOP").size() + " shop(s)");
    }

    @Override
    public void onEnable() {
        inst = this;
        if (!setupEconomy()) {
            getLogger().severe(String.format("[%s] - Disabled due to no Vault dependency found!", getDescription().getName()));
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        if (getServer().getPluginManager().getPlugin("MythicMobs") != null) {
            String version = Objects.requireNonNull(getServer().getPluginManager().getPlugin("MythicMobs")).getDescription().getVersion();
            if (version.startsWith("4")) {
                getLogger().log(Level.INFO, "Looking for data for compatibility with MythicMobs v4");
                setShopManger(new v4());
                getLogger().log(Level.INFO, "Hooked into MythicMobs v4");
            }
            if (version.startsWith("5")) {
                getLogger().log(Level.INFO, "Looking for data for compatibility with MythicMobs v5");
                setShopManger(new v5());
                getLogger().log(Level.INFO, "Hooked into MythicMobs v5");
            }
        }
        DCore.RegisterDCore(MythiccShop.getInstance());
        new CMD(this);
        getServer().getPluginManager().registerEvents(new Inventory(), this);
        getServer().getPluginManager().registerEvents(new Chat(), this);
        createfiles();
        File.updateFile(MythiccShop.getInstance(), getconfigfile(), "config.yml");
        File.updateFile(MythiccShop.getInstance(), getlanguagefile(), "language.yml");
        loadShop();
    }

    @Override
    public void onDisable() {
        for (Player p : getServer().getOnlinePlayers()) {
            p.closeInventory();
            Debug.sell.remove(p);
            Debug.buy.remove(p);
            Debug.name.remove(p);
        }
        Files.saveconfig();
        Files.savelanguage();
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return true;
    }

}
