package net.axxal.playercount.api.requests;

import net.axxal.playercount.api.IPluginAccess;
import org.java_websocket.WebSocket;

public abstract class AbstractRequest implements IPluginAccess {

    protected final String label;

    protected AbstractRequest(String label) {
        this.label = label;
    }

    public boolean handle(String label, WebSocket conn) {
        if (!this.label.equals(label)) return false;
        runAction(conn);
        return true;
    }

    protected abstract void runAction(WebSocket connection);
}
