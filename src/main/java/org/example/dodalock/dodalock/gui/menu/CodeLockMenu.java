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
import org.example.dodalock.dodalock.utils.ChatUtils;
import org.example.dodalock.dodalock.utils.config.Configurations;

public class CodeLockMenu extends CodeLockMenuHolder {
    private final String location;
    private String password = "";

    public CodeLockMenu(Player player, String location) {
        super(player);
        this.location = location;
    }

    @Override
    public String getMenuName() {
        if (Configurations.getLocks().isCodeLock(location) &&
                Configurations.getLocks().isPlayerInCodeLock(location, player))
            return "Change a Code";
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
                        if (isEmptyField(inventory.getItem(16))) {
                            inventory.setItem(16, getFieldFromButton(clickedItem));
                            password += clickedItem.getItemMeta().getDisplayName();
                        }
                        else if (isEmptyField(inventory.getItem(25))) {
                            inventory.setItem(25, getFieldFromButton(clickedItem));
                            password += clickedItem.getItemMeta().getDisplayName();
                        }
                        else if (isEmptyField(inventory.getItem(34))) {
                            inventory.setItem(34, getFieldFromButton(clickedItem));
                            password += clickedItem.getItemMeta().getDisplayName();
                        }
                        else if (isEmptyField(inventory.getItem(43))) {
                            inventory.setItem(43, getFieldFromButton(clickedItem));
                            password += clickedItem.getItemMeta().getDisplayName();
                        }
                    }
                    else if (isOptionsButtons(clickedItem)) {
                        if (isEnterButton(clickedItem)) {
                            if (!isEmptyField(inventory.getItem(43))) {
                                // Кодовый замок отсутствует на двери -> добавление замка, пароля и игрока в конфиг
                                if (!Configurations.getLocks().isCodeLock(location)) {
                                    Configurations.getLocks().addCodeLock(password, player, location);
                                    if (ItemsManager.isCodeLock(player.getEquipment().getItemInMainHand()) &&
                                        player.getEquipment().getItemInMainHand().getAmount() > 0) {
                                            player.getEquipment().getItemInMainHand().setAmount(player.getEquipment()
                                                    .getItemInMainHand().getAmount() - 1);
                                            player.updateInventory();
                                    }
                                    password = "";
                                    player.closeInventory();
                                    ChatUtils.printMessage(player, "success.installing_code_lock");
                                }
                                // Кодовый замок имеется на двери, но игрок ни разу не открывал дверь, при этом
                                // он правильно ввёл пароль -> добавление игрока в конфиг
                                else if (Configurations.getLocks().isCodeLock(location) &&
                                    !Configurations.getLocks().isPlayerInCodeLock(location, player) &&
                                    Configurations.getLocks().isTruePassword(location, password)) {
                                        Configurations.getLocks().addPlayerInCodeLockData(location, player);
                                        player.closeInventory();
                                        ChatUtils.printMessage(player, "success.add_player_in_code_lock");
                                }
                                // Кодовый замок имеется на двери, но игрок ни разу не открывал дверь, при этом
                                // он НЕправильно ввёл пароль -> проверка кол-ва попыток открытия двери (при 3 наносится урон)
                                else if (Configurations.getLocks().isCodeLock(location) &&
                                    !Configurations.getLocks().isPlayerInCodeLock(location, player) &&
                                    !Configurations.getLocks().isTruePassword(location, password)) {
                                        CheckPlayersAttempts.addAttempt(location, player);
                                        if (CheckPlayersAttempts.getAttempts(location, player) >= 3) {
                                            CheckPlayersAttempts.removeAttempts(location, player);
                                            CheckPlayersAttempts.removeLocation(location);
                                            player.closeInventory();
                                            player.damage(6);
                                            ChatUtils.printMessage(player, "error.damage_for_exceeding_attempts");
                                        }
                                        else ChatUtils.printMessage(player, "error.invalid_password_with_attempts");
                                }
                                // Кодовый замок имеется на двери, игрок уже открывал дверь, при этом
                                // пароль введён неправильно -> смена пароля для указанного замка
                                else if (Configurations.getLocks().isCodeLock(location) &&
                                    Configurations.getLocks().isPlayerInCodeLock(location, player) &&
                                    !Configurations.getLocks().isTruePassword(location, password)) {
                                        Configurations.getLocks().changePassword(location, password);
                                        player.closeInventory();
                                        ChatUtils.printMessage(player, "success.change_password_code_lock");
                                }
                                inventory.setItem(16, GuiItemsManager.getInventoryField().getItemStack());
                                inventory.setItem(25, GuiItemsManager.getInventoryField().getItemStack());
                                inventory.setItem(34, GuiItemsManager.getInventoryField().getItemStack());
                                inventory.setItem(43, GuiItemsManager.getInventoryField().getItemStack());
                                password = "";
                            }
                        }
                        else if (isClearButton(clickedItem)) {
                            inventory.setItem(16, GuiItemsManager.getInventoryField().getItemStack());
                            inventory.setItem(25, GuiItemsManager.getInventoryField().getItemStack());
                            inventory.setItem(34, GuiItemsManager.getInventoryField().getItemStack());
                            inventory.setItem(43, GuiItemsManager.getInventoryField().getItemStack());
                            password = "";
                        }
                        Configurations.getLocks().save();
                        Configurations.getLocks().reload();
                    }
            }
        }
    }

    @Override
    public void setMenuItems() {
        // CodeLock buttons
        inventory.setItem(10, GuiItemsManager.getInventoryButton1().getItemStack());
        inventory.setItem(11, GuiItemsManager.getInventoryButton2().getItemStack());
        inventory.setItem(12, GuiItemsManager.getInventoryButton3().getItemStack());
        inventory.setItem(13, GuiItemsManager.getInventoryPartWithBorderLeft().getItemStack());
        inventory.setItem(19, GuiItemsManager.getInventoryButton4().getItemStack());
        inventory.setItem(20, GuiItemsManager.getInventoryButton5().getItemStack());
        inventory.setItem(21, GuiItemsManager.getInventoryButton6().getItemStack());
        inventory.setItem(22, GuiItemsManager.getInventoryPartWithBorderLeft().getItemStack());
        inventory.setItem(28, GuiItemsManager.getInventoryButton7().getItemStack());
        inventory.setItem(29, GuiItemsManager.getInventoryButton8().getItemStack());
        inventory.setItem(30, GuiItemsManager.getInventoryButton9().getItemStack());
        inventory.setItem(31, GuiItemsManager.getInventoryPartWithBorderLeft().getItemStack());
        inventory.setItem(37, GuiItemsManager.getInventoryPartWithBorderUp().getItemStack());
        inventory.setItem(38, GuiItemsManager.getInventoryButton0().getItemStack());
        inventory.setItem(39, GuiItemsManager.getInventoryPartWithBorderLeftUp().getItemStack());
        inventory.setItem(40, GuiItemsManager.getInventoryPartWithBorderDotLeftUp().getItemStack());
        inventory.setItem(47, GuiItemsManager.getInventoryPartWithBorderUp().getItemStack());

        // Confirm and Clear buttons
        inventory.setItem(23, GuiItemsManager.getInventoryButtonE().getItemStack());
        inventory.setItem(24, GuiItemsManager.getInventoryPartWithBorderLeft().getItemStack());
        inventory.setItem(32, GuiItemsManager.getInventoryButtonC().getItemStack());
        inventory.setItem(33, GuiItemsManager.getInventoryPartWithBorderLeft().getItemStack());
        inventory.setItem(41, GuiItemsManager.getInventoryPartWithBorderUp().getItemStack());
        inventory.setItem(42, GuiItemsManager.getInventoryPartWithBorderDotLeftUp().getItemStack());

        // Enter code field
        inventory.setItem(16, GuiItemsManager.getInventoryField().getItemStack());
        inventory.setItem(17, GuiItemsManager.getInventoryPartWithBorderLeft().getItemStack());
        inventory.setItem(25, GuiItemsManager.getInventoryField().getItemStack());
        inventory.setItem(26, GuiItemsManager.getInventoryPartWithBorderLeft().getItemStack());
        inventory.setItem(34, GuiItemsManager.getInventoryField().getItemStack());
        inventory.setItem(35, GuiItemsManager.getInventoryPartWithBorderLeft().getItemStack());
        inventory.setItem(43, GuiItemsManager.getInventoryField().getItemStack());
        inventory.setItem(44, GuiItemsManager.getInventoryPartWithBorderLeft().getItemStack());
        inventory.setItem(52, GuiItemsManager.getInventoryPartWithBorderUp().getItemStack());
        inventory.setItem(53, GuiItemsManager.getInventoryPartWithBorderDotLeftUp().getItemStack());

        // Inventory parts
        for (int i = 0; i < inventory.getSize(); i++) {
            if (inventory.getItem(i) == null) {
                inventory.setItem(i, GuiItemsManager.getInventoryPart().getItemStack());
            }
        }
    }

    private boolean isCodeLockButtons(ItemStack item) {
        return item.equals(GuiItemsManager.getInventoryButton0().getItemStack()) ||
                item.equals(GuiItemsManager.getInventoryButton1().getItemStack()) ||
                item.equals(GuiItemsManager.getInventoryButton2().getItemStack()) ||
                item.equals(GuiItemsManager.getInventoryButton3().getItemStack()) ||
                item.equals(GuiItemsManager.getInventoryButton4().getItemStack()) ||
                item.equals(GuiItemsManager.getInventoryButton5().getItemStack()) ||
                item.equals(GuiItemsManager.getInventoryButton6().getItemStack()) ||
                item.equals(GuiItemsManager.getInventoryButton7().getItemStack()) ||
                item.equals(GuiItemsManager.getInventoryButton8().getItemStack()) ||
                item.equals(GuiItemsManager.getInventoryButton9().getItemStack());
    }

    private ItemStack getFieldFromButton(ItemStack item) {
        if (item.equals(GuiItemsManager.getInventoryButton0().getItemStack()))
            return GuiItemsManager.getInventoryField0().getItemStack();
        else if (item.equals(GuiItemsManager.getInventoryButton1().getItemStack()))
            return GuiItemsManager.getInventoryField1().getItemStack();
        else if (item.equals(GuiItemsManager.getInventoryButton2().getItemStack()))
            return GuiItemsManager.getInventoryField2().getItemStack();
        else if (item.equals(GuiItemsManager.getInventoryButton3().getItemStack()))
            return GuiItemsManager.getInventoryField3().getItemStack();
        else if (item.equals(GuiItemsManager.getInventoryButton4().getItemStack()))
            return GuiItemsManager.getInventoryField4().getItemStack();
        else if (item.equals(GuiItemsManager.getInventoryButton5().getItemStack()))
            return GuiItemsManager.getInventoryField5().getItemStack();
        else if (item.equals(GuiItemsManager.getInventoryButton6().getItemStack()))
            return GuiItemsManager.getInventoryField6().getItemStack();
        else if (item.equals(GuiItemsManager.getInventoryButton7().getItemStack()))
            return GuiItemsManager.getInventoryField7().getItemStack();
        else if (item.equals(GuiItemsManager.getInventoryButton8().getItemStack()))
            return GuiItemsManager.getInventoryField8().getItemStack();
        else if (item.equals(GuiItemsManager.getInventoryButton9().getItemStack()))
            return GuiItemsManager.getInventoryField9().getItemStack();
        return GuiItemsManager.getInventoryField().getItemStack();
    }

    private boolean isClearButton(ItemStack item) {
        return item.equals(GuiItemsManager.getInventoryButtonC().getItemStack());
    }

    private boolean isEnterButton(ItemStack item) {
        return item.equals(GuiItemsManager.getInventoryButtonE().getItemStack());
    }

    private boolean isOptionsButtons(ItemStack item) {
        return isEnterButton(item) || isClearButton(item);
    }

    private boolean isEmptyField(ItemStack item) {
        return item.equals(GuiItemsManager.getInventoryField().getItemStack());
    }
}
