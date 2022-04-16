package net.axxal.playercount;

import net.axxal.playercount.api.ApiListener;
import org.bukkit.plugin.java.JavaPlugin;

public final class PlayerCount extends JavaPlugin {

    private Thread listenerThread;

    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        ApiListener apiListener = new ApiListener();
        listenerThread = new Thread(apiListener);
        listenerThread.start();
    }

    @Override
    public void onDisable() {
        listenerThread.interrupt();
        try {
            listenerThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
