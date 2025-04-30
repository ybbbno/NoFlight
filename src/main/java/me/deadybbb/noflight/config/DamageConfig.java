package me.deadybbb.noflight.config;

import org.bukkit.entity.EntityType;

import java.util.Set;

public class DamageConfig {
    public enum Mode {
        INCLUDE,
        EXCLUDE,
    }

    private final Mode mode;
    private final Set<EntityType> entities;
    private final int duration;

    public DamageConfig(Mode mode, Set<EntityType> entities, int duration) {
        this.mode = mode;
        this.entities = entities;
        this.duration = duration;
    }

    public Mode getMode() {
        return mode;
    }

    public Set<EntityType> getEntities() {
        return entities;
    }

    public int getDuration() {
        return duration;
    }
}