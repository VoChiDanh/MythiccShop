package net.danh.mythiccshop.File;

import net.danh.mythiccshop.MythiccShop;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class Shop {


    private final String name;
    private File file;
    private FileConfiguration config;

    public Shop(String name) {
        this.name = name;
        this.file = new File(MythiccShop.getInstance().getDataFolder() + File.separator + "Shop", name + ".yml");
        this.config = YamlConfiguration.loadConfiguration(this.file);
    }

    public void load() {
        File folder = new File(MythiccShop.getInstance().getDataFolder(), "Shop");

        if (!folder.exists()) {
            folder.mkdirs();
        }

        this.file = new File(folder, this.name + ".yml");
        if (!this.file.exists()) {
            try {
                this.file.createNewFile();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        this.config = YamlConfiguration.loadConfiguration(this.file);
        if (!config.contains("NAME") && !config.contains("SIZE")) {
            try {
                MythiccShop.getInstance().getLogger().log(Level.INFO, "Creating shop " + name + "...");
                config.set("NAME", "&0Shop " + name);
                config.set("SIZE", 1);
                config.set("ITEMS.FILL.MATERIAL", "BLACK_STAINED_GLASS_PANE");
                config.set("ITEMS.FILL.NAME", "&7 ");
                config.set("ITEMS.FILL.HIDE_FLAG", true);
                config.set("ITEMS.FILL.GLOW", false);
                List<Integer> slots = new ArrayList<>();
                slots.add(0);
                slots.add(1);
                slots.add(2);
                slots.add(3);
                slots.add(5);
                slots.add(6);
                slots.add(7);
                slots.add(8);
                config.set("ITEMS.FILL.SLOTS", slots);
                List<String> lore = new ArrayList<>();
                lore.add("&7 ");
                config.set("ITEMS.FILL.LORE", lore);
                config.set("ITEMS.EXAMPLE_ITEM.MYTHICC_TYPE", "EXAMPLE_ITEM");
                config.set("ITEMS.EXAMPLE_ITEM.SELL_PRICE", 100);
                config.set("ITEMS.EXAMPLE_ITEM.BUY_PRICE", 500);
                config.set("ITEMS.EXAMPLE_ITEM.SLOT", 4);
                config.save(file);
                MythiccShop.getInstance().getLogger().log(Level.INFO, "Create new shop " + name + " complete!");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        MythiccShop.getInstance().getLogger().log(Level.INFO, "Loaded " + Files.getconfigfile().getStringList("SHOP").size() + " shop(s)");
    }

    public String getName() {
        return name;
    }

    public File getFile() {
        return this.file;
    }

    public FileConfiguration getConfig() {
        return this.config;
    }

    public void save() {
        try {
            this.config.save(this.file);
            MythiccShop.getInstance().getLogger().log(Level.INFO, "Loading shop " + name);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
