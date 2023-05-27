package org.example.dodalock.dodalock.gui.menu;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.example.dodalock.dodalock.gui.GuiItemsManager;
import org.example.dodalock.dodalock.gui.holders.CodeLockMenuHolder;
import org.example.dodalock.dodalock.items.ItemsManager;
import org.example.dodalock.dodalock.logic.CheckPlayersAttempts;
import org.example.dodalock.dodalock.utils.config.Configurations;

public class CodeLockMenu extends CodeLockMenuHolder {
    private final ItemStack EMPTY_FIELD = makeItem(Material.WHITE_STAINED_GLASS_PANE,
            Configurations.getLanguage().translate("gui.plug"));
    private final ItemStack CONFIRM_BUTTON = makeItem(Material.LIME_STAINED_GLASS_PANE, ChatColor.GREEN +
            Configurations.getLanguage().translate("gui.confirm"));
    private final ItemStack CLEAR_BUTTON = makeItem(Material.RED_STAINED_GLASS_PANE, ChatColor.RED +
            Configurations.getLanguage().translate("gui.clear"));
    private final String location;
    private String password = "";

    public CodeLockMenu(Player player, String location) {
        super(player);
        this.location = location;
    }

    @Override
    public String getMenuName() {
        return "Enter a Code";
    }

    @Override
    public int getSlots() {
        return 54;
    }

    @Override
    public void handleMenu(InventoryClickEvent event) {
        event.setCancelled(true);
        if (event.getInventory().equals(this.getInventory())) {
            ItemStack clickedItem = event.getCurrentItem();
            if (clickedItem != null && clickedItem.getItemMeta() != null &&
                clickedItem.getItemMeta().getDisplayName() != null) {
                    if (isCodeLockButtons(clickedItem)) {
                        if (inventory.getItem(16).equals(EMPTY_FIELD)) {
                            inventory.setItem(16, clickedItem);
                            password += clickedItem.getItemMeta().getDisplayName();
                        }
                        else if (inventory.getItem(25).equals(EMPTY_FIELD)) {
                            inventory.setItem(25, clickedItem);
                            password += clickedItem.getItemMeta().getDisplayName();
                        }
                        else if (inventory.getItem(34).equals(EMPTY_FIELD)) {
                            inventory.setItem(34, clickedItem);
                            password += clickedItem.getItemMeta().getDisplayName();
                        }
                        else if (inventory.getItem(43).equals(EMPTY_FIELD)) {
                            inventory.setItem(43, clickedItem);
                            password += clickedItem.getItemMeta().getDisplayName();
                        }
                    }
                    else if (isOptionsButtons(clickedItem)) {
                        if (clickedItem.equals(CONFIRM_BUTTON)) {
                            if (!inventory.getItem(43).equals(EMPTY_FIELD)) {
                                // Кодовый замок отсутствует на двери -> добавление замка, пароля и игрока в конфиг
                                if (!Configurations.getConfig().isCodeLock(location)) {
                                    Configurations.getConfig().addCodeLock(password, player, location);
                                    if (player.getEquipment().getItemInMainHand().equals(ItemsManager.getCodeLock()) &&
                                        player.getEquipment().getItemInMainHand().getAmount() > 0) {
                                            player.getEquipment().getItemInMainHand().setAmount(player.getEquipment()
                                                    .getItemInMainHand().getAmount() - 1);
                                            player.updateInventory();
                                    }
                                    password = "";
                                    player.closeInventory();
                                }
                                // Кодовый замок имеется на двери, но игрок ни разу не открывал дверь, при этом
                                // он правильно ввёл пароль -> добавление игрока в конфиг
                                else if (Configurations.getConfig().isCodeLock(location) &&
                                    !Configurations.getConfig().isPlayerInCodeLock(location, player) &&
                                    Configurations.getConfig().isTruePassword(location, password)) {
                                        Configurations.getConfig().addPlayerInCodeLockData(location, player);
                                        player.closeInventory();
                                }
                                // Кодовый замок имеется на двери, но игрок ни разу не открывал дверь, при этом
                                // он НЕправильно ввёл пароль -> проверка кол-ва попыток открытия двери (при 3 наносится урон)
                                else if (Configurations.getConfig().isCodeLock(location) &&
                                    !Configurations.getConfig().isPlayerInCodeLock(location, player) &&
                                    !Configurations.getConfig().isTruePassword(location, password)) {
                                        CheckPlayersAttempts.addAttempt(location, player);
                                        if (CheckPlayersAttempts.getAttempts(location, player) >= 3) {
                                            CheckPlayersAttempts.removeAttempts(location, player);
                                            CheckPlayersAttempts.removeLocation(location);
                                            player.closeInventory();
                                            player.damage(6);
                                        }
                                }
                                // Кодовый замок имеется на двери, игрок уже открывал дверь, при этом
                                // пароль введён неправильно -> смена пароля для указанного замка
                                else if (Configurations.getConfig().isCodeLock(location) &&
                                    Configurations.getConfig().isPlayerInCodeLock(location, player) &&
                                    !Configurations.getConfig().isTruePassword(location, password)) {
                                        Configurations.getConfig().changePassword(location, password);
                                        player.closeInventory();
                                }
                                inventory.setItem(16, EMPTY_FIELD);
                                inventory.setItem(25, EMPTY_FIELD);
                                inventory.setItem(34, EMPTY_FIELD);
                                inventory.setItem(43, EMPTY_FIELD);
                                password = "";
                            }
                        }
                        else if (clickedItem.equals(CLEAR_BUTTON)) {
                            inventory.setItem(16, EMPTY_FIELD);
                            inventory.setItem(25, EMPTY_FIELD);
                            inventory.setItem(34, EMPTY_FIELD);
                            inventory.setItem(43, EMPTY_FIELD);
                            password = "";
                        }
                        Configurations.save();
                        Configurations.reload();
                    }
            }
        }
    }

