package org.example.dodalock.dodalock.logic;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.example.dodalock.dodalock.DodaLockMain;
import org.example.dodalock.dodalock.items.ItemsManager;
import org.example.dodalock.dodalock.utils.FormattableUtils;
import org.example.dodalock.dodalock.utils.config.Configurations;

import java.util.UUID;

public class LockOperation {
    public static void handle(Player player, String location) {
        if (player.isSneaking()) {
            // Замок отсутствует на двери -> добавление замка и создателя замка в конфиг
            if (!Configurations.getConfig().isLock(location)) {
                Configurations.getConfig().addLock(player, location);
                if (player.getEquipment().getItemInMainHand().equals(ItemsManager.getLock()) &&
                        player.getEquipment().getItemInMainHand().getAmount() > 0) {
                    player.getEquipment().getItemInMainHand().setAmount(player.getEquipment()
                            .getItemInMainHand().getAmount() - 1);
                    player.updateInventory();
                }
            }
            // Замок имеется на двери, при этом у игрока в руках ключ, а ранее ключ не был записан -> запись ключа
            else if (Configurations.getConfig().isLock(location) &&
                    player.getEquipment().getItemInMainHand().equals(ItemsManager.getKey()) &&
                    (!Configurations.getConfig().isKey(location))) {
                UUID uuid = UUID.randomUUID();
                ItemStack item = ItemsManager.getKey().clone();
                ItemMeta itemMeta = item.getItemMeta();
                NamespacedKey key = new NamespacedKey(DodaLockMain.getPlugin(), location);
                itemMeta.getPersistentDataContainer().set(key, PersistentDataType.STRING, uuid.toString());
                item.setItemMeta(itemMeta);

                player.getEquipment().getItemInMainHand().setAmount(player.getEquipment()
                        .getItemInMainHand().getAmount() - 1);
                player.getInventory().addItem(item);
                player.updateInventory();
                Configurations.getConfig().changeKey(location, FormattableUtils.getUuidString(uuid));
            }
        }
        else {
            if (Configurations.getConfig().isLock(location)) {

            }
        }
        Configurations.save();
        Configurations.reload();
    }
}
