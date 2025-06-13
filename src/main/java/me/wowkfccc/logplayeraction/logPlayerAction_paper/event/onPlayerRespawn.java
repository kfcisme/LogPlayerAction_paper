package me.wowkfccc.logplayeraction.logPlayerAction_paper.event;

import me.wowkfccc.logplayeraction.logPlayerAction_paper.LogPlayerAction_paper;
//import me.wowkfccc.logplayeraction.logplayeraction.Logplayeraction;
import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerExpChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.entity.Entity;
import org.bukkit.event.player.PlayerRespawnEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class onPlayerRespawn implements Listener {
    private final LogPlayerAction_paper plugin;
    public static final Map<UUID, Integer> playerRespawnCount = new HashMap<>();

    public onPlayerRespawn(LogPlayerAction_paper plugin) {
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
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();
        UUID playerId = player.getUniqueId();

        // Increment the player's block break counter
        playerRespawnCount.put(playerId, playerRespawnCount.getOrDefault(playerId, 0) + 1);

        // Log the current block break count for the player
     //   Bukkit.getLogger().info("Player " + player.getName() + " playerRespawn count: " + playerRespawnCount.get(playerId));
    }

    public static int SendInsertData(UUID playerId){
        return playerRespawnCount.getOrDefault(playerId, 0);
    }

    public static void resetCounters(UUID playerId) {
        // Log the counters before resetting
        Player player = Bukkit.getPlayer(playerId);
        for (Map.Entry<UUID, Integer> entry : playerRespawnCount.entrySet()) {
            //Player player = Bukkit.getPlayer(entry.getKey());
            if (player != null) {
            //    Bukkit.getLogger().info("Player " + player.getName() + " total playerRespawn count: " + entry.getValue());
            }
        }

        // Clear the counters
        playerRespawnCount.remove(playerId);
      //  Bukkit.getLogger().info("All player playerRespawn counters have been reset.");
    }
}
