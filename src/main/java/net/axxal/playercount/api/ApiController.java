package net.axxal.playercount.api;

import net.axxal.playercount.interfaces.IPluginAccess;
import net.axxal.playercount.api.requests.GetMaxPlayerCountRequest;
import net.axxal.playercount.api.requests.GetPlayerCountRequest;
import net.axxal.playercount.api.requests.GetPlayerListRequest;
import org.java_websocket.WebSocket;
import org.slf4j.Logger;

public class ApiController implements IPluginAccess {

    private final Logger logger;
    private final AbstractRequest[] requestTypes;

    public ApiController() {
        this.logger = plugin.getSLF4JLogger();
        this.requestTypes = new AbstractRequest[] {
                new GetPlayerCountRequest(),
                new GetMaxPlayerCountRequest(),
                new GetPlayerListRequest()
        };
    }

    // Handles the opening of the connection.
    public void handleConnection(WebSocket conn) {
        logger.info("Received connection from {}", conn.getRemoteSocketAddress().getAddress().getHostAddress());
        ApiResponse response = new ApiResponse("connection", "Connection established");
        conn.send(response.getBytes());
    }

    // Handle the closing of the connection.
    public void handleClose(WebSocket conn, int code, boolean remote) {
        logger.info("{} closed connection from {}. Closing code [{}]",
                remote ? "Remote" : "Host",
                conn.getRemoteSocketAddress().getAddress().getHostAddress(),
                code
        );
    }

    // Handles the request from the client.
    public void handleRequest(WebSocket conn, String message) {

        // Checks if the message is a valid request and runs the action for said request.
        for (AbstractRequest request : requestTypes) {
            if (request.handle(message, conn)) return;
        }

        // Response given if the request is not recognized.
        ApiResponse response = new ApiResponse("error", "Request not recognized");
        conn.send(response.getBytes());
    }
}
