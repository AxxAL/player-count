package net.axxal.playercount.api.requests;

import net.axxal.playercount.api.AbstractRequest;
import net.axxal.playercount.api.ApiResponse;
import org.java_websocket.WebSocket;

public class GetMaxPlayerCountRequest extends AbstractRequest {

    public GetMaxPlayerCountRequest() {
        super("getMaxPlayerCount");
        requiresAuthentication = plugin.getConfig().getBoolean("socket-api.requests.get-max-player-count.requires-authentication");
    }

    @Override
    protected void runAction(WebSocket connection) {
        int maxPlayerCount = plugin.getServer().getMaxPlayers();
        ApiResponse response = new ApiResponse("maxPlayerCount", maxPlayerCount);
        connection.send(response.getBytes());
    }
}