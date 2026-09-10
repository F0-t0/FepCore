package pl.fepbox.core;

import org.bukkit.plugin.java.JavaPlugin;

import pl.fepbox.core.listeners.menuClick;

public class FepCore extends JavaPlugin {
    private static FepCore plugin;

    public static FepCore getPlugin() {
        return plugin;
    }
    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new menuClick(), this);
    }

    @Override
    public void onDisable() {
    }
    
}
