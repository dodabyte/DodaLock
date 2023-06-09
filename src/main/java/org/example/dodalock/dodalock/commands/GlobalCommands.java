package org.example.dodalock.dodalock.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.example.dodalock.dodalock.DodaLockMain;
import org.example.dodalock.dodalock.items.ItemsManager;
import org.example.dodalock.dodalock.utils.ChatUtils;
import org.example.dodalock.dodalock.utils.config.Configurations;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

public class GlobalCommands implements CommandExecutor {

    public GlobalCommands(DodaLockMain plugin) {
        Objects.requireNonNull(plugin.getCommand("dodalock")).setExecutor(this);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            System.out.println("Only a player can use this command.");
            return true;
        }

        if (args.length < 1 || args[0].equalsIgnoreCase("help")) {
            ChatUtils.printHelp(player);
            return true;
        }

        if (args[0].equalsIgnoreCase("list")) {
            if (args.length < 2) {
                ChatUtils.printMessage(player, "error.name_list_missing");
                return true;
            }
            else if (args.length > 2) {
                StringBuilder enteredCommand = new StringBuilder(label);
                for (String arg : args) {
                    enteredCommand.append(" ").append(arg);
                }
                String here = Configurations.getLanguage().translate("error.here");
                enteredCommand.append(ChatColor.ITALIC).append("<--[").append(here).append("]");
                ChatUtils.printError(player,
                        Configurations.getLanguage().translate("error.invalid_argument") +
                                ":\n" + enteredCommand);
                return true;
            }

            if (args[1].equalsIgnoreCase("all")) {
                ChatUtils.printAllList(player);
            }
            else if (args[1].equalsIgnoreCase("codelocks")) {
                ChatUtils.printCodeLocksList(player);
            }
            else if (args[1].equalsIgnoreCase("locks")) {
                ChatUtils.printLocksList(player);
            }
            else {
                ChatUtils.printMessage(player, "error.name_list_not_exist");
            }
            return true;
        }

        if (args[0].equalsIgnoreCase("remove")) {
            if (args.length < 2) {
                ChatUtils.printMessage(player, "error.name_list_missing");
                return true;
            }
            else if (args.length < 3) {
                ChatUtils.printMessage(player, "error.num_or_loc_missing");
                return true;
            }
            else if (args.length > 3) {
                StringBuilder enteredCommand = new StringBuilder(label);
                for (String arg : args) {
                    enteredCommand.append(" ").append(arg);
                }
                String here = Configurations.getLanguage().translate("error.here");
                enteredCommand.append(ChatColor.ITALIC).append("<--[").append(here).append("]");
                ChatUtils.printError(player,
                        Configurations.getLanguage().translate("error.invalid_argument") +
                                ":\n" + enteredCommand);
                return true;
            }

            if (!args[1].equalsIgnoreCase("codelock") && !args[1].equalsIgnoreCase("lock")) {
                ChatUtils.printMessage(player, "error.name_list_not_exist");
                return true;
            }

            if (args[2].matches("\\d+")) { // Если только числа, значит выбор по номеру в списке
                int num = Integer.parseInt(args[2]);
                if (args[1].equalsIgnoreCase("codelock") &&
                        Configurations.getLocks().getCodeLocksListWithPlayer(player).get(num - 1) != null &&
                        !Configurations.getLocks().getCodeLocksListWithPlayer(player).get(num - 1).equals("")) {
                    Configurations.getLocks().removeCodeLock(Configurations.getLocks().getCodeLocksListWithPlayer(player).get(num - 1));
                    ChatUtils.printMessage(player, "success.remove_code_lock");
                }
                else if (args[1].equalsIgnoreCase("lock") &&
                        Configurations.getLocks().getLocksListWithOwner(player).get(num - 1) != null &&
                        !Configurations.getLocks().getLocksListWithOwner(player).get(num - 1).equals("")) {
                    Configurations.getLocks().removeLock(Configurations.getLocks().getLocksListWithOwner(player).get(num - 1));
                    ChatUtils.printMessage(player, "success.remove_lock");
                }
                else {
                    ChatUtils.printMessage(player, "error.num_not_exist");
                    return true;
                }
            }
            else if (args[2].matches("[a-z]+([_][-]?[0-9]+){3}")) { // Иначе выбор по названию локации в списке
                if (args[1].equalsIgnoreCase("codelock") && Configurations.getLocks().isCodeLock(args[2]) &&
                        Configurations.getLocks().isPlayerInCodeLock(args[2], player)) {
                    Configurations.getLocks().removeCodeLock(args[2]);
                    Configurations.getLocks().save();
                    Configurations.getLocks().reload();
                    ChatUtils.printMessage(player, "success.remove_code_lock");
                }
                else if (args[1].equalsIgnoreCase("lock") && Configurations.getLocks().isLock(args[2]) &&
                        Configurations.getLocks().isOwnerInLock(args[2], player)) {
                    Configurations.getLocks().removeLock(args[2]);
                    Configurations.getLocks().save();
                    Configurations.getLocks().reload();
                    ChatUtils.printMessage(player, "success.remove_lock");
                }
                else {
                    ChatUtils.printMessage(player, "error.loc_not_exist");
                    return true;
                }
            }
            else {
                ChatUtils.printMessage(player, "error.num_or_loc_not_exist");
            }
            Configurations.getLocks().save();
            Configurations.getLocks().reload();

            return true;
        }

