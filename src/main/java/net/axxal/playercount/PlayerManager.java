package net.axxal.playercount;

import org.bukkit.entity.Player;

import java.util.ArrayList;

public class PlayerManager {

    private final PlayerCount plugin;
    private static PlayerManager instance;
    private final ArrayList<Player> players;

    private PlayerManager(PlayerCount plugin) {
        this.plugin = plugin;
        players = new ArrayList<Player>();
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void updatePlayers() {
        players.clear();
        players.addAll(plugin.getServer().getOnlinePlayers());
    }

    public int getPlayerCount() {
        return players.size();
    }

    public int maxPlayerCount() {
        return plugin.getServer().getMaxPlayers();
    }

    public static PlayerManager getInstance(PlayerCount plugin) {
        if (instance == null) {
            instance = new PlayerManager(plugin);
        }
        return instance;
    }

}
