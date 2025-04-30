package me.deadybbb.noflight;

import me.deadybbb.noflight.config.DamageConfig;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import java.util.Map;
import java.util.Set;

public class NoFlightListener implements Listener {
    private final Map<DamageCause, DamageConfig> damageConfigMap;

    public NoFlightListener(Map<DamageCause, DamageConfig> damageConfigMap) {
        this.damageConfigMap = damageConfigMap;
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player player)) {
            return;
        }

        EntityDamageEvent.DamageCause cause = event.getCause();
        DamageConfig config = damageConfigMap.get(cause);

        if (config == null) {
            return;
        }

        if (event instanceof EntityDamageByEntityEvent damageByEntityEvent) {
            if (isAllowed(damageByEntityEvent, config)) {
                NoFlightEffect.applyEffect(player, config.getDuration());
            }
        } else {
            NoFlightEffect.applyEffect(player, config.getDuration());
        }
    }
    private static boolean isAllowed(EntityDamageByEntityEvent damageByEntityEvent, DamageConfig config) {
        EntityType damagerType = damageByEntityEvent.getDamager().getType();
        Set<EntityType> entities = config.getEntities();
        boolean isAllowed;

        if (config.getMode() == DamageConfig.Mode.INCLUDE) {
            // Only allow specified entities
            isAllowed = !entities.isEmpty() && entities.contains(damagerType);
        } else {
            // Allow all except specified entities
            isAllowed = entities.isEmpty() || !entities.contains(damagerType);
        }
        return isAllowed;
    }
}