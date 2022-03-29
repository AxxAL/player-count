package net.axxal.playercount.socket;

import net.axxal.playercount.PlayerCount;
import net.axxal.playercount.PlayerManager;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.util.Timer;
import java.util.TimerTask;

public class SocketAPI extends WebSocketServer {

    private final PlayerCount plugin;
    private final PlayerManager playerManager;

    public SocketAPI(int port, PlayerCount plugin) {
        super(new InetSocketAddress(port));
        this.plugin = plugin;
        playerManager = PlayerManager.getInstance(plugin);
    }

    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        conn.send("Connected!");
        plugin.getSLF4JLogger().info(String.format("%s connected to the socket!", conn.getRemoteSocketAddress().getAddress().getHostAddress()));
    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        plugin.getSLF4JLogger().info(String.format("%s disconnected from the socket!", conn.getRemoteSocketAddress().getAddress().getHostAddress()));
    }

    @Override
    public void onMessage(WebSocket conn, String message) {
        switch (message) {
            case "ping":
                conn.send("pong");
            case "getPlayerCount":
                conn.send(String.valueOf(playerManager.getPlayerCount()));
                break;
            case "getMaxPlayers":
                conn.send(String.valueOf(playerManager.maxPlayerCount()));
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
        plugin.getSLF4JLogger().info(String.format("Socket server started! Listening on port %s", getPort()));
    }

    public void run() {
        start();

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                plugin.getSLF4JLogger().info("Broadcasting player count to all connected clients!");
                playerManager.updatePlayers();
                ByteBuffer buffer = ByteBuffer.allocate(4);
                buffer.putInt(playerManager.getPlayerCount());
                broadcast(ByteBuffer.wrap(buffer.array()));
            }
        }, 0, 15 * 1000);
    }
}

