package org.example.dodalock.dodalock.listeneres;

import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.DoubleChestInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.example.dodalock.dodalock.DodaLockMain;
import org.example.dodalock.dodalock.gui.menu.CodeLockMenu;
import org.example.dodalock.dodalock.items.ItemsManager;
import org.example.dodalock.dodalock.logic.LockOperation;
import org.example.dodalock.dodalock.utils.FormattableUtils;
import org.example.dodalock.dodalock.utils.WorldUtils;
import org.example.dodalock.dodalock.utils.config.Configurations;

public class LocksListener implements Listener {
    @EventHandler
    public void onPlayerDoorOpened(PlayerInteractEvent event) {
        Block clicked = event.getClickedBlock();
        Player player = event.getPlayer();
        Location location;

        if (clicked != null && event.getAction() == Action.RIGHT_CLICK_BLOCK &&
            WorldUtils.isTrueTypes(clicked)) {
            location = getLocation(clicked, player);
            System.out.println(location);
            if (location != null) {
                if (player.isSneaking()) {
                    // Открытие меню кодового замка при его наличии на двери
                    if (Configurations.getConfig().isCodeLock(FormattableUtils.getLocationString(location))) {
                        event.setCancelled(true);
                        new CodeLockMenu(player, FormattableUtils.getLocationString(location)).open();
                    }
                    // Открытие меню кодового замка при его отсутствии на двери с последующим его добавлением
                    else if (player.getEquipment().getItemInMainHand().equals(ItemsManager.getCodeLock()) &&
                            !Configurations.getConfig().isLock(FormattableUtils.getLocationString(location))) {
                        event.setCancelled(true);
                        new CodeLockMenu(player, FormattableUtils.getLocationString(location)).open();
                    }

                    // Клик на дверь с замком в руках при отсутствии его на двери с последующим добавлением замка
                    if (!Configurations.getConfig().isLock(FormattableUtils.getLocationString(location)) &&
                            player.getEquipment().getItemInMainHand().equals(ItemsManager.getLock()) &&
                            !Configurations.getConfig().isCodeLock(FormattableUtils.getLocationString(location))) {
                        event.setCancelled(true);
                        LockOperation.handle(player, FormattableUtils.getLocationString(location));
                    }
                    // Клик на дверь с ключом в руках при наличии замка на двери с последующим добавлением ключа
                    else if (Configurations.getConfig().isLock(FormattableUtils.getLocationString(location)) &&
                            player.getEquipment().getItemInMainHand().equals(ItemsManager.getKey())) {
                        event.setCancelled(true);
                        LockOperation.handle(player, FormattableUtils.getLocationString(location));
                    }
                    // При попытке открыть дверь, когда замок установлен и игрок не держит ключ в руках,
                    // а также находится в приседе -> отмена открытия двери
                    else if (Configurations.getConfig().isLock(FormattableUtils.getLocationString(location))) {
                        event.setCancelled(true);
                    }
                } else {
                    // При попытке открыть дверь, когда кодовый замок установлен и игрок не ввёл пароль -> отмена открытия двери
                    if (Configurations.getConfig().isCodeLock(FormattableUtils.getLocationString(location)) &&
                            !Configurations.getConfig().isPlayerInCodeLock(FormattableUtils.
                                    getLocationString(location), player)) {
                        event.setCancelled(true);
                    }

                    String id_key = "";
                    if (player.getEquipment().getItemInMainHand().equals(ItemsManager.getKey())) {
                        ItemStack itemInMainHand = player.getEquipment().getItemInMainHand();
                        ItemMeta itemMeta = itemInMainHand.getItemMeta();
                        PersistentDataContainer container = itemMeta.getPersistentDataContainer();
                        if (Configurations.getConfig().isLock(FormattableUtils.getLocationString(location)) &&
                                container.has(new NamespacedKey(DodaLockMain.getPlugin(), FormattableUtils.
                                        getLocationString(location)), PersistentDataType.STRING)) {
                            id_key = container.get(new NamespacedKey(DodaLockMain.getPlugin(), FormattableUtils.
                                    getLocationString(location)), PersistentDataType.STRING);
                        }
                    }

                    // При попытке открыть дверь, когда замок установлен и игрок не держит ключ в руках
                    // -> отмена открытия двери
                    if (Configurations.getConfig().isLock(FormattableUtils.getLocationString(location)) &&
                            Configurations.getConfig().isKey(FormattableUtils.getLocationString(location)) &&
                            ((player.getEquipment().getItemInMainHand().getItemMeta() != null &&
                            !player.getEquipment().getItemInMainHand().getItemMeta().getPersistentDataContainer().
                            has(new NamespacedKey(DodaLockMain.getPlugin(), FormattableUtils.getLocationString(location)),
                            PersistentDataType.STRING)) || player.getEquipment().getItemInMainHand().getItemMeta() == null)) {
                        event.setCancelled(true);
                    }
                    // При попытке открыть дверь, когда замок установлен, игрок держит ключ в руках,
                    // но не от данного замка -> отмена открытия двери
                    else if (Configurations.getConfig().isLock(FormattableUtils.getLocationString(location)) &&
                            Configurations.getConfig().isKey(FormattableUtils.getLocationString(location)) &&
                            player.getEquipment().getItemInMainHand().equals(ItemsManager.getKey()) && id_key != null &&
                            !id_key.equals("") && !Configurations.getConfig().isTrueKey(FormattableUtils.
                            getLocationString(location), id_key)) {
                        event.setCancelled(true);
                    }
                }
            }
            Configurations.save();
            Configurations.reload();
        }
        else if (event.getClickedBlock() != null && event.getAction() == Action.LEFT_CLICK_BLOCK &&
                WorldUtils.isTrueTypes(event.getClickedBlock())) {
            location = getLocation(clicked, player);
            if (location != null) {
                if (player.isSneaking()) {
                    // Удаление игрока из памяти кодового замка
                    // TODO Изменить это на снятие кодового замка
                    // TODO Так как забытие пароля будет в интерфейсе замка
                    if (Configurations.getConfig().isCodeLock(FormattableUtils.getLocationString(location))) {
                        Configurations.getConfig().removePlayerFromCodeLockData(FormattableUtils.
                                getLocationString(location), player);
                    }

                    // Удаление замка из конфига
                    if (Configurations.getConfig().isLock(FormattableUtils.getLocationString(location)) &&
                            Configurations.getConfig().isKey(FormattableUtils.getLocationString(location)) &&
                            player.getEquipment().getItemInMainHand().equals(ItemsManager.getKey())) {
                        Configurations.getConfig().removeLock(FormattableUtils.getLocationString(location));
                        Configurations.getConfig().removeLockFromList(FormattableUtils.getLocationString(location));
                    }
                }
            }
            Configurations.save();
            Configurations.reload();
        }
    }

