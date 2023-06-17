package org.example.dodalock.dodalock.listeneres;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.example.dodalock.dodalock.DodaLockMain;
import org.example.dodalock.dodalock.gui.holders.BunchKeysMenuHolder;
import org.example.dodalock.dodalock.gui.menu.BunchKeysMenu;
import org.example.dodalock.dodalock.items.ItemsManager;
import org.example.dodalock.dodalock.utils.FormattableUtils;
import org.example.dodalock.dodalock.utils.config.Configurations;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class BunchKeysListener implements Listener {
    Map<Player, BunchKeysMenu> bunchKeysMenuMap = new HashMap<>();
    @EventHandler
    public void onBunchKeysInventoryOpened(PlayerInteractEvent event) {
        String idBunchKeys = "";
        if (event.getPlayer().getEquipment().getItemInMainHand() != null &&
                event.getPlayer().getEquipment().getItemInMainHand().getItemMeta() != null) {
            ItemStack itemInMainHand = event.getPlayer().getEquipment().getItemInMainHand();
            ItemMeta itemMeta = itemInMainHand.getItemMeta();

            if (ItemsManager.isBunchKeys(event.getPlayer().getEquipment().getItemInMainHand()) ||
                    ItemsManager.isUsedBunchKeys(itemInMainHand)) {
                if ((event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_AIR) &&
                        event.getPlayer().isSneaking()) {
                    event.setCancelled(true);
                    Player player = event.getPlayer();
                    idBunchKeys = ItemsManager.getDataUsedBunchKeys(itemInMainHand);

                    // Если связка ключей не прописана в конфиге, то добавляем ему идентификатор в конфиг
                    if (!Configurations.getInventory().isBunchKeys(idBunchKeys)) {
                        UUID uuid = UUID.randomUUID();
                        while (!Configurations.getInventory().isUniqueUuidBunchKeys(FormattableUtils.getUuidString(uuid)))
                            uuid = UUID.randomUUID();
                        idBunchKeys = FormattableUtils.getUuidString(uuid);
                        NamespacedKey key = new NamespacedKey(DodaLockMain.getPlugin(), "bunch_of_keys");
                        itemMeta.getPersistentDataContainer().set(key, PersistentDataType.STRING, FormattableUtils.getUuidString(uuid));
                        itemInMainHand.setItemMeta(itemMeta);

                        Configurations.getInventory().addBunchKeys(idBunchKeys);
                        Configurations.getInventory().save();
                        Configurations.getInventory().reload();
                    }

                    bunchKeysMenuMap.put(player, new BunchKeysMenu(player, idBunchKeys));
                    bunchKeysMenuMap.get(player).open();
                }
            }
        }
    }

    @EventHandler
    public void onBunchKeysInventoryClosed(InventoryCloseEvent event) {
        if (event.getInventory().getHolder() instanceof BunchKeysMenuHolder) {
            bunchKeysMenuMap.get((Player) event.getPlayer()).checkInventory();
            if (Configurations.getConfig().isEnableLore()) {
                bunchKeysMenuMap.get((Player) event.getPlayer()).setLore();
            }
            Configurations.getInventory().serialize(bunchKeysMenuMap.get((Player) event.getPlayer()).getIdBunchKeys(),
                    event.getInventory());
        }
    }
}
