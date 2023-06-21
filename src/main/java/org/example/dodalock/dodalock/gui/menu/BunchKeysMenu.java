package org.example.dodalock.dodalock.gui.menu;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.example.dodalock.dodalock.gui.holders.BunchKeysMenuHolder;
import org.example.dodalock.dodalock.items.ItemsManager;

import java.util.ArrayList;
import java.util.List;

public class BunchKeysMenu extends BunchKeysMenuHolder {
    public BunchKeysMenu(Player player, String idBunchKeys) {
        super(player, idBunchKeys);
    }

    @Override
    public String getMenuName() {
        return "Bunch of Keys";
    }

    @Override
    public int getSlots() {
        return 9;
    }

    @Override
    public int getMaxStackSize() { return 1; }

    @Override
    public void handleMenu(InventoryClickEvent event) {
        if (event.getCurrentItem() != null && !ItemsManager.isKey(event.getCurrentItem()) &&
                !ItemsManager.isMasterKey(event.getCurrentItem()) &&
                !ItemsManager.isUsedKey(event.getCurrentItem())) {
                    event.setCancelled(true);
        }
        if (event.getAction() == InventoryAction.HOTBAR_MOVE_AND_READD || event.getAction() == InventoryAction.HOTBAR_SWAP) {
            if (!ItemsManager.isKey(event.getWhoClicked().getInventory().getItem(event.getHotbarButton())) &&
                    !ItemsManager.isMasterKey(event.getWhoClicked().getInventory().getItem(event.getHotbarButton())) &&
                    !ItemsManager.isUsedKey(event.getWhoClicked().getInventory().getItem(event.getHotbarButton()))) {
                event.setCancelled(true);
            }
        }
        checkInventory();
    }

    public void checkInventory() {
        for (ItemStack itemStack : getInventory().getContents()) {
            if (itemStack != null && !itemStack.getType().equals(Material.AIR) &&
                    !ItemsManager.isKey(itemStack) && !ItemsManager.isMasterKey(itemStack) &&
                    !ItemsManager.isUsedKey(itemStack)) {
                getPlayer().getInventory().addItem(itemStack);
                getInventory().remove(itemStack);
            }
        }
    }

    public void setLore() {
        List<String> locationList = new ArrayList<>();
        for (ItemStack itemStack : getInventory().getContents()) {
            if (itemStack != null && !itemStack.getType().equals(Material.AIR) &&
                    !ItemsManager.isKey(itemStack) && itemStack.getItemMeta() != null) {
                ItemMeta itemMeta = itemStack.getItemMeta();
                if (ItemsManager.isUsedKey(itemStack)) {
                    locationList.add(itemMeta.getPersistentDataContainer().getKeys().toArray()[0].
                            toString().split(":")[1]);
                }
            }
        }
        ItemMeta itemMeta = getPlayer().getInventory().getItemInMainHand().getItemMeta();
        itemMeta.setLore(locationList);
        getPlayer().getInventory().getItemInMainHand().setItemMeta(itemMeta);
    }
}
