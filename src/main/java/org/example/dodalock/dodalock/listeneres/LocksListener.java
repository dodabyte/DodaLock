package org.example.dodalock.dodalock.listeneres;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.persistence.PersistentDataType;
import org.example.dodalock.dodalock.DodaLockMain;
import org.example.dodalock.dodalock.gui.menu.CodeLockMenu;
import org.example.dodalock.dodalock.items.ItemsManager;
import org.example.dodalock.dodalock.logic.LockOperation;
import org.example.dodalock.dodalock.utils.ChatUtils;
import org.example.dodalock.dodalock.utils.FormattableUtils;
import org.example.dodalock.dodalock.utils.WorldUtils;
import org.example.dodalock.dodalock.utils.config.Configurations;

public class LocksListener implements Listener {
    @EventHandler
    public void onPlayerDoorOpened(PlayerInteractEvent event) {
        if (event.getHand() == EquipmentSlot.HAND) {
            Block clicked = event.getClickedBlock();
            Player player = event.getPlayer();
            Location location = WorldUtils.getLocation(clicked);

            if (clicked == null) return;

            if (location != null && WorldUtils.isTrueTypes(clicked)) {
                if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                    if (player.isSneaking()) {
                        // Открытие меню кодового замка при его наличии на двери
                        if (Configurations.getLocks().isCodeLock(FormattableUtils.getLocationString(location))) {
                            event.setCancelled(true);
                            new CodeLockMenu(player, FormattableUtils.getLocationString(location)).open();
                        }
                        if (ItemsManager.isCodeLock(player.getEquipment().getItemInMainHand())) {
                            // Открытие меню кодового замка при его отсутствии на двери с последующим его добавлением
                            if (!Configurations.getLocks().isCodeLock(FormattableUtils.getLocationString(location))
                                    && !Configurations.getLocks().isLock(FormattableUtils.getLocationString(location))) {
                                event.setCancelled(true);
                                new CodeLockMenu(player, FormattableUtils.getLocationString(location)).open();
                            }
                            // Вывод сообщения о том, что установить кодовый замок нельзя, так как уже установлен замок
                            else if (Configurations.getLocks().isLock(FormattableUtils.getLocationString(location))) {
                                ChatUtils.printMessage(player, "error.installing_code_lock_with_lock");
                            }
                            // Вывод сообщения о том, что установить кодовый замок нельзя, так как уже установлен кодовый замок
                            else if (Configurations.getLocks().isCodeLock(FormattableUtils.getLocationString(location))) {
                                ChatUtils.printMessage(player, "error.installing_code_lock_with_code_lock");
                            }
                        }

                        if (ItemsManager.isLock(player.getEquipment().getItemInMainHand())) {
                            // Клик на дверь с замком в руках при отсутствии его на двери с последующим добавлением замка
                            if (!Configurations.getLocks().isLock(FormattableUtils.getLocationString(location)) &&
                                    !Configurations.getLocks().isCodeLock(FormattableUtils.getLocationString(location))) {
                                LockOperation.handle(player, FormattableUtils.getLocationString(location));
                            }
                            // Вывод сообщения о том, что установить замок нельзя, так как уже установлен замок
                            else if (Configurations.getLocks().isLock(FormattableUtils.getLocationString(location))) {
                                ChatUtils.printMessage(player, "error.installing_lock_with_lock");
                            }
                            // Вывод сообщения о том, что установить замок нельзя, так как уже установлен кодовый замок
                            else if (Configurations.getLocks().isCodeLock(FormattableUtils.getLocationString(location))) {
                                ChatUtils.printMessage(player, "error.installing_lock_with_code_lock");
                            }
                        }
                        // Клик на дверь с ключом в руках при наличии замка на двери с последующим добавлением ключа
                        else if (Configurations.getLocks().isLock(FormattableUtils.getLocationString(location)) &&
                                ItemsManager.isKey(player.getEquipment().getItemInMainHand())) {
                            LockOperation.handle(player, FormattableUtils.getLocationString(location));
                        }
                        // При попытке открыть дверь, когда замок установлен и игрок не держит ничего в руках,
                        // а также находится в приседе -> отмена открытия двери
                        else if (Configurations.getLocks().isLock(FormattableUtils.getLocationString(location)) &&
                                player.getEquipment().getItemInMainHand().getType().equals(Material.AIR) &&
                                player.getEquipment().getItemInOffHand().getType().equals(Material.AIR)) {
                            event.setCancelled(true);
                            ChatUtils.printMessage(player, "error.open_object_with_lock");
                        }
                    } else {
                        // При попытке открыть дверь, когда кодовый замок установлен и игрок не вводил никогда пароль
                        // -> отмена открытия двери
                        if (Configurations.getLocks().isCodeLock(FormattableUtils.getLocationString(location)) &&
                                !Configurations.getLocks().isPlayerInCodeLock(FormattableUtils.
                                        getLocationString(location), player)) {
                            event.setCancelled(true);
                            ChatUtils.printMessage(player, "error.open_object_with_code_lock");
                        }

                        // При попытке открыть дверь, когда замок установлен и игрок не держит ключ от данного замка
                        // -> отмена открытия двери
                        if (Configurations.getLocks().isLock(FormattableUtils.getLocationString(location)) &&
                                Configurations.getLocks().isKey(FormattableUtils.getLocationString(location)) &&
                                (player.getEquipment().getItemInMainHand().getItemMeta() != null &&
                                ((!player.getEquipment().getItemInMainHand().getItemMeta().getPersistentDataContainer().
                                has(new NamespacedKey(DodaLockMain.getPlugin(), FormattableUtils.getLocationString(location)),
                                PersistentDataType.STRING) || !player.getEquipment().getItemInMainHand().getItemMeta().
                                getPersistentDataContainer().get(new NamespacedKey(DodaLockMain.getPlugin(),
                                FormattableUtils.getLocationString(location)), PersistentDataType.STRING).
                                equals(Configurations.getLocks().getKey(FormattableUtils.getLocationString(location)))) &&
                                (!player.getEquipment().getItemInMainHand().getItemMeta().
                                getPersistentDataContainer().has(new NamespacedKey(DodaLockMain.getPlugin(),
                                "bunch_of_keys"), PersistentDataType.STRING))) ||
                                player.getEquipment().getItemInMainHand().getItemMeta() == null)) {
                            event.setCancelled(true);
                            ChatUtils.printMessage(player, "error.open_object_with_lock");
                        }
                        // При попытке открыть дверь, когда замок установлен и игрок держит связку ключей, в которой нет
                        // ключа от замка -> отмена открытия двери
                        else if (Configurations.getLocks().isLock(FormattableUtils.getLocationString(location)) &&
                                Configurations.getLocks().isKey(FormattableUtils.getLocationString(location)) &&
                                player.getEquipment().getItemInMainHand().getItemMeta() != null &&
                                player.getEquipment().getItemInMainHand().getItemMeta().getPersistentDataContainer().
                                has(new NamespacedKey(DodaLockMain.getPlugin(), "bunch_of_keys"), PersistentDataType.STRING) &&
                                !Configurations.getInventory().checkDoorWithBunchKeys(location, player.getEquipment().getItemInMainHand().
                                getItemMeta().getPersistentDataContainer().get(new NamespacedKey(DodaLockMain.getPlugin(),
                                "bunch_of_keys"), PersistentDataType.STRING))) {
                            event.setCancelled(true);
                            ChatUtils.printMessage(player, "error.open_object_with_lock");
                        }
                    }
                    Configurations.getLocks().save();
                    Configurations.getLocks().reload();
                }
                else if (event.getAction() == Action.LEFT_CLICK_BLOCK) {
                    if (player.isSneaking()) {
                        // Удаление кодового замка из конфига и его снятие с двери
                        if (Configurations.getLocks().isCodeLock(FormattableUtils.getLocationString(location))) {
                            event.setCancelled(true);
                            Configurations.getLocks().removeCodeLock(FormattableUtils.getLocationString(location));
                            location.getWorld().dropItem(location, ItemsManager.getCodeLock().getItemStack());
                            ChatUtils.printMessage(player, "success.remove_code_lock");
                        }

                        // Удаление замка из конфига и его снятие с двери
                        if (Configurations.getLocks().isLock(FormattableUtils.getLocationString(location)) &&
                                Configurations.getLocks().isKey(FormattableUtils.getLocationString(location)) &&
                                ItemsManager.isKey(player.getEquipment().getItemInMainHand())) {
                            event.setCancelled(true);
                            Configurations.getLocks().removeLock(FormattableUtils.getLocationString(location));
                            location.getWorld().dropItem(location, ItemsManager.getLock().getItemStack());
                            ChatUtils.printMessage(player, "success.remove_lock");
                        }
                    }
                    Configurations.getLocks().save();
                    Configurations.getLocks().reload();
                }
            }
            else if (clicked.getType() == Material.DIRT || clicked.getType() == Material.DIRT_PATH ||
                    clicked.getType() == Material.GRASS_BLOCK || clicked.getType() == Material.ROOTED_DIRT ||
                    clicked.getType() == Material.COARSE_DIRT) {
                // Отмена любых действий с ключом и связкой ключей, если клик на любой другой блок, кроме дверей и т.д.
                if (ItemsManager.isKey(player.getEquipment().getItemInMainHand()) ||
                        (player.getEquipment().getItemInMainHand().getItemMeta() != null &&
                        player.getEquipment().getItemInMainHand().getItemMeta().getPersistentDataContainer().getKeys().size() == 1 &&
                        (player.getEquipment().getItemInMainHand().getItemMeta().getPersistentDataContainer().getKeys().
                        toArray()[0].toString().matches("[d][o][d][a][l][o][c][k][:][a-z]+([_][0-9]+){3}") ||
                        player.getEquipment().getItemInMainHand().getItemMeta().getPersistentDataContainer().getKeys().
                        toArray()[0].toString().contains("dodalock:bunch_of_keys")))) {
                    event.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onDoorBreak(BlockBreakEvent event) {
        if (WorldUtils.isTrueTypes(event.getBlock())) {
            Location location = WorldUtils.getLocation(event.getBlock());

            if (location != null) {
                if (Configurations.getLocks().isCodeLock(FormattableUtils.getLocationString(location))) {
                    Configurations.getLocks().removeCodeLock(FormattableUtils.getLocationString(location));
                    event.getBlock().getLocation().getWorld().dropItem(location, ItemsManager.getCodeLock().getItemStack());
                }

                if (Configurations.getLocks().isLock(FormattableUtils.getLocationString(location))) {
                    Configurations.getLocks().removeLock(FormattableUtils.getLocationString(location));
                    event.getBlock().getLocation().getWorld().dropItem(location, ItemsManager.getLock().getItemStack());
                }
            }
            Configurations.getLocks().save();
            Configurations.getLocks().reload();
        }
    }
}
