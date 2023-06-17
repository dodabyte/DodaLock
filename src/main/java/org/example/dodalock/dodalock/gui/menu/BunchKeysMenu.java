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
                !ItemsManager.isMasterKey(event.getCurrentItem())) {
            if (event.getCurrentItem().getItemMeta() != null) {
                ItemMeta itemMeta = event.getCurrentItem().getItemMeta();
                if (!(itemMeta.getPersistentDataContainer().getKeys().size() == 1 &&
                        itemMeta.getPersistentDataContainer().getKeys().toArray()[0].toString().matches(
                        "[d][o][d][a][l][o][c][k][:][a-z]+([_][0-9]+){3}"))) {
                    event.setCancelled(true);
                }
            }
        }
        if (event.getAction() == InventoryAction.HOTBAR_MOVE_AND_READD || event.getAction() == InventoryAction.HOTBAR_SWAP) {
            if (event.getWhoClicked().getInventory().getItem(event.getHotbarButton()) != null &&
                    !ItemsManager.isKey(event.getWhoClicked().getInventory().getItem(event.getHotbarButton())) &&
                    !ItemsManager.isMasterKey(event.getWhoClicked().getInventory().getItem(event.getHotbarButton()))) {
                if (event.getWhoClicked().getInventory().getItem(event.getHotbarButton()).getItemMeta() != null) {
                    ItemMeta itemMeta = event.getWhoClicked().getInventory().getItem(event.getHotbarButton()).getItemMeta();
                    if (!(itemMeta.getPersistentDataContainer().getKeys().size() == 1 &&
                            itemMeta.getPersistentDataContainer().getKeys().toArray()[0].toString().matches(
                            "[d][o][d][a][l][o][c][k][:][a-z]+([_][0-9]+){3}"))) {
                        event.setCancelled(true);
                    }
                }
            }
        }
        checkInventory();
    }

    public void checkInventory() {
        for (ItemStack itemStack : getInventory().getContents()) {
            if (itemStack != null && !itemStack.getType().equals(Material.AIR) && !ItemsManager.isKey(itemStack) &&
                    itemStack.getItemMeta() != null && !ItemsManager.isMasterKey(itemStack)) {
                ItemMeta itemMeta = itemStack.getItemMeta();
                if (!(itemMeta.getPersistentDataContainer().getKeys().size() == 1 &&
                        itemMeta.getPersistentDataContainer().getKeys().toArray()[0].toString().matches(
                        "[d][o][d][a][l][o][c][k][:][a-z]+([_][0-9]+){3}"))) {
                    getPlayer().getInventory().addItem(itemStack);
                    getInventory().remove(itemStack);
                }
            }
        }
    }

    public void setLore() {
        List<String> locationList = new ArrayList<>();
        for (ItemStack itemStack : getInventory().getContents()) {
            if (itemStack != null && !itemStack.getType().equals(Material.AIR) &&
                    !ItemsManager.isKey(itemStack) && itemStack.getItemMeta() != null) {
                ItemMeta itemMeta = itemStack.getItemMeta();
                if (itemMeta.getPersistentDataContainer().getKeys().size() == 1 &&
                        itemMeta.getPersistentDataContainer().getKeys().toArray()[0].toString().matches(
                                "[d][o][d][a][l][o][c][k][:][a-z]+([_][0-9]+){3}")) {
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