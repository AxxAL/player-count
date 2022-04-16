package net.axxal.playercount.api.requests;

import net.axxal.playercount.api.AbstractRequest;
import net.axxal.playercount.api.ApiResponse;
import org.java_websocket.WebSocket;

public class GetPlayerCountRequest extends AbstractRequest {

    public GetPlayerCountRequest() {
        super("getPlayerCount");
    }

    @Override
    protected void runAction(WebSocket connection) {
        int playerCount = plugin.getServer().getOnlinePlayers().size();
        ApiResponse response = new ApiResponse("playerCount", playerCount);
        connection.send(response.getBytes());
    }
}
