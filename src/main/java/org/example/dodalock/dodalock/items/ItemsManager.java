package org.example.dodalock.dodalock.items;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.example.dodalock.dodalock.utils.config.Configurations;

public class ItemsManager {
    private static final Material locksMaterial = Material.BOOK;
    private static final Material keysMaterial = Material.WOODEN_HOE;

    private static CustomItem codeLock;
    private static CustomItem lock;
    private static CustomItem key;
    private static CustomItem bunchKeys;

    public static void initializeItems() {
        codeLock = new CustomItem(locksMaterial, 1, "items_name.code_lock");
        lock = new CustomItem(locksMaterial, 2, "items_name.lock");
        key = new CustomItem(keysMaterial, 1, "items_name.key");
        bunchKeys = new CustomItem(keysMaterial, 2, "items_name.bunch_of_keys");
    }

    public static CustomItem getCodeLock() { return codeLock; }

    public static CustomItem getLock() { return lock; }

    public static CustomItem getKey() { return key; }

    public static CustomItem getBunchKeys() { return bunchKeys; }

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

    public static boolean isBunchKeys(ItemStack itemStack) {
        return itemStack != null && itemStack.getType().equals(getBunchKeys().getItemStack().getType()) &&
                itemStack.getItemMeta() != null && itemStack.getItemMeta().equals(getBunchKeys().getItemStack().getItemMeta());
    }
}
