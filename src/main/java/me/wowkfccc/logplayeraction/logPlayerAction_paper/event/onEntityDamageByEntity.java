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
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class onEntityDamageByEntity implements Listener {
    private final LogPlayerAction_paper plugin;
    public static final Map<UUID, Integer> playerEntityDamageByEntityCount = new HashMap<>();

    public onEntityDamageByEntity(LogPlayerAction_paper plugin) {
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
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        Entity entity = event.getDamager();
        Player player = null;
        UUID playerId = entity.getUniqueId();

        // Increment the player's block break counter
        playerEntityDamageByEntityCount.put(playerId, playerEntityDamageByEntityCount.getOrDefault(playerId, 0) + 1);

        // Log the current block break count for the player
        Bukkit.getLogger().info("Player " + entity.getName() + " Entity Damage By Entity count: " + playerEntityDamageByEntityCount.get(playerId));
    }

    public static int SendInsertData(UUID playerId){
        return playerEntityDamageByEntityCount.getOrDefault(playerId, 0);
    }

    public static void resetCounters(UUID playerId) {
        Player player = Bukkit.getPlayer(playerId);
        // Log the counters before resetting
        for (Map.Entry<UUID, Integer> entry : playerEntityDamageByEntityCount.entrySet()) {
            //Player player = Bukkit.getPlayer(entry.getKey());
            if (player != null) {
                Bukkit.getLogger().info("Player " + player.getName() + " total Entity Damage By Entity count: " + entry.getValue());
            }
        }

        // Clear the counters
        playerEntityDamageByEntityCount.remove(playerId);
        Bukkit.getLogger().info("All player Entity Damage By Entity counters have been reset.");
    }
}

