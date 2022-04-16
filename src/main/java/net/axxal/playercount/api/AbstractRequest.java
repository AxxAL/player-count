package net.axxal.playercount.api;

import static net.axxal.playercount.api.ApiAuthenticator.isAuthorized;

import net.axxal.playercount.interfaces.IPluginAccess;
import org.java_websocket.WebSocket;

public abstract class AbstractRequest implements IPluginAccess {

    protected final String label;
    protected boolean requiresAuthentication;

    protected AbstractRequest(String label) {
        this.label = label;
    }

    public boolean handle(String label, WebSocket conn) {
        if (!this.label.equals(label)) return false;

        // Check if the request originates from an allowed remote.
        if (requiresAuthentication && !isAuthorized(conn.getRemoteSocketAddress().getAddress().getHostAddress())) {
            unauthorizedRequest(conn);
            return true;
        }

        runAction(conn);
        return true;
    }

    private void unauthorizedRequest(WebSocket connection) {
        ApiResponse response = new ApiResponse("error", "Unauthorized request");
        connection.send(response.getBytes());
    }

    protected abstract void runAction(WebSocket connection);
}
