package org.example.dodalock.dodalock.items;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.example.dodalock.dodalock.utils.config.Configurations;

import java.util.List;

public class ItemsManager {
    private static final Material locksMaterial = Material.BOOK;
    private static final Material keysMaterial = Material.WOODEN_HOE;

    private static CustomItem codeLock;
    private static CustomItem lock;
    private static CustomItem key;
    private static CustomItem bunchKeys;

    public static void initializeItems() {
        codeLock = new CustomItem(locksMaterial, 1, Configurations.getLanguage().translate("items_name.code_lock"));
        lock = new CustomItem(locksMaterial, 2, Configurations.getLanguage().translate("items_name.lock"));
        key = new CustomItem(keysMaterial, 1, Configurations.getLanguage().translate("items_name.key"));
        bunchKeys = new CustomItem(keysMaterial, 2, Configurations.getLanguage().translate("items_name.bunch_keys"));

        createRecipes();
    }

    private static void createRecipes() {
        createCodeLockRecipe();
        createLockRecipe();
        createKeyRecipe();
        createBunchKeysRecipe();
    }

    private static void createCodeLockRecipe() {
        ShapedRecipe recipe = new ShapedRecipe(NamespacedKey.minecraft("code_lock"), codeLock.getItemStack());
        recipe.shape("$$$", "&*%", "$$$");
        recipe.setIngredient('$', Material.IRON_INGOT);
        recipe.setIngredient('*', Material.REDSTONE_BLOCK);
        recipe.setIngredient('&', Material.STONE_BUTTON);
        recipe.setIngredient('%', Material.GLASS_PANE);
        Bukkit.addRecipe(recipe);
    }

    private static void createLockRecipe() {
        ShapedRecipe recipe = new ShapedRecipe(NamespacedKey.minecraft("lock"), lock.getItemStack());
        recipe.shape("%%", "%%");
        recipe.setIngredient('%', Material.IRON_INGOT);
        Bukkit.addRecipe(recipe);
    }

    private static void createKeyRecipe() {
        ShapedRecipe recipe1 = new ShapedRecipe(NamespacedKey.minecraft("key"), key.getItemStack());
        recipe1.shape("%", "$");
        recipe1.setIngredient('%', Material.IRON_INGOT);
        recipe1.setIngredient('$', Material.LEVER);
        Bukkit.addRecipe(recipe1);

        // TODO Реализовать крафт клона ключа
//        ShapedRecipe recipe2 = new ShapedRecipe(NamespacedKey.minecraft("key_clone"), key.getItemStack());
//        recipe2.shape("$", "$");
//        recipe2.setIngredient('$', Material.BOOK);
//        Bukkit.addRecipe(recipe2);
    }

    private static void createBunchKeysRecipe() {
        ShapedRecipe recipe = new ShapedRecipe(NamespacedKey.minecraft("bunch_of_keys"), bunchKeys.getItemStack());
        recipe.shape(" $ ", "$%$", " $ ");
        recipe.setIngredient('%', Material.IRON_INGOT);
        recipe.setIngredient('$', Material.STRING);
        Bukkit.addRecipe(recipe);
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
