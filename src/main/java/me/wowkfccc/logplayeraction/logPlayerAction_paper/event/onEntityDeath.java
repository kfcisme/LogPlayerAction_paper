package me.wowkfccc.logplayeraction.logPlayerAction_paper.event;

import me.wowkfccc.logplayeraction.logPlayerAction_paper.LogPlayerAction_paper;
//import me.wowkfccc.logplayeraction.logplayeraction.Logplayeraction;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class onEntityDeath implements Listener {
    private final LogPlayerAction_paper plugin;
    public static final Map<UUID, Integer> playerEntityDamageCount = new HashMap<>();

    public onEntityDeath(LogPlayerAction_paper plugin) {
        this.plugin = plugin;

        // Schedule a task to reset counters periodically
//        int timer = plugin.getConfig().getInt("database.insert_interval", 3600); // Default to 1 hour
//        new BukkitRunnable() {
//            @Override
//            public void run() {
//                resetCounters();
//            }
//        }.runTaskTimer(plugin, 0L, timer * 20L);
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        Entity entity = event.getEntity();
        Player player = null;
        UUID playerId = entity.getUniqueId();
        Player killer = event.getEntity().getKiller();
        UUID killerId = null;
        if (killer != null) {
            killerId = killer.getUniqueId();
        }

        // Increment the player's block break counter
        playerEntityDamageCount.put(killerId, playerEntityDamageCount.getOrDefault(playerId, 0) + 1);

        // Log the current block break count for the player
        //Bukkit.getLogger().info("Player " + entity.getName() + " EntityDamageCount: " + playerEntityDamageCount.get(playerId));
    }

    public static int SendInsertData(UUID killerId){
        return playerEntityDamageCount.getOrDefault(killerId, 0);
    }

    public static void resetCounters(UUID killerId) {
        // Log the counters before resetting
        Player player = Bukkit.getPlayer(killerId);
        for (Map.Entry<UUID, Integer> entry : playerEntityDamageCount.entrySet()) {
            //Player player = Bukkit.getPlayer(entry.getKey());
            if (player != null) {
                // Bukkit.getLogger().info("Player " + player.getName() + " total EntityDamageCount: " + entry.getValue());
            }
        }

        // Clear the counters
        playerEntityDamageCount.remove(killerId);
        // Bukkit.getLogger().info("All player EntityDamageCounters have been reset.");
    }
}

