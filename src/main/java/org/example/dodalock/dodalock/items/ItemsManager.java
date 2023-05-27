package org.example.dodalock.dodalock.items;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.example.dodalock.dodalock.utils.config.Configurations;

public class ItemsManager {
    private static ItemStack codeLock;
    private static ItemStack lock;
    private static ItemStack key;
    private static ItemStack inventoryPart;

    public static void initializeItems() {
        createCodeLock();
        createLock();
        createKey();
        createInventoryPart();
    }

    private static void createCodeLock() {
        codeLock = new ItemStack(Material.BOOK);
        ItemMeta itemMeta = codeLock.getItemMeta();
        itemMeta.setCustomModelData(1);
        itemMeta.setDisplayName(ChatColor.RESET + Configurations.getLanguage().translate("items_name.code_lock"));
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        codeLock.setItemMeta(itemMeta);

        ShapedRecipe recipe = new ShapedRecipe(NamespacedKey.minecraft("code_lock"), codeLock);
        recipe.shape("$$$", "&*%", "$$$");
        recipe.setIngredient('$', Material.IRON_INGOT);
        recipe.setIngredient('*', Material.REDSTONE_BLOCK);
        recipe.setIngredient('&', Material.STONE_BUTTON);
        recipe.setIngredient('%', Material.GLASS_PANE);
        Bukkit.addRecipe(recipe);
    }

    private static void createLock() {
        lock = new ItemStack(Material.BOOK);
        ItemMeta itemMeta = lock.getItemMeta();
        itemMeta.setCustomModelData(2);
        itemMeta.setDisplayName(ChatColor.RESET + Configurations.getLanguage().translate("items_name.lock"));
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        lock.setItemMeta(itemMeta);

        ShapedRecipe recipe = new ShapedRecipe(NamespacedKey.minecraft("lock"), lock);
        recipe.shape("%%", "%%");
        recipe.setIngredient('%', Material.IRON_INGOT);
        Bukkit.addRecipe(recipe);
    }

    private static void createKey() {
        key = new ItemStack(Material.BOOK);
        ItemMeta itemMeta = key.getItemMeta();
        itemMeta.setCustomModelData(3);
        itemMeta.setDisplayName(ChatColor.RESET + Configurations.getLanguage().translate("items_name.key"));
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        key.setItemMeta(itemMeta);

        ShapedRecipe recipe = new ShapedRecipe(NamespacedKey.minecraft("key"), key);
        recipe.shape("%", "$");
        recipe.setIngredient('%', Material.IRON_INGOT);
        recipe.setIngredient('$', Material.LEVER);
        Bukkit.addRecipe(recipe);
    }

    private static void createInventoryPart() {
        inventoryPart = new ItemStack(Material.SHULKER_SHELL);
        ItemMeta itemMeta = inventoryPart.getItemMeta();
        itemMeta.setCustomModelData(10000);
        itemMeta.setDisplayName(ChatColor.RESET + "");
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        inventoryPart.setItemMeta(itemMeta);
    }

    public static ItemStack getCodeLock() { return codeLock; }

    public static ItemStack getLock() { return lock; }

    public static ItemStack getKey() { return key; }

    public static ItemStack getInventoryPart() { return inventoryPart; }
}
