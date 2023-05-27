package org.example.dodalock.dodalock;

import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;
import org.example.dodalock.dodalock.gui.GuiItemsManager;
import org.example.dodalock.dodalock.items.ItemsManager;
import org.example.dodalock.dodalock.listeneres.BunchKeysListener;
import org.example.dodalock.dodalock.listeneres.MenuListener;
import org.example.dodalock.dodalock.listeneres.LocksListener;
import org.example.dodalock.dodalock.listeneres.PlayerJoinListener;
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
    }

    @Override
    public void onDisable() {
        checkLocks();
        Configurations.save();
    }

    private void registerEvents() {
        this.getServer().getPluginManager().registerEvents(new MenuListener(), this);
        this.getServer().getPluginManager().registerEvents(new LocksListener(), this);
        this.getServer().getPluginManager().registerEvents(new BunchKeysListener(), this);
        this.getServer().getPluginManager().registerEvents(new PlayerJoinListener(), this);
    }

    private void registerCommands() {

    }

    private static void checkLocks() {
        // TODO Разобраться с ошибкой
//        List<String> codeLocksList = Configurations.getConfig().getCodeLockData();
//        for (String codeLock : codeLocksList) {
//            Location location = FormattableUtils.getLocationFromString(codeLock);
//            if (!WorldUtils.isTrueTypes(location.getBlock())) {
//                Configurations.getConfig().removeCodeLockFromList(codeLock);
//                Configurations.getConfig().removeCodeLock(codeLock);
//            }
//        }
//
//        List<String> locksList = Configurations.getConfig().getLockData();
//        for (String lock : locksList) {
//            Location location = FormattableUtils.getLocationFromString(lock);
//            if (!WorldUtils.isTrueTypes(location.getBlock())) {
//                Configurations.getConfig().removeLockFromList(lock);
//                Configurations.getConfig().removeLock(lock);
//            }
//        }
    }

    public static DodaLockMain getPlugin() { return PLUGIN; }
}
