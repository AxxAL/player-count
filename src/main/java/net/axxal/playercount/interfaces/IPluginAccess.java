package net.axxal.playercount.api;

import net.axxal.playercount.PlayerCount;
import org.bukkit.plugin.java.JavaPlugin;

public interface IPluginAccess {
    PlayerCount plugin = JavaPlugin.getPlugin(PlayerCount.class);
}
