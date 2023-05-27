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
    private static ItemStack bunchKeys;

    public static void initializeItems() {
        createCodeLock();
        createLock();
        createKey();
        createBunchKeys();
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

    private static void createBunchKeys() {
        bunchKeys = new ItemStack(Material.BOOK);
        ItemMeta itemMeta = bunchKeys.getItemMeta();
        itemMeta.setCustomModelData(4);
        itemMeta.setDisplayName(ChatColor.RESET + Configurations.getLanguage().translate("items_name.bunch_keys"));
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        bunchKeys.setItemMeta(itemMeta);

        ShapedRecipe recipe = new ShapedRecipe(NamespacedKey.minecraft("bunch_of_keys"), bunchKeys);
        recipe.shape(" $ ", "$%$", " $ ");
        recipe.setIngredient('%', Material.IRON_INGOT);
        recipe.setIngredient('$', Material.STRING);
        Bukkit.addRecipe(recipe);
    }


    public static ItemStack getCodeLock() { return codeLock; }

    public static ItemStack getLock() { return lock; }

    public static ItemStack getKey() { return key; }

    public static ItemStack getBunchKeys() { return bunchKeys; }
}
