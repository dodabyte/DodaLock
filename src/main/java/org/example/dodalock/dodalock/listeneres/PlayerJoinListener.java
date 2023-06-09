package org.example.dodalock.dodalock.listeneres;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.example.dodalock.dodalock.utils.config.Configurations;

public class PlayerJoinListener implements Listener {
    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Configurations.getLanguage().checkItemsLocalization(event.getPlayer());
        event.getPlayer().setResourcePack("http://resourcepack.host/dl/gQjtp9E0GON57x4LkJ3NNke0zsWL954g/DodaLock.zip");
    }
}
