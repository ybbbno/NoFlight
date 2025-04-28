package me.deadybbb.noflight;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class NoFlightEffect {
    private static final String EFFECT_KEY = "NoFlightEffect";
    private static final int DURATION_TICKS = 20 * 10;
    private static final String TASK_KEY = "NoFlightEffectTask";

    public static void applyEffect(Player player) {
        GameMode gamemode = player.getGameMode();
        boolean hasElytra = player.getInventory().getChestplate() != null && player.getInventory().getChestplate().getType() == Material.ELYTRA;

        if (player.hasMetadata(EFFECT_KEY) || gamemode == GameMode.SPECTATOR || gamemode == GameMode.CREATIVE || !hasElytra) {
            return;
        }

        if (!player.hasMetadata(EFFECT_KEY)) {
            player.setMetadata(EFFECT_KEY, new FixedMetadataValue(JavaPlugin.getProvidingPlugin(NoFlight.class), true));
        }

        player.addPotionEffect(new PotionEffect(
                PotionEffectType.SLOW_FALLING,
                DURATION_TICKS,
                0,
                true,
                true,
                true
        ));

        if (player.hasMetadata(TASK_KEY)) {
            int taskId = player.getMetadata(TASK_KEY).get(0).asInt();
            JavaPlugin.getProvidingPlugin(NoFlight.class).getServer().getScheduler().cancelTask(taskId);
            player.removeMetadata(TASK_KEY, JavaPlugin.getProvidingPlugin(NoFlight.class));
        }

        BukkitTask task = new BukkitRunnable() {
            int ticks = 0;

            @Override
            public void run() {
                if(!player.isOnline() || ticks >= DURATION_TICKS || !player.hasMetadata(EFFECT_KEY)) {
                    removeEffect(player);
                    cancel();
                    return;
                }

                player.setFlying(false);
                player.setGliding(false);
                player.setAllowFlight(false);

                ticks++;
            }
        }.runTaskTimer(JavaPlugin.getProvidingPlugin(NoFlight.class), 0L, 1L);

        player.setMetadata(TASK_KEY, new FixedMetadataValue(JavaPlugin.getProvidingPlugin(NoFlight.class), task.getTaskId()));
    }

    public static void removeEffect(Player player) {
        if (player.hasMetadata(EFFECT_KEY)) {
            player.removeMetadata(EFFECT_KEY, JavaPlugin.getProvidingPlugin(NoFlight.class));
            player.removePotionEffect(PotionEffectType.SLOW);

            if (player.hasMetadata(TASK_KEY)) {
                int taskId = player.getMetadata(TASK_KEY).get(0).asInt();
                JavaPlugin.getProvidingPlugin(NoFlight.class).getServer().getScheduler().cancelTask(taskId);
                player.removeMetadata(TASK_KEY, JavaPlugin.getProvidingPlugin(NoFlight.class));
            }
        }
    }
}
