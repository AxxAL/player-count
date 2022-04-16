package net.axxal.playercount.api.requests;

import net.axxal.playercount.api.AbstractRequest;
import net.axxal.playercount.api.ApiResponse;
import org.bukkit.entity.HumanEntity;
import org.java_websocket.WebSocket;

public class GetPlayerListRequest extends AbstractRequest {

    public GetPlayerListRequest() {
        super("getPlayerList");
    }

    @Override
    protected void runAction(WebSocket connection) {
        String[] playerList = plugin.getServer().getOnlinePlayers().stream().map(HumanEntity::getName).toArray(String[]::new);
        ApiResponse response = new ApiResponse("playerList", playerList);
        connection.send(response.getBytes());
    }
}
