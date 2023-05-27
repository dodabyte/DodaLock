package org.example.dodalock.dodalock.gui;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class GuiItemsManager {
    private static ItemStack inventoryPart;

    public static void initializeGuiItems() {
        createInventoryPart();
    }

    private static void createInventoryPart() {
        inventoryPart = new ItemStack(Material.SHULKER_SHELL);
        ItemMeta itemMeta = inventoryPart.getItemMeta();
        itemMeta.setCustomModelData(10000);
        itemMeta.setDisplayName(ChatColor.RESET + "");
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        inventoryPart.setItemMeta(itemMeta);
    }

    public static ItemStack getInventoryPart() { return inventoryPart; }
}
