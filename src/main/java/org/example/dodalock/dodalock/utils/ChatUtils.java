package org.example.dodalock.dodalock.utils;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.example.dodalock.dodalock.utils.config.Configurations;

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
}
