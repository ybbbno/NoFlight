package me.deadybbb.noflight;

import org.bukkit.plugin.java.JavaPlugin;
import me.deadybbb.noflight.config.ConfigManager;

public final class NoFlight extends JavaPlugin {
    private ConfigManager configManager;

    @Override
    public void onEnable() {
        configManager = new ConfigManager(this);
        configManager.loadConfig();
        getCommand("noflight").setExecutor(new NoFlightEffectCommand(configManager));
        getServer().getPluginManager().registerEvents(new NoFlightListener(configManager.getDamageConfigMap()), this);
    }

    @Override
    public void onDisable() {

    }
}
