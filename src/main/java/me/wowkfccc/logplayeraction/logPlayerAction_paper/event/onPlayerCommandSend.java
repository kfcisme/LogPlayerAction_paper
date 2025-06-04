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
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class onPlayerCommandSend implements Listener {
    private final LogPlayerAction_paper plugin;
    public static final Map<UUID, Integer> playerPlayerCommandSendCount = new HashMap<>();

    public onPlayerCommandSend(LogPlayerAction_paper plugin) {
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
    public void onPlayerCommandSend(PlayerCommandSendEvent event) {

        Player player = event.getPlayer();
        UUID playerId = player.getUniqueId();

        // Increment the player's block break counter
        playerPlayerCommandSendCount.put(playerId, playerPlayerCommandSendCount.getOrDefault(playerId, 0) + 1);

        // Log the current block break count for the player
        Bukkit.getLogger().info("Player " + player.getName() + " PlayerCommandSend count: " + playerPlayerCommandSendCount.get(playerId));
    }

    public static int SendInsertData(UUID playerId){
        return playerPlayerCommandSendCount.getOrDefault(playerId, 0);
    }

    public static void resetCounters(UUID playerId) {
        // Log the counters before resetting
        for (Map.Entry<UUID, Integer> entry : playerPlayerCommandSendCount.entrySet()) {
            Player player = Bukkit.getPlayer(entry.getKey());
            if (player != null) {
                Bukkit.getLogger().info("Player " + player.getName() + " total PlayerCommandSend count: " + entry.getValue());
            }
        }

        // Clear the counters
        playerPlayerCommandSendCount.remove(playerId);
        Bukkit.getLogger().info("All player PlayerCommandSend counters have been reset.");
    }
}


