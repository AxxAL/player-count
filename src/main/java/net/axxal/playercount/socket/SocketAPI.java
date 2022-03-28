package net.axxal.playercount.socket;

import net.axxal.playercount.PlayerCount;
import net.axxal.playercount.PlayerManager;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.util.ArrayList;
import java.util.List;

@ServerEndpoint(value = "/player-count")
public class SocketAPI implements Runnable {

    private final PlayerManager playerManager;
    private boolean isRunning = false;
    private static List<Session> sessions;

    public SocketAPI(PlayerCount plugin) {
        sessions = new ArrayList<Session>();
        playerManager = plugin.playerManager;
    }

    @OnOpen
    public void onOpen(Session session) {
        System.out.println("Socket opened.");
        sessions.add(session);
    }

    @OnMessage
    public void onMessage(Session session, String message) {
        System.out.printf("Message from socket %s: %s", session.getId(), message);
    }

    @OnClose
    public void onClose(Session session) {
        System.out.printf("Socket closed: id(%s)", session.getId());
        sessions.remove(session);
    }

    public static void broadcast(String message) {
        for (Session session : sessions) {
            session.getAsyncRemote().sendText(message);
        }
    }

    public void start() {
        isRunning = true;
        new Thread(this).start();
    }

    public void stop() {
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

