package org.example.dodalock.dodalock.tasks;

import org.bukkit.scheduler.BukkitRunnable;
import org.example.dodalock.dodalock.utils.config.Configurations;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class ClearBunchKeysInventory extends BukkitRunnable {
    private final int period;

    public ClearBunchKeysInventory(int period) {
        this.period = period;
    }

    @Override
    public void run() {
        for (String bunchKeys : Configurations.getInventory().getBunchKeysData()) {
            if (Configurations.getInventory().getLastDateSerialize(bunchKeys) != null &&
                    (LocalDateTime.now().minus(period, ChronoUnit.HOURS).isAfter(
                    Configurations.getInventory().getLastDateSerialize(bunchKeys)) ||
                    LocalDateTime.now().minus(period, ChronoUnit.HOURS).isEqual(
                    Configurations.getInventory().getLastDateSerialize(bunchKeys)))) {
                Configurations.getInventory().removeBunchOfKeys(bunchKeys);
                Configurations.getInventory().save();
                Configurations.getInventory().reload();
            }
        }
    }
}
