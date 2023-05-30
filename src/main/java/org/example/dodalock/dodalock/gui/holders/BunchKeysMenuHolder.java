package org.example.dodalock.dodalock.gui.holders;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.example.dodalock.dodalock.utils.config.InventoryConfiguration;

import java.util.Arrays;

public abstract class BunchKeysMenuHolder implements InventoryHolder {
    protected Player player;
    protected Inventory inventory;
    protected String idBunchKeys;

    public BunchKeysMenuHolder(Player player, String idBunchKeys) {
        this.player = player;
        this.idBunchKeys = idBunchKeys;
    }

    public abstract String getMenuName();

    public abstract int getSlots();

    public abstract void handleMenu(InventoryClickEvent event);

    public void open() {
        inventory = Bukkit.createInventory(this, getSlots(), getMenuName());
        InventoryConfiguration.deserialize(getPlayer(), getIdBunchKeys(), inventory);
        getPlayer().openInventory(inventory);
    }

    @Override
    public Inventory getInventory() {
        return inventory;
    }

    public Player getPlayer() {
        return player;
    }

    public String getIdBunchKeys() { return idBunchKeys; }

    public ItemStack makeItem(Material material, String displayName, String... lore) {
        ItemStack item = new ItemStack(material);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(displayName);

        itemMeta.setLore(Arrays.asList(lore));
        item.setItemMeta(itemMeta);

        return item;
    }
}
