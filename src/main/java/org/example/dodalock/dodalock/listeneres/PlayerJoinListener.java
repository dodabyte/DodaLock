package org.example.dodalock.dodalock.listeneres;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {
    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        event.getPlayer().setResourcePack("http://resourcepack.host/dl/YdSfaPZn7KnZQH2TYbiTizDrI6T1l4UZ/DodaLock.zip");
    }
}
