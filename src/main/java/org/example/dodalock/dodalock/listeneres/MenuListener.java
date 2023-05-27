package org.example.dodalock.dodalock.listeneres;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.InventoryHolder;
import org.example.dodalock.dodalock.gui.Menu;

public class MenuListener implements Listener {
    @EventHandler
    public void onMenuClick(InventoryClickEvent event) {
        InventoryHolder holder = event.getInventory().getHolder();
        if (holder instanceof Menu) {
            Menu menu = (Menu) holder;
            event.setCancelled(true);
            if (event.getCurrentItem() == null) {
                return;
            }
            menu.handleMenu(event);
        }
    }
}