        if (args[0].equalsIgnoreCase("clear")) {
            if (args.length < 2) {
                ChatUtils.printMessage(player, "error.name_list_missing");
                return true;
            }
            else if (args.length > 2) {
                StringBuilder enteredCommand = new StringBuilder(label);
                for (String arg : args) {
                    enteredCommand.append(" ").append(arg);
                }
                String here = Configurations.getLanguage().translate("error.here");
                enteredCommand.append(ChatColor.ITALIC).append("<--[").append(here).append("]");
                ChatUtils.printError(player,
                        Configurations.getLanguage().translate("error.invalid_argument") +
                                ":\n" + enteredCommand);
                return true;
            }

            List<String> locksList;
            if (args[1].equalsIgnoreCase("all")) {
                locksList = Configurations.getLocks().getCodeLockData();
                locksList.addAll(Configurations.getLocks().getLockData());
            }
            else if (args[1].equalsIgnoreCase("codelock")) {
                locksList = Configurations.getLocks().getCodeLockData();
            }
            else if (args[1].equalsIgnoreCase("lock")) {
                locksList = Configurations.getLocks().getLockData();
            }
            else {
                ChatUtils.printMessage(player, "error.name_list_not_exist");
                return true;
            }

            if (locksList != null) {
                for (String lock : locksList) {
                    if (Configurations.getLocks().isCodeLock(lock) && Configurations.getLocks().isPlayerInCodeLock(lock, player)) {
                        Configurations.getLocks().removeCodeLock(lock);
                    }
                    else if (Configurations.getLocks().isLock(lock) && Configurations.getLocks().isOwnerInLock(lock, player)) {
                        Configurations.getLocks().removeLock(lock);
                    }
                }
                Configurations.getLocks().save();
                Configurations.getLocks().reload();
                ChatUtils.printMessage(player, "success.clear_locks");
            }

            return true;
        }

        if (args[0].equalsIgnoreCase("clone")) {
            if (args.length > 1) {
                StringBuilder enteredCommand = new StringBuilder(label);
                for (String arg : args) {
                    enteredCommand.append(" ").append(arg);
                }
                String here = Configurations.getLanguage().translate("error.here");
                enteredCommand.append(ChatColor.ITALIC).append("<--[").append(here).append("]");
                ChatUtils.printError(player,
                        Configurations.getLanguage().translate("error.invalid_argument") +
                                ":\n" + enteredCommand);
                return true;
            }

            if (ItemsManager.isUsedKey(player.getEquipment().getItemInMainHand())) {
                if (player.getInventory().contains(ItemsManager.getKey().getItemStack())) {
                    player.getInventory().removeItem(ItemsManager.getKey().getItemStack());
                    player.getInventory().addItem(player.getEquipment().getItemInMainHand().clone());
                    ChatUtils.printMessage(player, "success.key_clone");
                }
                else {
                    ChatUtils.printMessage(player, "error.ordinary_key_for_clone_not_exist");
                }
            }
            else {
                ChatUtils.printMessage(player, "error.item_clone");
            }
            return true;
        }

