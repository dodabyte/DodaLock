package org.example.dodalock.dodalock.logic;

import org.bukkit.entity.Player;

import java.util.*;

public class CheckPlayersAttempts {
    private final static Map<String, Map<UUID, Integer>> playerAttemptsMap = new HashMap<>();

    public static void addAttempt(String location, Player player) {
        int attempt = 0;
        if (!playerAttemptsMap.containsKey(location)) {
            playerAttemptsMap.put(location, new HashMap<>());
        }
        if (playerAttemptsMap.get(location).containsKey(player.getUniqueId())) {
            attempt = playerAttemptsMap.get(location).get(player.getUniqueId()) + 1;
        } else {
            attempt++;
        }
        playerAttemptsMap.get(location).put(player.getUniqueId(), attempt);
    }

    public static void removeAttempts(String location, Player player) {
        playerAttemptsMap.get(location).remove(player.getUniqueId());
    }

    public static void removeLocation(String location) {
        if (playerAttemptsMap.get(location).isEmpty()) {
            playerAttemptsMap.remove(location);
        }
    }

    public static int getAttempts(String location, Player player) {
        return playerAttemptsMap.get(location).get(player.getUniqueId());
    }
}
