package org.example.dodalock.dodalock.tasks;

import org.bukkit.scheduler.BukkitRunnable;
import org.example.dodalock.dodalock.utils.config.Configurations;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class ClearBunchKeysInventory extends BukkitRunnable {
    @Override
    public void run() {
        if (Configurations.getConfig().getAllowClearBunchKeysInventory()) {
            for (String bunchKeys : Configurations.getConfig().getBunchKeys()) {
                if (LocalDateTime.now().minus(3, ChronoUnit.HOURS).isAfter(
                        Configurations.getConfig().getLastDateSerialize(bunchKeys)) ||
                        LocalDateTime.now().minus(3, ChronoUnit.HOURS).isEqual(
                        Configurations.getConfig().getLastDateSerialize(bunchKeys))) {
                    Configurations.getConfig().removeBunchOfKeys(bunchKeys);
                }
            }
        }
    }
}
