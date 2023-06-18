package org.example.dodalock.dodalock.items;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.example.dodalock.dodalock.DodaLockMain;
import org.example.dodalock.dodalock.utils.FormattableUtils;
import org.example.dodalock.dodalock.utils.config.Configurations;

public class ItemsManager {
    private static final Material locksMaterial = Material.BOOK;
    private static final Material keysMaterial = Material.WOODEN_HOE;

    private static CustomItem codeLock;
    private static CustomItem lock;
    private static CustomItem key;
    private static CustomItem masterKey;
    private static CustomItem bunchKeys;

    public static void initializeItems() {
        codeLock = new CustomItem(locksMaterial, 1, "items_name.code_lock");
        lock = new CustomItem(locksMaterial, 2, "items_name.lock");
        key = new CustomItem(keysMaterial, 1, "items_name.key");
        bunchKeys = new CustomItem(keysMaterial, 2, "items_name.bunch_of_keys");
        if (Configurations.getConfig().isEnableMasterKey())
            masterKey = new CustomItem(keysMaterial, 3, "items_name.master_key");
    }

    public static CustomItem getCodeLock() { return codeLock; }

    public static CustomItem getLock() { return lock; }

    public static CustomItem getKey() { return key; }

    public static CustomItem getBunchKeys() { return bunchKeys; }

    public static CustomItem getMasterKey() { return masterKey; }

    public static boolean isCodeLock(ItemStack itemStack) {
        return itemStack != null && itemStack.getType().equals(getCodeLock().getItemStack().getType()) &&
                itemStack.getItemMeta() != null && itemStack.getItemMeta().equals(getCodeLock().getItemStack().getItemMeta());
    }

    public static boolean isLock(ItemStack itemStack) {
        return itemStack != null && itemStack.getType().equals(getLock().getItemStack().getType()) &&
                itemStack.getItemMeta() != null && itemStack.getItemMeta().equals(getLock().getItemStack().getItemMeta());
    }


    public static boolean isKey(ItemStack itemStack) {
        return itemStack != null && itemStack.getType().equals(getKey().getItemStack().getType()) &&
                itemStack.getItemMeta() != null && itemStack.getItemMeta().equals(getKey().getItemStack().getItemMeta());
    }

    public static boolean isUsedKey(ItemStack itemStack, Location location) {
        return itemStack != null && itemStack.getItemMeta() != null &&
                itemStack.getItemMeta().getPersistentDataContainer().
                has(new NamespacedKey(DodaLockMain.getPlugin(), FormattableUtils.getLocationString(location)),
                PersistentDataType.STRING) && itemStack.getItemMeta().
                getPersistentDataContainer().get(new NamespacedKey(DodaLockMain.getPlugin(),
                FormattableUtils.getLocationString(location)), PersistentDataType.STRING).
                equals(Configurations.getLocks().getKey(FormattableUtils.getLocationString(location)));
    }

    public static boolean isUsedKey(ItemStack itemStack) {
        return itemStack != null && itemStack.getItemMeta() != null &&
                itemStack.getItemMeta().getPersistentDataContainer().getKeys().toArray()[0].toString().
                matches("[d][o][d][a][l][o][c][k][:][a-z]+([_][0-9]+){3}");
    }

    public static boolean isUsedBunchKeys(ItemStack itemStack) {
        return itemStack != null && itemStack.getItemMeta() != null &&
                itemStack.getItemMeta().getPersistentDataContainer().
                has(new NamespacedKey(DodaLockMain.getPlugin(), "bunch_of_keys"), PersistentDataType.STRING);
    }

    public static String getDataUsedBunchKeys(ItemStack itemStack) {
        if (itemStack != null && itemStack.getItemMeta() != null)
            return itemStack.getItemMeta().getPersistentDataContainer().get(new NamespacedKey(DodaLockMain.getPlugin(),
                        "bunch_of_keys"), PersistentDataType.STRING);
        return null;
    }

    public static boolean isBunchKeys(ItemStack itemStack) {
        return itemStack != null && itemStack.getType().equals(getBunchKeys().getItemStack().getType()) &&
                itemStack.getItemMeta() != null && itemStack.getItemMeta().equals(getBunchKeys().getItemStack().getItemMeta());
    }

    public static boolean isMasterKey(ItemStack itemStack) {
        return itemStack != null && getMasterKey() != null && getMasterKey().getItemStack() != null &&
                getMasterKey().getItemStack().getItemMeta() != null &&
                itemStack.getType().equals(getMasterKey().getItemStack().getType()) &&
                itemStack.getItemMeta() != null &&
                itemStack.getItemMeta().equals(getMasterKey().getItemStack().getItemMeta());
    }
}
