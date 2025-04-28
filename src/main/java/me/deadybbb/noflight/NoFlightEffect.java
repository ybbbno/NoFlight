package me.deadybbb.noflight;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class NoFlightEffect {
    private static final String EFFECT_KEY = "NoFlightEffect";
    private static final int DURATION_TICKS = 20 * 30;

    public static void applyEffect(Player player) {
        GameMode gamemode = player.getGameMode();
        if (player.hasMetadata(EFFECT_KEY) || gamemode == GameMode.SPECTATOR || gamemode == GameMode.CREATIVE) {
            return;
        }

        player.setMetadata(EFFECT_KEY, new FixedMetadataValue(JavaPlugin.getProvidingPlugin(NoFlight.class), true));

        new BukkitRunnable() {
            int ticks = 0;

            @Override
            public void run() {
                if(!player.isOnline() || ticks >= DURATION_TICKS) {
                    removeEffect(player);
                    cancel();
                    return;
                }

                player.setFlying(false);
                player.setGliding(false);
                player.setAllowFlight(false);

                ticks++;
            }
        }.runTaskTimer(JavaPlugin.getProvidingPlugin(NoFlight.class), 0L, 20L);
    }

    public static void removeEffect(Player player) {
        if (player.hasMetadata(EFFECT_KEY)) {
            player.removeMetadata(EFFECT_KEY, JavaPlugin.getProvidingPlugin(NoFlight.class));
            player.setAllowFlight(true);
        }
    }
}
