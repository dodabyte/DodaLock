package org.example.dodalock.dodalock.utils.config;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.example.dodalock.dodalock.DodaLockMain;

import java.io.*;
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

    public static void serialize(Player player, String bunchKeys, Inventory inventory) {
        for (int i = 0; i < inventory.getSize(); i++) {
            if (inventory.getItem(i) != null) {
                getFileInventoryConfiguration().set("inventory." + player.getName() + "." + bunchKeys + "." + i, inventory.getItem(i));
            }
            else getFileInventoryConfiguration().set("inventory." + player.getName() + "." + bunchKeys + "." + i, null);
        }
        save();
        reload();
    }

    public static void deserialize(Player player, String bunchKeys, Inventory inventory) {
        for (int i = 0; i < inventory.getSize(); i++) {
            if (getFileInventoryConfiguration().contains("inventory." + player.getName() + "." + bunchKeys + "." + i)) {
                inventory.setItem(i, getFileInventoryConfiguration().getItemStack("inventory." + player.getName() +
                        "." + bunchKeys + "." + i));
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
