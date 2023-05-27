package org.example.dodalock.dodalock.listeneres;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.example.dodalock.dodalock.gui.holders.BunchKeysMenuHolder;
import org.example.dodalock.dodalock.gui.holders.CodeLockMenuHolder;
import org.example.dodalock.dodalock.gui.menu.BunchKeysMenu;
import org.example.dodalock.dodalock.items.ItemsManager;
import org.example.dodalock.dodalock.utils.config.InventoryConfiguration;

public class BunchKeysListener implements Listener {
    @EventHandler
    public void onBunchKeysInventoryOpened(PlayerInteractEvent event) {
        if (event.getPlayer().getEquipment().getItemInMainHand().equals(ItemsManager.getBunchKeys()))
            new BunchKeysMenu(event.getPlayer()).open();
    }

    @EventHandler
    public void onBunchKeysInventoryClosed(InventoryCloseEvent event) {
        if (event.getInventory().getHolder() instanceof BunchKeysMenuHolder)
            InventoryConfiguration.serialize((Player) event.getPlayer(), event.getInventory());
    }
}
