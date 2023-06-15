package org.example.dodalock.dodalock.listeneres;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.example.dodalock.dodalock.utils.config.Configurations;

public class PlayerJoinListener implements Listener {
    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Configurations.getLanguage().checkItemsLocalization(event.getPlayer());
        if (Configurations.getConfig().isEnableResourcePack())
            event.getPlayer().setResourcePack(Configurations.getConfig().getResourcePackUrl());
    }
}
