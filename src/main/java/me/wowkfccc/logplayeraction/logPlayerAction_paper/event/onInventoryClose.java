package me.wowkfccc.logplayeraction.logPlayerAction_paper.event;

import me.wowkfccc.logplayeraction.logPlayerAction_paper.LogPlayerAction_paper;
//import me.wowkfccc.logplayeraction.logplayeraction.Logplayeraction;
import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockCanBuildEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class onInventoryClose implements Listener {
    private final LogPlayerAction_paper plugin;
    public static final Map<UUID, Integer> playerInventoryCloseCount = new HashMap<>();

    public onInventoryClose(LogPlayerAction_paper plugin) {
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
    public void onInventoryClose(InventoryCloseEvent event) {
        HumanEntity humanEntity = event.getPlayer();
        Player player = null;
        UUID playerId = humanEntity.getUniqueId();

        // Increment the player's block break counter
        playerInventoryCloseCount.put(playerId, playerInventoryCloseCount.getOrDefault(playerId, 0) + 1);

        // Log the current block break count for the player
      //  Bukkit.getLogger().info("Player " + humanEntity.getName() + " InventoryClose count: " + playerInventoryCloseCount.get(playerId));
    }

    public static int SendInsertData(UUID playerId){
        return playerInventoryCloseCount.getOrDefault(playerId, 0);
    }

    public static void resetCounters(UUID playerId) {
        // Log the counters before resetting
        Player player = Bukkit.getPlayer(playerId);
        for (Map.Entry<UUID, Integer> entry : playerInventoryCloseCount.entrySet()) {
            if (player != null) {
          //      Bukkit.getLogger().info("Player " + player.getName() + " total InventoryClose count: " + entry.getValue());
            }
        }

        // Clear the counters
        playerInventoryCloseCount.remove(playerId);

        String nameOrId = (player != null ? player.getName() : playerId.toString());
    //    Bukkit.getLogger().info("InventoryClose counter for " + nameOrId + " has been reset.");
    }
}
