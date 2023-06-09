package org.example.dodalock.dodalock.utils.config;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.example.dodalock.dodalock.DodaLockMain;

import java.io.File;
import java.io.IOException;

public class Configurations {
    private static File file;
    private static FileConfiguration fileConfiguration;
    private static MainConfiguration CONFIG;
    private static LanguageConfiguration LANGUAGE;
    private static InventoryConfiguration INVENTORY;
    private static LocksConfiguration LOCKS;
    private static final String language = "en_us";
    private static final boolean clearBunchKeysInventory = true;
    private static final int verificationPeriod = 3;

    public static void setup() {
        file = new File(DodaLockMain.getPlugin().getDataFolder(), "config.yml");

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                System.out.println("Oops, error creating the configuration file.");
            }
            fileConfiguration = YamlConfiguration.loadConfiguration(file);
            CONFIG = new MainConfiguration(fileConfiguration);
            getConfig().setup(language, clearBunchKeysInventory, verificationPeriod);
        }
        else {
            fileConfiguration = YamlConfiguration.loadConfiguration(file);
            CONFIG = new MainConfiguration(fileConfiguration);
            getConfig().setup(language, clearBunchKeysInventory, verificationPeriod);
        }
        LANGUAGE = new LanguageConfiguration(getConfig().getLanguage());
        LOCKS = new LocksConfiguration();
        INVENTORY = new InventoryConfiguration();

        getFileConfiguration().options().copyDefaults(true);
        save();
    }

    public static void save() {
        try {
            fileConfiguration.save(file);
        }
        catch (IOException e) {
            System.out.println("Couldn't save configuration file");
        }
    }

    public static void reload() {
        fileConfiguration = YamlConfiguration.loadConfiguration(file);
        CONFIG = new MainConfiguration(fileConfiguration);
        getConfig().setup("en_us", true, 1);
        LANGUAGE = new LanguageConfiguration(getConfig().getLanguage());
        LOCKS = new LocksConfiguration();
    }

    public static LanguageConfiguration getLanguage() { return LANGUAGE; }

    public static MainConfiguration getConfig() { return CONFIG; }

    public static InventoryConfiguration getInventory() { return INVENTORY; }

    public static LocksConfiguration getLocks() { return LOCKS; }

    public static FileConfiguration getFileConfiguration() { return fileConfiguration; }
}
