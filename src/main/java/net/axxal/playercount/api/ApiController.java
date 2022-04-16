package net.axxal.playercount.api;

import net.axxal.playercount.PlayerCount;
import org.bukkit.entity.HumanEntity;
import org.java_websocket.WebSocket;
import org.slf4j.Logger;

public class APIController {

    private final PlayerCount plugin;
    private final Logger logger;

    public APIController(PlayerCount plugin) {
        this.plugin = plugin;
        this.logger = plugin.getSLF4JLogger();
    }

    // Handles the opening of the connection.
    public void handleConnection(WebSocket conn) {
        logger.info("Received connection from {}", conn.getRemoteSocketAddress().getAddress().getHostAddress());
        APIResponse response = new APIResponse("connection", "Connection established");
        conn.send(response.getBytes());
    }

    // Handle the closing of the connection.
    public void handleClose(WebSocket conn, int code, boolean remote) {
        if (remote) {
            logger.info("Remote {} closed connection to the socket. [{}]", conn.getRemoteSocketAddress().getAddress().getHostAddress(), code);
            return;
        }
        logger.info("Host closed connection from {}. [{}]", conn.getRemoteSocketAddress().getAddress().getHostAddress(), code);
    }

    // Handles the request from the client.
    public void handleMessage(WebSocket conn, String message) {
        // Responds with player count.
        if (message.equals("getPlayerCount")) {
            APIResponse response = new APIResponse("playerCount", plugin.getServer().getOnlinePlayers().size());
            conn.send(response.getBytes());
            return;
        }

        // Responds with max player count.
        if (message.equals("getMaxPlayerCount")) {
            APIResponse response = new APIResponse("maxPlayerCount", plugin.getServer().getMaxPlayers());
            conn.send(response.getBytes());
            return;
        }

        // Responds with a list of the server's online players' names.
        if (message.equals("getPlayerList")) {
            APIResponse response = new APIResponse("playerList", plugin.getServer().getOnlinePlayers().stream().map(HumanEntity::getName).toArray(String[]::new));
            conn.send(response.getBytes());
            return;
        }

        // Response given if the request is not recognized.
        APIResponse response = new APIResponse("error", "Request not recognized");
        conn.send(response.getBytes());
    }
}