    @Override
    public void setMenuItems() {
        // CodeLock buttons
        inventory.setItem(10, makeItem(Material.GRAY_STAINED_GLASS_PANE,
                Configurations.getLanguage().translate("gui.number_1")));
        inventory.setItem(11, makeItem(Material.BROWN_STAINED_GLASS_PANE,
                Configurations.getLanguage().translate("gui.number_2")));
        inventory.setItem(12, makeItem(Material.ORANGE_STAINED_GLASS_PANE,
                Configurations.getLanguage().translate("gui.number_3")));
        inventory.setItem(19, makeItem(Material.YELLOW_STAINED_GLASS_PANE,
                Configurations.getLanguage().translate("gui.number_4")));
        inventory.setItem(20, makeItem(Material.GREEN_STAINED_GLASS_PANE,
                Configurations.getLanguage().translate("gui.number_5")));
        inventory.setItem(21, makeItem(Material.LIGHT_BLUE_STAINED_GLASS_PANE,
                Configurations.getLanguage().translate("gui.number_6")));
        inventory.setItem(28, makeItem(Material.CYAN_STAINED_GLASS_PANE,
                Configurations.getLanguage().translate("gui.number_7")));
        inventory.setItem(29, makeItem(Material.BLUE_STAINED_GLASS_PANE,
                Configurations.getLanguage().translate("gui.number_8")));
        inventory.setItem(30, makeItem(Material.MAGENTA_STAINED_GLASS_PANE,
                Configurations.getLanguage().translate("gui.number_9")));
        inventory.setItem(38, makeItem(Material.PINK_STAINED_GLASS_PANE,
                Configurations.getLanguage().translate("gui.number_0")));

        // Confirm and Clear buttons
        inventory.setItem(23, CONFIRM_BUTTON);
        inventory.setItem(32, CLEAR_BUTTON);

        // Enter code field
        inventory.setItem(16, EMPTY_FIELD);
        inventory.setItem(25, EMPTY_FIELD);
        inventory.setItem(34, EMPTY_FIELD);
        inventory.setItem(43, EMPTY_FIELD);

        // Inventory parts
        for (int i = 0; i < inventory.getSize(); i++) {
            if (inventory.getItem(i) == null) {
                inventory.setItem(i, GuiItemsManager.getInventoryPart());
            }
        }
    }

    public boolean isCodeLockButtons(ItemStack item) {
        return item.getType().equals(Material.GRAY_STAINED_GLASS_PANE) ||
                item.getType().equals(Material.BROWN_STAINED_GLASS_PANE) ||
                item.getType().equals(Material.ORANGE_STAINED_GLASS_PANE) ||
                item.getType().equals(Material.YELLOW_STAINED_GLASS_PANE) ||
                item.getType().equals(Material.GREEN_STAINED_GLASS_PANE) ||
                item.getType().equals(Material.LIGHT_BLUE_STAINED_GLASS_PANE) ||
                item.getType().equals(Material.CYAN_STAINED_GLASS_PANE) ||
                item.getType().equals(Material.BLUE_STAINED_GLASS_PANE) ||
                item.getType().equals(Material.MAGENTA_STAINED_GLASS_PANE) ||
                item.getType().equals(Material.PINK_STAINED_GLASS_PANE);
    }

    private boolean isOptionsButtons(ItemStack item) {
        return item.getType().equals(Material.LIME_STAINED_GLASS_PANE) ||
                item.getType().equals(Material.RED_STAINED_GLASS_PANE);
    }
}
