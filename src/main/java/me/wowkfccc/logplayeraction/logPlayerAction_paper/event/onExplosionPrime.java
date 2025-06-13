package me.wowkfccc.logplayeraction.logPlayerAction_paper.event;
import me.wowkfccc.logplayeraction.logPlayerAction_paper.LogPlayerAction_paper;
//import me.wowkfccc.logplayeraction.logplayeraction.Logplayeraction;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ExplosionPrimeEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class onExplosionPrime implements Listener {
    private final LogPlayerAction_paper plugin;
    public static final Map<UUID, Integer> explosionPrimeCounts = new HashMap<>();

    public onExplosionPrime(LogPlayerAction_paper plugin) {
        this.plugin = plugin;
//        int timer = plugin.getConfig().getInt("database.insert_interval", 3600);
//        new BukkitRunnable() {
//            @Override
//            public void run() { resetCounters(); }
//        }.runTaskTimer(plugin, 0L, timer * 20L);
    }

    @EventHandler
    public void onExplosionPrime(ExplosionPrimeEvent event) {
        Entity entity = event.getEntity();
        if (entity instanceof TNTPrimed) {
            TNTPrimed tnt = (TNTPrimed) entity;
            if (tnt.getSource() instanceof Player) {
                Player player = (Player) tnt.getSource();
                UUID playerId = player.getUniqueId();
                explosionPrimeCounts.put(playerId, explosionPrimeCounts.getOrDefault(playerId, 0) + 1);
          //      Bukkit.getLogger().info("Player " + player.getName() + " primed TNT count: " + explosionPrimeCounts.get(playerId));
            }
        }
    }

    public static int SendInsertData(UUID playerId){
        return explosionPrimeCounts.getOrDefault(playerId, 0);
    }

        public static void resetCounters(UUID playerId) {
        Player player = Bukkit.getPlayer(playerId);
            for (Map.Entry<UUID, Integer> entry : explosionPrimeCounts.entrySet()) {
                //Player player = Bukkit.getPlayer(entry.getKey());
                if (player != null) {
              //      Bukkit.getLogger().info("Player " + player.getName() + " total TNT prime count: " + entry.getValue());
                }
            }
            explosionPrimeCounts.remove(playerId);
      //      Bukkit.getLogger().info("All player TNT prime counters have been reset.");
        }
}