    @EventHandler
    public void onDoorBreak(BlockBreakEvent event) {
        if (WorldUtils.isTrueTypes(event.getBlock())) {
            Location location = getLocation(event.getBlock(), event.getPlayer());

            if (location != null) {
                if (Configurations.getConfig().isCodeLock(FormattableUtils.getLocationString(location))) {
                    Configurations.getConfig().removeCodeLock(FormattableUtils.getLocationString(location));
                    Configurations.getConfig().removeCodeLockFromList(FormattableUtils.getLocationString(location));
                    event.getBlock().getLocation().getWorld().dropItem(location, ItemsManager.getCodeLock());
                    Configurations.save();
                    Configurations.reload();
                }

                if (Configurations.getConfig().isLock(FormattableUtils.getLocationString(location))) {
                    Configurations.getConfig().removeLock(FormattableUtils.getLocationString(location));
                    Configurations.getConfig().removeLockFromList(FormattableUtils.getLocationString(location));
                    event.getBlock().getLocation().getWorld().dropItem(location, ItemsManager.getLock());
                    Configurations.save();
                    Configurations.reload();
                }
            }
        }
    }

    private Location getLocation(Block block, Player player) {
        if (block != null) {
            if (WorldUtils.isDoor(block)) {
                // Ведущим блоков у двери является её нижняя часть (нижний блок)
                // TODO Для двери надо добавить проверку на то, что нажатые блоки относится к одной двери
                // TODO Если норм работает, то вынести в отдельный метод
                // TODO не работает, кста
                if (block == block.getRelative(0, 1, 0)) {
                    System.out.println("Dveri ravni");
                }
                // Если сверху имеется второй блок двери, то возвращаем текущий блок
                if (WorldUtils.isDoor(block.getRelative(0, 1, 0)))
                    return block.getLocation();
                // Иначе возвращаем блок снизу
                else return block.getRelative(0, -1, 0).getLocation();
            }
            else if (WorldUtils.isDoubleChest(block) && player != null) {
                // Ведущим блоком у двойного сундука является его левая часть (левый блок)
                DoubleChestInventory inventory = (DoubleChestInventory) ((Chest) block.getState()).getInventory();
                return inventory.getLeftSide().getLocation();
            }
            else if (WorldUtils.isChest(block) || WorldUtils.isTrapdoor(block) || WorldUtils.isBarrel(block)) {
                return block.getLocation();
            }
        }
        return null;
    }
}
