package net.danh.mythiccshop.File;

import net.danh.mythiccshop.MythiccShop;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

import static net.danh.mythiccshop.MythiccShop.getInstance;
import static net.danh.mythiccshop.MythiccShop.loadShop;


public class Files {

    private static File configFile;
    private static File languageFile;
    private static FileConfiguration config;
    private static FileConfiguration language;

    public static void createfiles() {
        configFile = new File(getInstance().getDataFolder(), "config.yml");
        languageFile = new File(getInstance().getDataFolder(), "language.yml");
        File exampleFile = new File(getInstance().getDataFolder(), "example.yml");

        if (!configFile.exists()) getInstance().saveResource("config.yml", false);
        if (!languageFile.exists()) getInstance().saveResource("language.yml", false);
        if (!exampleFile.exists()) getInstance().saveResource("example.yml", false);
        language = new YamlConfiguration();
        config = new YamlConfiguration();
        FileConfiguration example = new YamlConfiguration();

        try {
            language.load(languageFile);
            config.load(configFile);
            example.load(exampleFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public static FileConfiguration getconfigfile() {
        return config;
    }

    public static FileConfiguration getlanguagefile() {
        return language;
    }

    public static void reloadfiles() {
        MythiccShop.getInstance().getLogger().log(Level.INFO, "Reloading.....");
        language = YamlConfiguration.loadConfiguration(languageFile);
        config = YamlConfiguration.loadConfiguration(configFile);
        loadShop();
    }

    public static void saveconfig() {
        try {
            config.save(configFile);
        } catch (IOException ignored) {
        }
    }

    public static void savelanguage() {
        try {
            language.save(languageFile);
        } catch (IOException ignored) {
        }
    }

}
