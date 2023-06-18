package org.example.dodalock.dodalock.utils;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.example.dodalock.dodalock.utils.config.Configurations;

import java.util.List;

public class ChatUtils {
    public static void printError(Player player, String message) {
        player.sendMessage(ChatColor.RED + message);
    }

    public static void printSuccess(Player player, String message) {
        player.sendMessage(ChatColor.GREEN + message);
    }

    public static void printMessage(Player player, String key) {
        try {
            String[] keys = key.split("\\.");
            ChatColor color;
            switch (keys[0]) {
                case "success":
                    color = ChatColor.GREEN;
                    break;
                case "error":
                    color = ChatColor.RED;
                    break;
                default:
                    color = ChatColor.WHITE;
                    break;
            }
            player.sendMessage(color + Configurations.getLanguage().translate(key));
        }
        catch (Exception e) {
            System.out.println("Error: Key '" + key + "' does not exist in the localization file. Error message: " + e);
        }
    }

    public static void printHelp(Player player) {
        String message = ChatColor.YELLOW + "--------- " + ChatColor.WHITE +
                Configurations.getLanguage().translate("help.help_list") + ChatColor.YELLOW + " ---------\n" +
                ChatColor.GOLD + "/dodalock: "
                + ChatColor.WHITE + Configurations.getLanguage().translate("help.help_command") + "\n" +
                ChatColor.GOLD + "/dodalock help: "
                + ChatColor.WHITE + Configurations.getLanguage().translate("help.help_command") + "\n" +
                ChatColor.GOLD + "/dodalock list [all | codelocks | locks]: "
                + ChatColor.WHITE + Configurations.getLanguage().translate("help.list_command") + "\n" +
                ChatColor.GOLD + "/dodalock remove [codelock | lock] [" + ChatColor.ITALIC + "number" +
                ChatColor.RESET + ChatColor.GOLD + " | " + ChatColor.ITALIC + "location" + ChatColor.RESET + ChatColor.GOLD + "]: "
                + ChatColor.WHITE + Configurations.getLanguage().translate("help.remove_command") + "\n" +
                ChatColor.GOLD + "/dodalock clear [all | codelock | lock]: "
                + ChatColor.WHITE + Configurations.getLanguage().translate("help.clear_command") + "\n" +
                ChatColor.GOLD + "/dodalock clone: "
                + ChatColor.WHITE + Configurations.getLanguage().translate("help.clone_command") + "\n" +
                ChatColor.GOLD + "/dodalock give " + ChatColor.ITALIC + "player" + ChatColor.RESET + ChatColor.GOLD +
                " [codelock | lock | key | masterkey | bunchofkeys]: "
                + ChatColor.WHITE + Configurations.getLanguage().translate("help.give_command") + "\n" +
                ChatColor.GOLD + "/dodalock reload: "
                + ChatColor.WHITE + Configurations.getLanguage().translate("help.reload_command") + "\n";
        player.sendMessage(message);
    }

    public static void printAllList(Player player) {
        printCodeLocksList(player);
        printLocksList(player);
    }

    public static void printCodeLocksList(Player player) {
        StringBuilder message = new StringBuilder(ChatColor.YELLOW + "--------- " + ChatColor.WHITE +
                Configurations.getLanguage().translate("data.code_locks_list") + ChatColor.YELLOW + " ---------\n");
        List<String> codeLocksList = Configurations.getLocks().getCodeLocksListWithPlayer(player);
        if (codeLocksList.size() < 1) return;
        for (int i = 0; i < codeLocksList.size(); i++) {
            if (Configurations.getLocks().isPlayerInCodeLock(codeLocksList.get(i), player)) {
                message.append(i + 1).append(") ").append(codeLocksList.get(i)).append("\n");
            }
        }
        player.sendMessage(message.toString());
    }

    public static void printLocksList(Player player) {
        StringBuilder message = new StringBuilder(ChatColor.YELLOW + "--------- " + ChatColor.WHITE +
                Configurations.getLanguage().translate("data.locks_list") + ChatColor.YELLOW + " ---------\n");
        List<String> locksList = Configurations.getLocks().getLocksListWithOwner(player);
        if (locksList.size() < 1) return;
        for (int i = 0; i < locksList.size(); i++) {
            message.append(i + 1).append(") ").append(locksList.get(i)).append("\n");
        }
        player.sendMessage(message.toString());
    }
}
