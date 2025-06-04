package me.wowkfccc.logplayeraction.logPlayerAction_paper.event;

import me.wowkfccc.logplayeraction.logPlayerAction_paper.LogPlayerAction_paper;
//import me.wowkfccc.logplayeraction.logplayeraction.Logplayeraction;
import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockCanBuildEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.event.inventory.InventoryOpenEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class onInventoryOpen implements Listener {
    private final LogPlayerAction_paper plugin;
    public static final Map<UUID, Integer> playerInventoryopenCount = new HashMap<>();

    public onInventoryOpen(LogPlayerAction_paper plugin) {
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
    public void onInventoryOpen(InventoryOpenEvent event) {
        HumanEntity humanEntity = event.getPlayer();
        Player player = null;
        UUID playerId = humanEntity.getUniqueId();

        // Increment the player's block break counter
        playerInventoryopenCount.put(playerId, playerInventoryopenCount.getOrDefault(playerId, 0) + 1);

        // Log the current block break count for the player
        Bukkit.getLogger().info("Player " + humanEntity.getName() + " Inventoryopen count: " + playerInventoryopenCount.get(playerId));
    }

    public static int SendInsertData(UUID playerId){
        return playerInventoryopenCount.getOrDefault(playerId, 0);
    }

    public static void resetCounters(UUID playerId) {
        // Log the counters before resetting
        Player player = Bukkit.getPlayer(playerId);
        for (Map.Entry<UUID, Integer> entry : playerInventoryopenCount.entrySet()) {
            //Player player = Bukkit.getPlayer(entry.getKey());
            if (player != null) {
                Bukkit.getLogger().info("Player " + player.getName() + " total Inventoryopen count: " + entry.getValue());
            }
        }

        // Clear the counters
        playerInventoryopenCount.remove(playerId);
        Bukkit.getLogger().info("All player Inventoryopen counters have been reset.");
    }
}
