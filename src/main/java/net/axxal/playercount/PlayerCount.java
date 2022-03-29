package net.axxal.playercount;

import net.axxal.playercount.socket.SocketAPI;
import org.bukkit.plugin.java.JavaPlugin;

public final class PlayerCount extends JavaPlugin {

    public SocketAPI socketAPI;
    public PlayerManager playerManager;

    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        playerManager = PlayerManager.getInstance(this);
        this.getServer().getPluginManager().registerEvents(new EventListener(this), this);
        socketAPI = new SocketAPI(getConfig().getInt("socket-port"), this);
        socketAPI.run();
    }

    @Override
    public void onDisable() {

    }
}
