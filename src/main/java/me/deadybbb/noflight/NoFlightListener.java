package me.deadybbb.noflight;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class NoFlightListener implements Listener {
    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player player)) {
            return;
        }

        EntityDamageEvent.DamageCause cause = event.getCause();
        if (cause == EntityDamageEvent.DamageCause.VOID ||
            cause == EntityDamageEvent.DamageCause.FLY_INTO_WALL ||
            cause == EntityDamageEvent.DamageCause.FALL) {
            //event.setCancelled(true);
            return;
        }

        NoFlightEffect.applyEffect(player, 10);
    }
}
