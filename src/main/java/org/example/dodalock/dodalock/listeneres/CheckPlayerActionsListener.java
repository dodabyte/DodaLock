package org.example.dodalock.dodalock.listeneres;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.FurnaceBurnEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.example.dodalock.dodalock.items.ItemsManager;
import org.example.dodalock.dodalock.utils.FormattableUtils;
import org.example.dodalock.dodalock.utils.WorldUtils;
import org.example.dodalock.dodalock.utils.config.Configurations;

import java.util.List;

public class CheckPlayerActionsListener implements Listener {
    @EventHandler
    public void onPlayerClicked(PlayerInteractEvent event) {
        if (event.getHand() == EquipmentSlot.HAND) {
            Block clicked = event.getClickedBlock();
            Player player = event.getPlayer();
            Location location = WorldUtils.getLocation(clicked);

            if (clicked == null) return;

            // Отмена любых действий с ключом и связкой ключей, если клик на любой другой блок, кроме дверей и т.д.
            if (location == null && (clicked.getType() == Material.DIRT || clicked.getType() == Material.DIRT_PATH ||
                    clicked.getType() == Material.GRASS_BLOCK || clicked.getType() == Material.ROOTED_DIRT ||
                    clicked.getType() == Material.COARSE_DIRT)) {
                if (ItemsManager.isKey(player.getEquipment().getItemInMainHand()) ||
                        ItemsManager.isMasterKey(player.getEquipment().getItemInMainHand()) ||
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
    public void onPlayerCrafted(CraftItemEvent event) {
        if (!event.isCancelled()) {
            if (event.getInventory().contains(Material.WOODEN_HOE)) {
                for (ItemStack itemStack : event.getInventory().getMatrix()) {
                    if (ItemsManager.isKey(itemStack) || ItemsManager.isMasterKey(itemStack) ||
                            (itemStack != null && itemStack.getItemMeta() != null &&
                            itemStack.getItemMeta().getPersistentDataContainer().getKeys().size() == 1 &&
                            (itemStack.getItemMeta().getPersistentDataContainer().getKeys().
                            toArray()[0].toString().matches("[d][o][d][a][l][o][c][k][:][a-z]+([_][0-9]+){3}") ||
                            itemStack.getItemMeta().getPersistentDataContainer().getKeys().
                            toArray()[0].toString().contains("dodalock:bunch_of_keys")))) {
                        event.setCancelled(true);
                    }
                }
            }
        }
    }

    @EventHandler
    public void onPlayerEnchant(EnchantItemEvent event) {
        if (!event.isCancelled()) {
            if (event.getItem().getType() == Material.WOODEN_HOE) {
                ItemStack itemStack = event.getItem();
                if (ItemsManager.isKey(itemStack) || ItemsManager.isMasterKey(itemStack) ||
                        (itemStack.getItemMeta() != null &&
                        itemStack.getItemMeta().getPersistentDataContainer().getKeys().size() == 1 &&
                        (itemStack.getItemMeta().getPersistentDataContainer().getKeys().
                        toArray()[0].toString().matches("[d][o][d][a][l][o][c][k][:][a-z]+([_][0-9]+){3}") ||
                        itemStack.getItemMeta().getPersistentDataContainer().getKeys().
                        toArray()[0].toString().contains("dodalock:bunch_of_keys")))) {
                    event.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onPlayerBurn(FurnaceBurnEvent event) {
        if (!event.isCancelled()) {
            if (event.getFuel().getType() == Material.WOODEN_HOE) {
                ItemStack itemStack = event.getFuel();
                if (ItemsManager.isKey(itemStack) || ItemsManager.isMasterKey(itemStack) ||
                        (itemStack.getItemMeta() != null &&
                        itemStack.getItemMeta().getPersistentDataContainer().getKeys().size() == 1 &&
                        (itemStack.getItemMeta().getPersistentDataContainer().getKeys().
                        toArray()[0].toString().matches("[d][o][d][a][l][o][c][k][:][a-z]+([_][0-9]+){3}") ||
                        itemStack.getItemMeta().getPersistentDataContainer().getKeys().
                        toArray()[0].toString().contains("dodalock:bunch_of_keys")))) {
                    event.setCancelled(true);
                }
            }
        }
    }
}