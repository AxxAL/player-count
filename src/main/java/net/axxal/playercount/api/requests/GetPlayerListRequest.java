package net.axxal.playercount.api.requests;

import net.axxal.playercount.api.AbstractRequest;
import net.axxal.playercount.api.ApiResponse;
import org.bukkit.entity.HumanEntity;
import org.java_websocket.WebSocket;

public class GetPlayerListRequest extends AbstractRequest {

    public GetPlayerListRequest() {
        super("getPlayerList");
        requiresAuthentication = plugin.getConfig().getBoolean("socket-api.requests.get-player-list.requires-authentication");
    }

    @Override
    protected void runAction(WebSocket connection) {
        String[] playerList = plugin.getServer().getOnlinePlayers().stream().map(HumanEntity::getName).toArray(String[]::new);
        ApiResponse response = new ApiResponse("playerList", playerList);
        connection.send(response.getBytes());
    }
}