package net.axxal.playercount.socket;

import net.axxal.playercount.PlayerCount;
import net.axxal.playercount.PlayerManager;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

@ServerEndpoint(value = "/socket/player-count")
public class SocketAPI implements Runnable {

    private final PlayerCount plugin;
    private final PlayerManager playerManager;
    private boolean isRunning = false;
    private Thread thread;
    private static List<Session> sessions;

    public SocketAPI(PlayerCount plugin) {
        sessions = new ArrayList<>();
        this.plugin = plugin;
        playerManager = plugin.playerManager;
    }

    @OnOpen
    public void onOpen(Session session) {
        plugin.getLogger().info("Socket opened.");
        sessions.add(session);
    }

    @OnMessage
    public void onMessage(Session session, String message) {
        plugin.getLogger().info(String.format("Message from socket %s: %s", session.getId(), message));
    }

    @OnClose
    public void onClose(Session session) {
        plugin.getLogger().info(String.format("Socket with id %s closed", session.getId()));
        sessions.remove(session);
    }

    public static void broadcast(String message) {
        for (Session session : sessions) {
            ByteBuffer buffer = ByteBuffer.wrap(message.getBytes());
            session.getAsyncRemote().sendBinary(buffer);
        }
    }

    public void start() {
        if (isRunning) return;
        plugin.getLogger().info(String.format("Starting thread running socket on: 127.0.0.1:%d", plugin.getConfig().getInt("socket-port")));
        isRunning = true;
        thread = new Thread(this);
        thread.start();
    }

    public void stop() {
        plugin.getLogger().info("Stopping socket thread.");
        isRunning = false;
    }

    @Override
    public void run() {
        while (isRunning) {
            broadcast(String.valueOf(playerManager.getPlayerCount()));
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