        if (player.hasPermission("dodalock.give")) {
            if (args[0].equalsIgnoreCase("give")) {
                if (args.length < 2) {
                    ChatUtils.printMessage(player, "error.player_nickname_missing");
                    return true;
                }
                else if (args.length < 3) {
                    ChatUtils.printMessage(player, "error.name_item_missing");
                    return true;
                }
                else if (args.length > 3) {
                    StringBuilder enteredCommand = new StringBuilder(label);
                    for (String arg : args) {
                        enteredCommand.append(" ").append(arg);
                    }
                    String here = Configurations.getLanguage().translate("error.here");
                    enteredCommand.append(ChatColor.ITALIC).append("<--[").append(here).append("]");
                    ChatUtils.printError(player,
                            Configurations.getLanguage().translate("error.invalid_argument") +
                                    ":\n" + enteredCommand);
                    return true;
                }

                Player enterPlayer = Bukkit.getPlayer(args[1]);
                if (enterPlayer == null || !Bukkit.getOnlinePlayers().contains(enterPlayer)) {
                    ChatUtils.printMessage(player, "error.player_not_exist");
                    return true;
                }

                if (args[2].equalsIgnoreCase("key")) {
                    if (player.hasPermission("dodalock.give.key")) {
                        enterPlayer.getInventory().addItem(ItemsManager.getKey().getItemStack());
                    }
                    else {
                        ChatUtils.printMessage(player, "error.permission_give_command");
                    }
                }
                else if (args[2].equalsIgnoreCase("masterkey")) {
                    if (player.hasPermission("dodalock.give.masterkey")) {
                        enterPlayer.getInventory().addItem(ItemsManager.getMasterKey().getItemStack());
                    }
                    else {
                        ChatUtils.printMessage(player, "error.permission_give_command");
                    }
                }
                else if (args[2].equalsIgnoreCase("lock")) {
                    if (player.hasPermission("dodalock.give.lock")) {
                        enterPlayer.getInventory().addItem(ItemsManager.getLock().getItemStack());
                    }
                    else {
                        ChatUtils.printMessage(player, "error.permission_give_command");
                    }
                }
                else if (args[2].equalsIgnoreCase("codelock")) {
                    if (player.hasPermission("dodalock.give.codelock")) {
                        enterPlayer.getInventory().addItem(ItemsManager.getCodeLock().getItemStack());
                    }
                    else {
                        ChatUtils.printMessage(player, "error.permission_give_command");
                    }
                }
                else if (args[2].equalsIgnoreCase("bunchofkeys")) {
                    if (player.hasPermission("dodalock.give.bunchofkeys")) {
                        enterPlayer.getInventory().addItem(ItemsManager.getBunchKeys().getItemStack());
                    }
                    else {
                        ChatUtils.printMessage(player, "error.permission_give_command");
                    }
                }
                else {
                    ChatUtils.printMessage(player, "error.name_item_not_exist");
                }
                return true;
            }
        }
        else {
            ChatUtils.printMessage(player, "error.permission_give_command");
            return true;
        }

        if (args[0].equalsIgnoreCase("reload")) {
            if (args.length > 1) {
                StringBuilder enteredCommand = new StringBuilder(label);
                for (String arg : args) {
                    enteredCommand.append(" ").append(arg);
                }
                String here = Configurations.getLanguage().translate("error.here");
                enteredCommand.append(ChatColor.ITALIC).append("<--[").append(here).append("]");
                ChatUtils.printError(player,
                        Configurations.getLanguage().translate("error.invalid_argument") +
                                ":\n" + enteredCommand);
                return true;
            }

            Configurations.reload();
            ChatUtils.printMessage(player, "success.reloaded");
            return true;
        }

        return true;
    }
}
