package org.example.dodalock.dodalock.utils;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.block.data.Bisected;
import org.bukkit.block.data.type.Door;
import org.bukkit.inventory.DoubleChestInventory;


public class WorldUtils {
    public static boolean isTrueTypes(Block block) {
        return isDoor(block) || isTrapdoor(block) || isChest(block) || isDoubleChest(block) || isBarrel(block);
    }
    
    public static boolean isDoor(Block block) {
        return block.getType().toString().contains("DOOR") && !block.getType().toString().contains("IRON") &&
                !block.getType().toString().contains("TRAPDOOR");
    }

    public static boolean isTrapdoor(Block block) {
        return block.getType().toString().contains("TRAPDOOR") && !block.getType().toString().contains("IRON");
    }

    public static boolean isChest(Block block) {
        return block.getType().toString().contains("CHEST") && !block.getType().toString().contains("ENDER");
    }

    public static boolean isDoubleChest(Block block) {
        return isChest(block) &&
                ((Chest) block.getState()).getInventory() instanceof DoubleChestInventory;
    }

    public static boolean isBarrel(Block block) {
        return block.getType().toString().contains("BARREL");
    }

    public static Location getLocation(Block block) {
        if (block != null) {
            if (WorldUtils.isDoor(block)) {
                // Ведущим блоком у двери является её нижняя часть (нижний блок)
                Door door = (Door) block.getBlockData();
                if (door.getHalf().equals(Bisected.Half.BOTTOM))
                    return block.getLocation();
                else return block.getRelative(0, -1, 0).getLocation();
            }
            else if (WorldUtils.isDoubleChest(block)) {
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
