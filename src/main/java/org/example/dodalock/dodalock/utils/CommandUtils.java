package org.example.dodalock.dodalock.utils;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.example.dodalock.dodalock.utils.config.Configurations;

public class CommandUtils {
    // TODO Исправить на команды текущего плагина
    public static void printHelp(Player player) {
        String message = ChatColor.YELLOW + "--------- " + ChatColor.WHITE +
                Configurations.getLanguage().translate("ve_help_list") + ChatColor.YELLOW + " ---------\n" +
                ChatColor.GOLD + "/variousenchantments: "
                + ChatColor.WHITE + Configurations.getLanguage().translate("help.help_command") + "\n" +
                ChatColor.GOLD + "/variousenchantments help: "
                + ChatColor.WHITE + Configurations.getLanguage().translate("help.help_command") + "\n" +
                ChatColor.GOLD + "/variousenchantments list: "
                + ChatColor.WHITE + Configurations.getLanguage().translate("help.list_command") + "\n" +
                ChatColor.GOLD + "/variousenchant: "
                + ChatColor.WHITE + Configurations.getLanguage().translate("help.enchant_command") + "\n" +
                ChatColor.GOLD + "/variousenchantgui: "
                + ChatColor.WHITE + Configurations.getLanguage().translate("help.enchant_gui_command") + "\n";
        player.sendMessage(message);
    }

    public static void printError(Player player, String message) {
        player.sendMessage(ChatColor.RED + message);
    }

    public static void printSuccess(Player player, String message) {
        player.sendMessage(ChatColor.GREEN + message);
    }
}
