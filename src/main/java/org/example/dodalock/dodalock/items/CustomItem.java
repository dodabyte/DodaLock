package org.example.dodalock.dodalock.items;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.example.dodalock.dodalock.utils.FormattableUtils;
import org.example.dodalock.dodalock.utils.config.Configurations;

import java.util.*;

public class CustomItem {
    private final ItemStack itemStack;

    public CustomItem(Material material, int customModelData, String key) {
        itemStack = new ItemStack(material);
        createItemMeta(customModelData, key);
        if (Configurations.getConfig().isConfigItems(key.split("\\.")[1])) {
            createRecipe(key.split("\\.")[1],
                    Configurations.getConfig().getItemShape(key.split("\\.")[1]),
                    Configurations.getConfig().getItemShapeMaterials(key.split("\\.")[1]));
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
            itemMeta.setLore(List.of(Configurations.getLanguage().translate("master_key_lore")));
        }
        itemStack.setItemMeta(itemMeta);
    }

    private void createRecipe(String key, String shape, String shapeMaterials) {
        Set<Character> shapeChars = new TreeSet<>();
        for (char c : shape.toCharArray()) {
            if (c != ',' && c != ' ' && c != '-') {
                shapeChars.add(c);
            }
        }

        ShapedRecipe recipe = new ShapedRecipe(NamespacedKey.minecraft(key), itemStack);
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

    public ItemStack getItemStack() {
        return itemStack;
    }
}
