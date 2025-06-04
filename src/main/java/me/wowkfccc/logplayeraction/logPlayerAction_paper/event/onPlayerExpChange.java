package me.wowkfccc.logplayeraction.logPlayerAction_paper.event;

import me.wowkfccc.logplayeraction.logPlayerAction_paper.LogPlayerAction_paper;
//import me.wowkfccc.logplayeraction.logplayeraction.Logplayeraction;
import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerExpChangeEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.entity.Entity;
import org.bukkit.event.player.PlayerExpChangeEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class onPlayerExpChange implements Listener {
    private final LogPlayerAction_paper plugin;
    public static final Map<UUID, Integer> playerPlayerExpChangeCount = new HashMap<>();

    public onPlayerExpChange(LogPlayerAction_paper plugin) {
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
    public void onPlayerExpChange(PlayerExpChangeEvent event) {
        Player player = event.getPlayer();
        UUID playerId = player.getUniqueId();

        // Increment the player's block break counter
        playerPlayerExpChangeCount.put(playerId, playerPlayerExpChangeCount.getOrDefault(playerId, 0) + 1);

        // Log the current block break count for the player
        Bukkit.getLogger().info("Player " + player.getName() + " PlayerExpChange count: " + playerPlayerExpChangeCount.get(playerId));
    }

    public static int SendInsertData(UUID playerId){
        return playerPlayerExpChangeCount.getOrDefault(playerId, 0);
    }

    public static void resetCounters(UUID playerId) {
        // Log the counters before resetting
        Player player = Bukkit.getPlayer(playerId);
        for (Map.Entry<UUID, Integer> entry : playerPlayerExpChangeCount.entrySet()) {
            //Player player = Bukkit.getPlayer(entry.getKey());
            if (player != null) {
                Bukkit.getLogger().info("Player " + player.getName() + " total PlayerExpChange count: " + entry.getValue());
            }
        }

        // Clear the counters
        playerPlayerExpChangeCount.remove(playerId);
        Bukkit.getLogger().info("All player PlayerExpChange counters have been reset.");
    }
}
