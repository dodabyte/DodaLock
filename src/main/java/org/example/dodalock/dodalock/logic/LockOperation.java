package org.example.dodalock.dodalock.logic;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.example.dodalock.dodalock.DodaLockMain;
import org.example.dodalock.dodalock.items.ItemsManager;
import org.example.dodalock.dodalock.utils.ChatUtils;
import org.example.dodalock.dodalock.utils.FormattableUtils;
import org.example.dodalock.dodalock.utils.config.Configurations;

import java.util.UUID;

public class LockOperation {
    public static void handle(Player player, String location) {
        if (player.isSneaking()) {
            // Замок отсутствует на двери -> добавление замка и создателя замка в конфиг
            if (!Configurations.getLocks().isLock(location)) {
                Configurations.getLocks().addLock(player, location);
                if (ItemsManager.isLock(player.getEquipment().getItemInMainHand()) &&
                        player.getEquipment().getItemInMainHand().getAmount() > 0) {
                    player.getEquipment().getItemInMainHand().setAmount(player.getEquipment()
                            .getItemInMainHand().getAmount() - 1);
                    player.updateInventory();
                    ChatUtils.printMessage(player, "success.installing_lock");
                }
            }
            // Замок имеется на двери, при этом у игрока в руках ключ или связка ключей,
            // а ранее ключ не был записан -> запись ключа
            else if (Configurations.getLocks().isLock(location) &&
                    ItemsManager.isKey(player.getEquipment().getItemInMainHand()) &&
                    (!Configurations.getLocks().isKey(location))) {
                UUID uuid = UUID.randomUUID();
                while (!Configurations.getLocks().isUniqueUuidKey(FormattableUtils.getUuidString(uuid)))
                    uuid = UUID.randomUUID();

                ItemStack item = ItemsManager.getKey().getItemStack().clone();
                ItemMeta itemMeta = item.getItemMeta();
                NamespacedKey key = new NamespacedKey(DodaLockMain.getPlugin(), location);
                itemMeta.getPersistentDataContainer().set(key, PersistentDataType.STRING, uuid.toString());
                itemMeta.setCustomModelData(3);
                item.setItemMeta(itemMeta);

                player.getEquipment().getItemInMainHand().setAmount(player.getEquipment()
                        .getItemInMainHand().getAmount() - 1);
                player.getInventory().addItem(item);
                player.updateInventory();
                Configurations.getLocks().changeKey(location, FormattableUtils.getUuidString(uuid));
                ChatUtils.printMessage(player, "success.create_key");
            }
        }
        Configurations.getLocks().save();
        Configurations.getLocks().reload();
    }
}
