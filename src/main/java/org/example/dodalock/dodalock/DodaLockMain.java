package org.example.dodalock.dodalock;

import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;
import org.example.dodalock.dodalock.commands.GlobalCommands;
import org.example.dodalock.dodalock.gui.GuiItemsManager;
import org.example.dodalock.dodalock.items.ItemsManager;
import org.example.dodalock.dodalock.listeneres.*;
import org.example.dodalock.dodalock.tasks.ClearBunchKeysInventory;
import org.example.dodalock.dodalock.utils.FormattableUtils;
import org.example.dodalock.dodalock.utils.WorldUtils;
import org.example.dodalock.dodalock.utils.config.Configurations;

import java.util.List;

public final class DodaLockMain extends JavaPlugin {
    private static DodaLockMain PLUGIN;

    @Override
    public void onEnable() {
        PLUGIN = this;

        getConfig().options().copyDefaults();
        saveDefaultConfig();
        Configurations.setup();

        registerEvents();
        registerCommands();
        ItemsManager.initializeItems();
        GuiItemsManager.initializeGuiItems();

        checkLocks();
        checkBunchKeys();
    }

    @Override
    public void onDisable() {
        Configurations.save();
    }

    private void registerEvents() {
        this.getServer().getPluginManager().registerEvents(new MenuListener(), this);
        this.getServer().getPluginManager().registerEvents(new LocksListener(), this);
        this.getServer().getPluginManager().registerEvents(new BunchKeysListener(), this);
        this.getServer().getPluginManager().registerEvents(new PlayerJoinListener(), this);
        this.getServer().getPluginManager().registerEvents(new CheckPlayerActionsListener(), this);
    }

    private void registerCommands() {
        new GlobalCommands(this);
    }

    private void checkLocks() {
        try {
            List<String> codeLocksList = Configurations.getLocks().getCodeLockData();
            for (String codeLock : codeLocksList) {
                if (codeLock != null && !codeLock.equals("")) {
                    Location location = FormattableUtils.getLocationFromString(codeLock);
                    if (!WorldUtils.isTrueTypes(location.getBlock())) {
                        Configurations.getLocks().removeCodeLock(codeLock);
                    }
                }
            }

            List<String> locksList = Configurations.getLocks().getLockData();
            for (String lock : locksList) {
                if (lock != null && !lock.equals("")) {
                    Location location = FormattableUtils.getLocationFromString(lock);
                    if (!WorldUtils.isTrueTypes(location.getBlock())) {
                        Configurations.getLocks().removeLock(lock);
                    }
                }
            }
        }
        catch (Exception ignored) {}
    }

    public void checkBunchKeys() {
        if (Configurations.getConfig().getAllowClearBunchKeysInventory()) {
            int period = Configurations.getConfig().getVerificationPeriod();
            if (period <= 0) period = 3;
            new ClearBunchKeysInventory(period).runTaskTimer(getPlugin(), 0L, period * 60 * 60 * 20L);
        }
    }

    public static DodaLockMain getPlugin() { return PLUGIN; }
}
