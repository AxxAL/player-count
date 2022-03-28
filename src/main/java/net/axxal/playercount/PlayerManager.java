package net.axxal.playercount;

import org.bukkit.entity.Player;

import java.util.ArrayList;

public class PlayerManager {

    private static PlayerManager instance;
    private ArrayList<Player> players;

    private PlayerManager() {
        players = new ArrayList<Player>();
    }

    public Player addPlayer(Player player) {
        if (!players.contains(player)) {
            players.add(player);
        }
        return player;
    }

    public Player removePlayer(Player player) {
        players.remove(player);
        return player;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public int getPlayerCount() {
        return players.size();
    }

    public static PlayerManager getInstance() {
        if (instance == null) {
            instance = new PlayerManager();
        }
        return instance;
    }

}
