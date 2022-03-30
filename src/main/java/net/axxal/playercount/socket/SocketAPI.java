package net.axxal.playercount.socket;

import net.axxal.playercount.PlayerCount;
import net.axxal.playercount.PlayerManager;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;
import org.slf4j.Logger;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;

public class SocketAPI extends WebSocketServer {

    private final PlayerCount plugin;
    private final PlayerManager playerManager;
    private final Logger logger;

    public SocketAPI(int port, PlayerCount plugin) {
        super(new InetSocketAddress(port));
        this.plugin = plugin;
        logger = plugin.getSLF4JLogger();
        playerManager = PlayerManager.getInstance(plugin);
    }

    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        conn.send("Connected!");
        logger.info(String.format("%s connected to the socket!", conn.getRemoteSocketAddress().getAddress().getHostAddress()));
    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        logger.info(String.format("%s disconnected from the socket!", conn.getRemoteSocketAddress().getAddress().getHostAddress()));
    }

    @Override
    public void onMessage(WebSocket conn, String message) {
        switch (message) {
            case "ping":
                conn.send("pong");
            case "getPlayerCount":
                ByteBuffer playerCountBuffer = ByteBuffer.allocate(4);
                playerCountBuffer.putInt(playerManager.getPlayerCount());
                conn.send(playerCountBuffer.array());
                break;
            case "getMaxPlayers":
                ByteBuffer maxPlayerBuffer = ByteBuffer.allocate(4);
                maxPlayerBuffer.putInt(playerManager.maxPlayerCount());
                conn.send(maxPlayerBuffer.array());
                break;
            default:
                conn.send("unknown action");
                break;
        }
    }

    @Override
    public void onError(WebSocket conn, Exception ex) {
        ex.printStackTrace();
    }

    @Override
    public void onStart() {
        logger.info(String.format("Socket server started! Listening on port %s", getPort()));
    }
}

