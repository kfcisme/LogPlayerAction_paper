package me.wowkfccc.logplayeraction.logPlayerAction_paper.event;

import me.wowkfccc.logplayeraction.logPlayerAction_paper.LogPlayerAction_paper;
//import me.wowkfccc.logplayeraction.logPlayerAction_paper.Logplayeraction;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class onAsyncPlayerChat implements Listener {
    private final LogPlayerAction_paper plugin;
    private static final Map<UUID, Integer> playerChatTime = new HashMap<>();

    public onAsyncPlayerChat(LogPlayerAction_paper plugin) {
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
    public void onAsyncChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        UUID playerId = player.getUniqueId();

        // Increment the player's chat time counter
        playerChatTime.put(playerId, playerChatTime.getOrDefault(playerId, 0) + 1);

        // Log the current chat time for the player
       // Bukkit.getLogger().info("Player " + player.getName() + " chat time: " + playerChatTime.get(playerId) + " seconds.");
    }

    public static int SendInsertData(UUID playerId){
        return playerChatTime.getOrDefault(playerId, 0);
    }

    public static void resetCounters(UUID playerId) {
        Player player = Bukkit.getPlayer(playerId);
        // Log the counters before resetting
        for (Map.Entry<UUID, Integer> entry : playerChatTime.entrySet()) {

            if (player != null) {
              //  Bukkit.getLogger().info("Player " + player.getName() + " total chat time: " + entry.getValue() + " seconds.");
            }
        }

        // Clear the counters
        playerChatTime.remove(playerId);
       // Bukkit.getLogger().info("All player chat counters have been reset.");
    }
}