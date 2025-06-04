package me.wowkfccc.logplayeraction.logPlayerAction_paper.event;

import me.wowkfccc.logplayeraction.logPlayerAction_paper.LogPlayerAction_paper;
//import me.wowkfccc.logplayeraction.logplayeraction.Logplayeraction;
import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockCanBuildEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerCommandSendEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.entity.Entity;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class onPlayerDeath implements Listener {
    private final LogPlayerAction_paper plugin;
    public static final Map<UUID, Integer> playerPlayerDeathCount = new HashMap<>();

    public onPlayerDeath(LogPlayerAction_paper plugin) {
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
    public void onPlayerDeath(PlayerDeathEvent event) {
        Entity entity = event.getEntity();
        Player player = null;
        UUID playerId = entity.getUniqueId();

        // Increment the player's block break counter
        playerPlayerDeathCount.put(playerId, playerPlayerDeathCount.getOrDefault(playerId, 0) + 1);

        // Log the current block break count for the player
        Bukkit.getLogger().info("Player " + entity.getName() + " PlayerDeath count: " + playerPlayerDeathCount.get(playerId));
    }

    public static int SendInsertData(UUID playerId){
        return playerPlayerDeathCount.getOrDefault(playerId, 0);
    }

    public static void resetCounters(UUID playerId) {
        // Log the counters before resetting
        for (Map.Entry<UUID, Integer> entry : playerPlayerDeathCount.entrySet()) {
            Player player = Bukkit.getPlayer(entry.getKey());
            if (player != null) {
                Bukkit.getLogger().info("Player " + player.getName() + " total PlayerDeath count: " + entry.getValue());
            }
        }

        // Clear the counters
        playerPlayerDeathCount.remove(playerId);
        Bukkit.getLogger().info("All player PlayerDeath counters have been reset.");
    }
}


