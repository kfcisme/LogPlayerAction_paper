package me.wowkfccc.logplayeraction.logPlayerAction_paper.event;

import me.wowkfccc.logplayeraction.logPlayerAction_paper.LogPlayerAction_paper;
//import me.wowkfccc.logplayeraction.logplayeraction.Logplayeraction;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.FurnaceExtractEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class onFurnaceExtract implements Listener {
    private final LogPlayerAction_paper plugin;
    public static final Map<UUID, Integer> playerFurnaceExtractCount = new HashMap<>();

    public onFurnaceExtract(LogPlayerAction_paper plugin) {
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
    public void onFurnaceExtract(FurnaceExtractEvent event) {
        Player player = event.getPlayer();
        UUID playerId = player.getUniqueId();

        // Increment the player's block break counter
        playerFurnaceExtractCount.put(playerId, playerFurnaceExtractCount.getOrDefault(playerId, 0) + 1);

        // Log the current block break count for the player
      //  Bukkit.getLogger().info("Player " + player.getName() + " FurnaceExtract count: " + playerFurnaceExtractCount.get(playerId));
    }

    public static int SendInsertData(UUID playerId){
        return playerFurnaceExtractCount.getOrDefault(playerId, 0);
    }

    public static void resetCounters(UUID playerId) {
        // Log the counters before resetting
        Player player = Bukkit.getPlayer(playerId);
        for (Map.Entry<UUID, Integer> entry : playerFurnaceExtractCount.entrySet()) {
            //Player player = Bukkit.getPlayer(entry.getKey());
            if (player != null) {
          //      Bukkit.getLogger().info("Player " + player.getName() + " total FurnaceExtract count: " + entry.getValue());
            }
        }

        // Clear the counters
        playerFurnaceExtractCount.remove(playerId);
    //    Bukkit.getLogger().info("All player FurnaceExtract counters have been reset.");
    }
}

