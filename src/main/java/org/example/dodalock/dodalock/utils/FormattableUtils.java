package org.example.dodalock.dodalock.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class FormattableUtils {
    public static Location getLocationFromString(String locationString) {
        if (locationString != null) {
            String[] partsLocation = locationString.split("_");
            return new Location(Bukkit.getWorld(partsLocation[0]),
                    Double.parseDouble(partsLocation[1]),
                    Double.parseDouble(partsLocation[2]),
                    Double.parseDouble(partsLocation[3]));
        }
        return null;
    }

    public static String getLocationString(Location location) {
        String locationString = location.getWorld().getName() + "_" +
                (int) BigDecimal.valueOf(location.getX()).setScale(3, RoundingMode.HALF_EVEN).doubleValue() + "_" +
                (int) BigDecimal.valueOf(location.getY()).setScale(3, RoundingMode.HALF_EVEN).doubleValue() + "_" +
                (int) BigDecimal.valueOf(location.getZ()).setScale(3, RoundingMode.HALF_EVEN).doubleValue();
        return locationString;
    }

    public static UUID getUuidFromString(String uuid) {
        return UUID.fromString(uuid);
    }

    public static String getUuidString(UUID uuid) {
        return uuid.toString();
    }

    public static String[] getShapeArray(String shape) {
        if (shape.replace(" ", "").contains(","))
            return shape.replace(" ", "").split(",");
        return null;
    }

    public static Map<Character, String> getShapeMaterialsMap(String shapeMaterials) {
        Map<Character, String> shapeMaterialsMap = new HashMap<>();
        String[] strings = shapeMaterials.replace(" ", "").split(",");
        for (String string : strings) {
            if (string.replace(" ", "").matches(".:[a-zA-Z_]+")) {
                char symbol = string.replace(" ", "").split(":")[0].charAt(0);
                String material = string.replace(" ", "").split(":")[1];
                shapeMaterialsMap.put(symbol, material);
            }
        }
        return shapeMaterialsMap;
    }
}
