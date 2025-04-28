package me.deadybbb.noflight;

import org.bukkit.plugin.java.JavaPlugin;

public final class NoFlight extends JavaPlugin {

    @Override
    public void onEnable() {
        getLogger().info("NoFlight has been enabled!");
        getCommand("noflight").setExecutor(new NoFlightEffectCommand());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
