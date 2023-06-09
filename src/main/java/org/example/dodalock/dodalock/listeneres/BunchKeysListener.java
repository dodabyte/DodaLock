package org.example.dodalock.dodalock.listeneres;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.example.dodalock.dodalock.DodaLockMain;
import org.example.dodalock.dodalock.gui.holders.BunchKeysMenuHolder;
import org.example.dodalock.dodalock.gui.menu.BunchKeysMenu;
import org.example.dodalock.dodalock.items.ItemsManager;
import org.example.dodalock.dodalock.utils.FormattableUtils;
import org.example.dodalock.dodalock.utils.config.Configurations;

import java.util.UUID;

public class BunchKeysListener implements Listener {
    BunchKeysMenu bunchKeysMenu;
    @EventHandler
    public void onBunchKeysInventoryOpened(PlayerInteractEvent event) {
        String idBunchKeys = "";
        if (event.getPlayer().getEquipment().getItemInMainHand() != null &&
                event.getPlayer().getEquipment().getItemInMainHand().getItemMeta() != null) {
            ItemStack itemInMainHand = event.getPlayer().getEquipment().getItemInMainHand();
            ItemMeta itemMeta = itemInMainHand.getItemMeta();
            NamespacedKey key = new NamespacedKey(DodaLockMain.getPlugin(),
                    "bunch_keys");
            PersistentDataContainer container = itemMeta.getPersistentDataContainer();
            if (container.has(key, PersistentDataType.STRING)) {
                idBunchKeys = container.get(key, PersistentDataType.STRING);
            }

            if (event.getPlayer().isSneaking() &&
                    (ItemsManager.isBunchKeys(event.getPlayer().getEquipment().getItemInMainHand()) ||
                            container.has(key, PersistentDataType.STRING))) {
                Player player = event.getPlayer();
                idBunchKeys = container.get(key, PersistentDataType.STRING);

                // Если связка ключей не прописана в конфиге, то добавляем ему идентификатор в конфиг
                if (!Configurations.getInventory().isBunchKeys(idBunchKeys)) {
                    UUID uuid = UUID.randomUUID();
                    idBunchKeys = FormattableUtils.getUuidString(uuid);
                    itemMeta.getPersistentDataContainer().set(key, PersistentDataType.STRING, FormattableUtils.getUuidString(uuid));
                    itemInMainHand.setItemMeta(itemMeta);

                    Configurations.getInventory().addBunchKeys(idBunchKeys);
                    Configurations.getInventory().save();
                    Configurations.getInventory().reload();
                }

                bunchKeysMenu = new BunchKeysMenu(player, idBunchKeys);
                bunchKeysMenu.open();
            }
        }
    }

    @EventHandler
    public void onBunchKeysInventoryClosed(InventoryCloseEvent event) {
        if (event.getInventory().getHolder() instanceof BunchKeysMenuHolder)
            Configurations.getInventory().serialize(bunchKeysMenu.getIdBunchKeys(), event.getInventory());
    }
}
