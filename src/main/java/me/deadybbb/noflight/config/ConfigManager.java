package me.deadybbb.noflight.config;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ConfigManager {
    private final JavaPlugin plugin;
    private FileConfiguration config;
    private Map<DamageCause, DamageConfig> damageConfigMap;

    // Default duration in seconds
    private static final int DEFAULT_DURATION = 10;

    public ConfigManager(JavaPlugin plugin) {
        this.plugin = plugin;
        this.damageConfigMap = new HashMap<>();
    }

    public void loadConfig() {
        // Ensure config file exists
        File configFile = new File(plugin.getDataFolder(), "config.yml");
        if (!configFile.exists()) {
            try {
                copyResourceToFile("config.yml", configFile);
                plugin.getLogger().info("Copied default config.yml to " + configFile.getPath());
            } catch (Exception e) {
                plugin.getLogger().warning("Failed to copy config.yml from resources: " + e.getMessage() + ".");
            }
        }
        config = YamlConfiguration.loadConfiguration(configFile);

        // Clear existing configurations
        damageConfigMap.clear();

        List<?> damageTypesList = config.getList("allowed-damage-types");
        if (damageTypesList == null) {
            plugin.getLogger().warning("No allowed-damage-types specified in config.");
            return;
        }

        for (Object item : damageTypesList) {
            if (item instanceof String damageTypeKey) {
                // Handle simple string entries
                try {
                    DamageCause damageCause = DamageCause.valueOf(damageTypeKey.toUpperCase());
                    damageConfigMap.put(damageCause, new DamageConfig(DamageConfig.Mode.INCLUDE, new HashSet<>(), DEFAULT_DURATION));
                } catch (IllegalArgumentException e) {
                    plugin.getLogger().warning("Invalid damage type in config: " + damageTypeKey);
                }
            } else if (item instanceof Map) {
                // Handle Map entries (e.g., ENTITY_ATTACK: {entities: [PLAYER]})
                @SuppressWarnings("unchecked")
                Map<String, Object> map = (Map<String, Object>) item;
                for (Map.Entry<String, Object> entry : map.entrySet()) {
                    String damageTypeKey = entry.getKey();
                    Object value = entry.getValue();
                    try {
                        DamageCause damageCause = DamageCause.valueOf(damageTypeKey.toUpperCase());
                        if (value instanceof Map) {
                            // Convert Map to ConfigurationSection-like structure
                            @SuppressWarnings("unchecked")
                            Map<String, Object> configMap = (Map<String, Object>) value;
                            YamlConfiguration tempConfig = new YamlConfiguration();
                            for (Map.Entry<String, Object> configEntry : configMap.entrySet()) {
                                tempConfig.set(configEntry.getKey(), configEntry.getValue());
                            }

                            int duration = tempConfig.getInt("duration", DEFAULT_DURATION);
                            if (duration < 0) {
                                plugin.getLogger().warning("Invalid duration for " + damageTypeKey + ": " + duration + ". Using default: " + DEFAULT_DURATION);
                                duration = DEFAULT_DURATION;
                            }

                            String modeStr = tempConfig.getString("mode", "include").toUpperCase();
                            DamageConfig.Mode mode;
                            try {
                                mode = DamageConfig.Mode.valueOf(modeStr);
                            } catch (IllegalArgumentException e) {
                                plugin.getLogger().warning("Invalid mode for " + damageTypeKey + ": " + modeStr + ". Using INCLUDE.");
                                mode = DamageConfig.Mode.INCLUDE;
                            }

                            List<String> entityStrings = tempConfig.getStringList("entities");
                            Set<EntityType> allowedEntities = new HashSet<>();

                            for (String entity : entityStrings) {
                                try {
                                    EntityType entityType = EntityType.valueOf(entity.toUpperCase());
                                    allowedEntities.add(entityType);
                                } catch (IllegalArgumentException e) {
                                    plugin.getLogger().warning("Invalid entity type for " + damageTypeKey + ": " + entity);
                                }
                            }

                            damageConfigMap.put(damageCause, new DamageConfig(mode, allowedEntities, duration));
                        } else {
                            plugin.getLogger().warning("Invalid configuration for " + damageTypeKey + ": Expected a configuration map, got " + value);
                        }
                    } catch (IllegalArgumentException e) {
                        plugin.getLogger().warning("Invalid damage type in config: " + damageTypeKey);
                    }
                }
            } else if (item instanceof ConfigurationSection section) {
                // Handle ConfigurationSection entries
                for (String damageTypeKey : section.getKeys(false)) {
                    try {
                        DamageCause damageCause = DamageCause.valueOf(damageTypeKey.toUpperCase());
                        int duration = section.getInt(damageTypeKey + ".duration", DEFAULT_DURATION);
                        if (duration < 0) {
                            plugin.getLogger().warning("Invalid duration for " + damageTypeKey + ": " + duration + ". Using default: " + DEFAULT_DURATION);
                            duration = DEFAULT_DURATION;
                        }

                        String modeStr = section.getString(damageTypeKey + ".mode", "include").toUpperCase();
                        DamageConfig.Mode mode;
                        try {
                            mode = DamageConfig.Mode.valueOf(modeStr);
                        } catch (IllegalArgumentException e) {
                            plugin.getLogger().warning("Invalid mode for " + damageTypeKey + ": " + modeStr + ". Using INCLUDE.");
                            mode = DamageConfig.Mode.INCLUDE;
                        }

                        List<String> entityStrings = section.getStringList(damageTypeKey + ".entities");
                        Set<EntityType> allowedEntities = new HashSet<>();

                        for (String entity : entityStrings) {
                            try {
                                EntityType entityType = EntityType.valueOf(entity.toUpperCase());
                                allowedEntities.add(entityType);
                            } catch (IllegalArgumentException e) {
                                plugin.getLogger().warning("Invalid entity type for " + damageTypeKey + ": " + entity);
                            }
                        }

                        damageConfigMap.put(damageCause, new DamageConfig(mode, allowedEntities, duration));
                    } catch (IllegalArgumentException e) {
                        plugin.getLogger().warning("Invalid damage type in config: " + damageTypeKey);
                    }
                }
            } else {
                plugin.getLogger().warning("Invalid config entry: " + item + ". Expected a damage type string (e.g., LAVA), map (e.g., {LAVA: {duration: 10}}), or configuration section.");
            }
        }

        if (damageConfigMap.isEmpty()) {
            plugin.getLogger().warning("No valid damage types specified in config.");
        }

        plugin.getLogger().info("Loaded " + damageConfigMap.size() + " damage types");
    }

    private void copyResourceToFile(String resourcePath, File destination) throws Exception {
        if (!destination.getParentFile().exists()) {
            destination.getParentFile().mkdirs();
        }
        try (InputStream resourceStream = plugin.getResource(resourcePath)) {
            if (resourceStream == null) {
                throw new IllegalArgumentException("Resource " + resourcePath + " not found in plugin JAR");
            }
            Files.copy(resourceStream, destination.toPath(), StandardCopyOption.REPLACE_EXISTING);
        }
    }

    public Map<DamageCause, DamageConfig> getDamageConfigMap() {
        return Collections.unmodifiableMap(damageConfigMap);
    }
}