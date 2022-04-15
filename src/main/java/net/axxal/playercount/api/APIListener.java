package net.axxal.playercount.api;

import net.axxal.playercount.PlayerCount;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;
import org.slf4j.Logger;

import java.net.InetSocketAddress;

public class APIListener extends WebSocketServer {

    private final Logger logger;
    private final APIController controller;

    public APIListener(int port, PlayerCount plugin) {
        super(new InetSocketAddress(port));
        controller = new APIController(plugin);
        logger = plugin.getSLF4JLogger();
    }

    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        controller.handleConnection(conn);
    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        controller.handleClose(conn, code, remote);
    }

    @Override
    public void onMessage(WebSocket conn, String message) {
        controller.handleMessage(conn, message);
    }

    @Override
    public void onError(WebSocket conn, Exception ex) {
        ex.printStackTrace();
    }

    @Override
    public void onStart() {
        logger.info("Socket server started! Listening on localhost:{}.", getPort());
    }
}

