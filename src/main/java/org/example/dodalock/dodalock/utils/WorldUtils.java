package org.example.dodalock.dodalock.utils;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.inventory.DoubleChestInventory;


public class WorldUtils {
    public static boolean isTrueTypes(Block block) {
        return isDoor(block) || isTrapdoor(block) || isChest(block) || isDoubleChest(block) || isBarrel(block);
    }
    
    public static boolean isDoor(Block block) {
        return block.getType().toString().contains("DOOR") && !block.getType().toString().contains("IRON");
    }

    public static boolean isTrapdoor(Block block) {
        return block.getType().toString().contains("TRAPDOOR") && !block.getType().toString().contains("IRON");
    }

    public static boolean isChest(Block block) {
        return block.getType().toString().contains("CHEST") && !block.getType().toString().contains("ENDER");
    }

    public static boolean isDoubleChest(Block block) {
        System.out.println(isChest(block) &&
                ((Chest) block.getState()).getInventory() instanceof DoubleChestInventory);

        return isChest(block) &&
                ((Chest) block.getState()).getInventory() instanceof DoubleChestInventory;
    }

    public static boolean isBarrel(Block block) {
        return block.getType().toString().contains("BARREL");
    }
}
