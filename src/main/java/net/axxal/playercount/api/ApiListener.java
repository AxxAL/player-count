package net.axxal.playercount.api;

import net.axxal.playercount.interfaces.IPluginAccess;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;
import org.slf4j.Logger;

import java.net.InetSocketAddress;

public class ApiListener extends WebSocketServer implements IPluginAccess {

    private final Logger logger;
    private final ApiController controller;

    public ApiListener() {
        super(new InetSocketAddress(plugin.getConfig().getInt("socket-port")));
        controller = new ApiController();
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
        controller.handleRequest(conn, message);
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

