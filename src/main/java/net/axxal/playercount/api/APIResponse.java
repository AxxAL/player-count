package net.axxal.playercount.api;

import org.json.JSONObject;

import java.time.Instant;

public class APIResponse {

    private final JSONObject json;

    public APIResponse(String type, Object data) {
        json = new JSONObject();
        json.put("type", type);
        json.put("data", data);
        json.put("timestamp", Instant.now().toString());
    }

    public byte[] getBytes() {
        return json.toString().getBytes();
    }
}
