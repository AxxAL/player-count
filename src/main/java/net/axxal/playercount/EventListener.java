package net.axxal.playercount;

import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class EventListener implements Listener {

    private final PlayerManager playerManager;

    public EventListener(PlayerCount plugin) {
        playerManager = PlayerManager.getInstance(plugin);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        playerManager.updatePlayers();
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        playerManager.updatePlayers();
    }

}
