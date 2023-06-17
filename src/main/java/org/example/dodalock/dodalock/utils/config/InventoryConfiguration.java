package org.example.dodalock.dodalock.utils.config;

import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.example.dodalock.dodalock.DodaLockMain;
import org.example.dodalock.dodalock.items.ItemsManager;
import org.example.dodalock.dodalock.utils.FormattableUtils;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class InventoryConfiguration {
    private static File file;
    private FileConfiguration fileInventoryConfiguration;

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

    public void serialize(String bunchKeys, Inventory inventory) {
        getFileInventoryConfiguration().set("inventory." + bunchKeys + ".date_last_serialize", LocalDateTime.now().toString());
        for (int i = 0; i < inventory.getSize(); i++) {
            if (inventory.getItem(i) != null) {
                getFileInventoryConfiguration().set("inventory." + bunchKeys + ".content." + i, inventory.getItem(i));
            }
            else getFileInventoryConfiguration().set("inventory." + bunchKeys + ".content." + i, null);
        }
        save();
        reload();
    }

    public void deserialize(String bunchKeys, Inventory inventory) {
        for (int i = 0; i < inventory.getSize(); i++) {
            if (getFileInventoryConfiguration().contains("inventory." + bunchKeys + ".content." + i)) {
                inventory.setItem(i, getFileInventoryConfiguration().getItemStack("inventory." +
                        "." + bunchKeys + ".content." + i));
            }
        }
    }

    public void save() {
        try {
            fileInventoryConfiguration.save(file);
        }
        catch (IOException e) {
            System.out.println("Couldn't save configuration file");
        }
    }

    public void reload() {
        fileInventoryConfiguration = YamlConfiguration.loadConfiguration(file);
    }

    public void addBunchKeys(String bunchKeys) {
        getFileInventoryConfiguration().createSection("inventory." + bunchKeys);

        List<String> bunchKeysList = getFileInventoryConfiguration().getStringList("inventory.bunch_of_keys_list");
        bunchKeysList.add(bunchKeys);
        getFileInventoryConfiguration().set("inventory.bunch_of_keys_list", null);
        getFileInventoryConfiguration().set("inventory.bunch_of_keys_list", bunchKeysList);
    }

    public void removeBunchOfKeys(String bunchKeys) {
        getFileInventoryConfiguration().set("inventory." + bunchKeys, null);

        List<String> bunchKeysList = getFileInventoryConfiguration().getStringList("inventory.bunch_of_keys_list");
        bunchKeysList.remove(bunchKeys);
        getFileInventoryConfiguration().set("inventory.bunch_of_keys_list", null);
        getFileInventoryConfiguration().set("inventory.bunch_of_keys_list", bunchKeysList);
    }

    public boolean isBunchKeys(String bunchKeys) {
        return bunchKeys != null && !bunchKeys.equals("") &&
                getFileInventoryConfiguration().contains("inventory." + bunchKeys);
    }

    public List<ItemStack> getKeysInBunchKeys(String bunchKeys) {
        List<ItemStack> itemStackList = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            if (getFileInventoryConfiguration().contains("inventory." + bunchKeys + ".content." + i)) {
                itemStackList.add(getFileInventoryConfiguration().getItemStack("inventory." +
                        bunchKeys + ".content." + i));
            }
        }
        return itemStackList;
    }

    public boolean checkDoorWithBunchKeys(Location location, String idBunchKeys) {
        boolean isTrueKeysInBunch = false;

        if (idBunchKeys != null && !idBunchKeys.equals("") && isBunchKeys(idBunchKeys)) {
            for (ItemStack item : getKeysInBunchKeys(idBunchKeys)) {
                if (ItemsManager.isUsedKey(item, location) || ItemsManager.isMasterKey(item)) {
                    isTrueKeysInBunch = true;
                    break;
                }
            }
        }
        return isTrueKeysInBunch;
    }

    public boolean isUniqueUuidBunchKeys(String other_id) {
        boolean isUniqueUuid = true;
        List<String> idList = getBunchKeysData();
        for (String id : idList) {
            if (id.equals(other_id)) {
                isUniqueUuid = false;
                break;
            }
        }
        return isUniqueUuid;
    }

    public List<String> getBunchKeysData() {
        return getFileInventoryConfiguration().getStringList("inventory.bunch_of_keys_list");
    }

    public LocalDateTime getLastDateSerialize(String bunchKeys) {
        String stringDate = getFileInventoryConfiguration().getString("inventory." +
                bunchKeys + ".date_last_serialize");
        if (stringDate != null)
            return LocalDateTime.parse(stringDate);
        return null;
    }

    public FileConfiguration getFileInventoryConfiguration() { return fileInventoryConfiguration; }
}
