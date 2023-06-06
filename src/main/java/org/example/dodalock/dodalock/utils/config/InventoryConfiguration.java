package org.example.dodalock.dodalock.utils.config;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.example.dodalock.dodalock.DodaLockMain;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public class InventoryConfiguration {
    private static File file;
    private static FileConfiguration fileInventoryConfiguration;

    public InventoryConfiguration() {
        file = new File(DodaLockMain.getPlugin().getDataFolder(), "inventories/data.yml");

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                System.out.println("Oops, error creating the configuration file.");
            }
            fileInventoryConfiguration = YamlConfiguration.loadConfiguration(file);
            setup();
        }
        else fileInventoryConfiguration = YamlConfiguration.loadConfiguration(file);

        getFileInventoryConfiguration().options().copyDefaults(true);
        save();
    }

    public void setup() {
        getFileInventoryConfiguration().createSection("inventory");
    }

    public static void serialize(String bunchKeys, Inventory inventory) {
        getFileInventoryConfiguration().set("inventory." + bunchKeys + ".date_last_serialize", LocalDateTime.now().toString());
        for (int i = 0; i < inventory.getSize(); i++) {
            if (inventory.getItem(i) != null) {
                getFileInventoryConfiguration().set("inventory." + bunchKeys + ".content." + i, inventory.getItem(i));
            }
            else getFileInventoryConfiguration().set("inventory." + bunchKeys + "." + i, null);
        }
        save();
        reload();
    }

    public static void deserialize(String bunchKeys, Inventory inventory) {
        for (int i = 0; i < inventory.getSize(); i++) {
            if (getFileInventoryConfiguration().contains("inventory." + bunchKeys + ".content." + i)) {
                inventory.setItem(i, getFileInventoryConfiguration().getItemStack("inventory." +
                        "." + bunchKeys + ".content." + i));
            }
        }
    }

    public static void save() {
        try {
            fileInventoryConfiguration.save(file);
        }
        catch (IOException e) {
            System.out.println("Couldn't save configuration file");
        }
    }

    public static void reload() {
        fileInventoryConfiguration = YamlConfiguration.loadConfiguration(file);
    }

    public static FileConfiguration getFileInventoryConfiguration() { return fileInventoryConfiguration; }
}
