package org.example.dodalock.dodalock.utils.config;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.ItemMeta;
import org.example.dodalock.dodalock.DodaLockMain;
import org.example.dodalock.dodalock.items.ItemsManager;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class LanguageConfiguration {
    private FileConfiguration fileLanguageConfiguration;

    public LanguageConfiguration(String locale){
        if (locale.contains(File.separator)) throw new RuntimeException("Locale name inputStream not valid");
        if (locale.equals("system")) locale = System.getProperty("user.language");

        InputStream inputStream = null;
        inputStream = DodaLockMain.getPlugin().getResource("localization/" + locale + ".yml");
        if (inputStream == null) {
            inputStream = DodaLockMain.getPlugin().getResource("localization/en_us.yml");
        }

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            fileLanguageConfiguration = YamlConfiguration.loadConfiguration(reader);
        }
        catch (NullPointerException e) { System.out.println("Error in InputStreamReader: " + e); }
    }

    public String translate(String key) {
        return fileLanguageConfiguration.getString(key);
    }

    public void checkItemsLocalization(Player player) {
        if (player.getInventory().contains(ItemsManager.getCodeLock().getItemStack()) ||
                player.getInventory().contains(ItemsManager.getLock().getItemStack()) ||
                player.getInventory().contains(ItemsManager.getKey().getItemStack()) ||
                player.getInventory().contains(ItemsManager.getBunchKeys().getItemStack())) {
            for (int i = 0; i < player.getInventory().getSize(); i++) {
                if (player.getInventory().getItem(i) != null &&
                        !player.getInventory().getItem(i).getItemMeta().getDisplayName().contains(ChatColor.ITALIC + "")) {
                    if (ItemsManager.isCodeLock(player.getInventory().getItem(i))) {
                        ItemMeta itemMeta = player.getInventory().getItem(i).getItemMeta();
                        itemMeta.setDisplayName(ChatColor.RESET + translate("items_name.code_lock"));
                        player.getInventory().getItem(i).setItemMeta(itemMeta);
                    } else if (ItemsManager.isLock(player.getInventory().getItem(i))) {
                        ItemMeta itemMeta = player.getInventory().getItem(i).getItemMeta();
                        itemMeta.setDisplayName(ChatColor.RESET + translate("items_name.lock"));
                        player.getInventory().getItem(i).setItemMeta(itemMeta);
                    } else if (ItemsManager.isKey(player.getInventory().getItem(i)) ||
                            (player.getInventory().getItemInMainHand().getItemMeta() != null &&
                            player.getInventory().getItem(i).getItemMeta().getPersistentDataContainer().getKeys().size() == 1 &&
                            player.getInventory().getItem(i).getItemMeta().getPersistentDataContainer().getKeys().
                            toArray()[0].toString().matches("[d][o][d][a][l][o][c][k][:][a-z]+([_][0-9]+){3}"))) {
                        ItemMeta itemMeta = player.getInventory().getItem(i).getItemMeta();
                        itemMeta.setDisplayName(ChatColor.RESET + translate("items_name.key"));
                        player.getInventory().getItem(i).setItemMeta(itemMeta);
                    } else if (ItemsManager.isBunchKeys(player.getInventory().getItem(i)) ||
                            (player.getInventory().getItem(i).getItemMeta() != null &&
                            player.getInventory().getItem(i).getItemMeta().getPersistentDataContainer().getKeys().size() == 1 &&
                            player.getInventory().getItem(i).getItemMeta().getPersistentDataContainer().getKeys().
                            toArray()[0].toString().matches("dodalock:bunch_of_keys"))) {
                        ItemMeta itemMeta = player.getInventory().getItem(i).getItemMeta();
                        itemMeta.setDisplayName(ChatColor.RESET + translate("items_name.bunch_of_keys"));
                        player.getInventory().getItem(i).setItemMeta(itemMeta);
                    }
                }
            }
        }
    }
}
