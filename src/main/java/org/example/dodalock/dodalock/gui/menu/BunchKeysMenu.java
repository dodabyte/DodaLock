package org.example.dodalock.dodalock.gui.menu;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.example.dodalock.dodalock.gui.holders.BunchKeysMenuHolder;

public class BunchKeysMenu extends BunchKeysMenuHolder {
    public BunchKeysMenu(Player player) {
        super(player);
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
    public void handleMenu(InventoryClickEvent event) {
        if (event.getInventory().equals(this.getInventory())) {
            // TODO Сделать проверку на то, что положить сюда можно только ключи

        }
    }
}
