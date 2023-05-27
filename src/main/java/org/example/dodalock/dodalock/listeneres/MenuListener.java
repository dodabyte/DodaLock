package org.example.dodalock.dodalock.listeneres;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.InventoryHolder;
import org.example.dodalock.dodalock.gui.holders.CodeLockMenuHolder;

public class MenuListener implements Listener {
    @EventHandler
    public void onMenuClick(InventoryClickEvent event) {
        InventoryHolder holder = event.getInventory().getHolder();
        if (holder instanceof CodeLockMenuHolder) {
            CodeLockMenuHolder menu = (CodeLockMenuHolder) holder;
            if (event.getCurrentItem() == null) {
                return;
            }
            menu.handleMenu(event);
        }
    }
}
