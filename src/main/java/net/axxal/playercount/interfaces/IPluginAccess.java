package net.axxal.playercount.interfaces;

import net.axxal.playercount.PlayerCount;
import org.bukkit.plugin.java.JavaPlugin;

public interface IPluginAccess {
    // Provides access to the PlayerCount plugin instance to the implementing class.
    PlayerCount plugin = JavaPlugin.getPlugin(PlayerCount.class);
}
