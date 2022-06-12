package net.danh.mythiccshop.File;

import net.danh.mythiccshop.MythiccShop;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
