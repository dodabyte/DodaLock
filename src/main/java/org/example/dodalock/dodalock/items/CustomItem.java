package org.example.dodalock.dodalock.items;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.example.dodalock.dodalock.utils.FormattableUtils;
import org.example.dodalock.dodalock.utils.config.Configurations;

import java.util.*;

public class CustomItem {
    private final ItemStack itemStack;
    private String key;
    private String shape;
    private String shapeMaterials;
    private ShapedRecipe recipe;

    public CustomItem(Material material, int customModelData, String key) {
        itemStack = new ItemStack(material);
        createItemMeta(customModelData, key);
        if (Configurations.getConfig().isConfigItems(key.split("\\.")[1])) {
            this.key = key.split("\\.")[1];
            this.shape = Configurations.getConfig().getItemShape(key.split("\\.")[1]);
            this.shapeMaterials = Configurations.getConfig().getItemShapeMaterials(key.split("\\.")[1]);
            createRecipe();
        }
    }

    private void createItemMeta(int customModelData, String key) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setCustomModelData(customModelData);
        itemMeta.setDisplayName(ChatColor.RESET + Configurations.getLanguage().translate(key));
        itemMeta.setUnbreakable(true);
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_DESTROYS, ItemFlag.HIDE_UNBREAKABLE);
        if (key.contains("master_key")) {
            itemMeta.addEnchant(Enchantment.ARROW_KNOCKBACK, 1, true);
            itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            itemMeta.setLore(Arrays.asList(Configurations.getLanguage().translate("master_key_lore")));
        }
        itemStack.setItemMeta(itemMeta);
    }

    private void createRecipe() {
        Set<Character> shapeChars = new TreeSet<>();
        for (char c : shape.toCharArray()) {
            if (c != ',' && c != ' ' && c != '-') {
                shapeChars.add(c);
            }
        }

        recipe = new ShapedRecipe(NamespacedKey.minecraft(key), itemStack);
        String[] shapeArray = FormattableUtils.getShapeArray(shape);
        if (shapeArray != null && shapeArray.length > 0) {
            recipe.shape(shapeArray);
        }

        Map<Character, String> shapeMaterialsMap = FormattableUtils.getShapeMaterialsMap(shapeMaterials);
        for (Character c : shapeChars) {
            if (shapeMaterialsMap.containsKey(c) && shapeMaterialsMap.get(c) != null &&
                    Material.getMaterial(shapeMaterialsMap.get(c).toUpperCase()) != null) {
                recipe.setIngredient(c, Material.getMaterial(shapeMaterialsMap.get(c).toUpperCase()));
            } else {
                System.out.println("Error: The materials for crafting items are incorrectly configured.");
                return;
            }
        }

        Bukkit.addRecipe(recipe);
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }
}
