package org.example.dodalock.dodalock.utils.config;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.example.dodalock.dodalock.DodaLockMain;

import java.io.File;
import java.io.IOException;

public class Configurations {
    private static MainConfiguration CONFIG;
    private static LanguageConfiguration LANGUAGE;
    private static InventoryConfiguration INVENTORY;
    private static LocksConfiguration LOCKS;

    public static void setup() {
        CONFIG = new MainConfiguration();
        LANGUAGE = new LanguageConfiguration(getConfig().getLanguage());
        LOCKS = new LocksConfiguration();
        INVENTORY = new InventoryConfiguration();

        save();
    }

    public static void save() {
        getConfig().save();
        getLocks().save();
        getInventory().save();
    }

    public static void reload() {
        CONFIG = new MainConfiguration();
        LANGUAGE = new LanguageConfiguration(getConfig().getLanguage());
        LOCKS = new LocksConfiguration();
        INVENTORY = new InventoryConfiguration();
    }

    public static LanguageConfiguration getLanguage() { return LANGUAGE; }

    public static MainConfiguration getConfig() { return CONFIG; }

    public static InventoryConfiguration getInventory() { return INVENTORY; }

    public static LocksConfiguration getLocks() { return LOCKS; }
}
