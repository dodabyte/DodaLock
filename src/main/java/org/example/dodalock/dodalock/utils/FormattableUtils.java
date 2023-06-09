package org.example.dodalock.dodalock.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
}
