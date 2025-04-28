package me.deadybbb.noflight;

import org.bukkit.plugin.java.JavaPlugin;

public final class NoFlight extends JavaPlugin {

    @Override
    public void onEnable() {
        getCommand("noflight").setExecutor(new NoFlightEffectCommand());
        getServer().getPluginManager().registerEvents(new NoFlightListener(), this);
    }

    @Override
    public void onDisable() {

    }
}
