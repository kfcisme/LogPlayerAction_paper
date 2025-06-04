//package me.wowkfccc.logplayeraction.logplayeraction.event;
//
//import me.wowkfccc.logplayeraction.logplayeraction.Logplayeraction;
//import org.bukkit.Bukkit;
//import org.bukkit.entity.Player;
//import org.bukkit.event.EventHandler;
//import org.bukkit.event.Listener;
//import org.bukkit.event.block.BlockBurnEvent;
//import org.bukkit.scheduler.BukkitRunnable;
//
//import java.util.HashMap;
//import java.util.Map;
//import java.util.UUID;
//
//public class onBlockBurn implements Listener {
//    private final Logplayeraction plugin;
//    private final Map<UUID, Integer> playerBlockBurnCount = new HashMap<>();
//
//    public onBlockBurn(Logplayeraction plugin) {
//        this.plugin = plugin;
//
//        // Schedule a task to reset counters periodically
//        int timer = plugin.getConfig().getInt("database.insert_interval", 3600); // Default to 1 hour
//        new BukkitRunnable() {
//            @Override
//            public void run() {
//                resetCounters();
//            }
//        }.runTaskTimer(plugin, 0L, timer * 20L);
//    }
//
//    @EventHandler
//    public void onBlockBurn(BlockBurnEvent event) {
//        Player player = event.getPlayer();
//        UUID playerId = player.getUniqueId();
//
//        // Increment the player's block break counter
//        playerBlockBurnCount.put(playerId, playerBlockBurnCount.getOrDefault(playerId, 0) + 1);
//
//        // Log the current block break count for the player
//        Bukkit.getLogger().info("Player " + player.getName() + " block burn count: " + playerBlockBurnCount.get(playerId));
//    }
//
//    private void resetCounters() {
//        // Log the counters before resetting
//        for (Map.Entry<UUID, Integer> entry : playerBlockBurnCount.entrySet()) {
//            Player player = Bukkit.getPlayer(entry.getKey());
//            if (player != null) {
//                Bukkit.getLogger().info("Player " + player.getName() + " total block burn count: " + entry.getValue());
//            }
//        }
//
//        // Clear the counters
//        playerBlockBurnCount.clear();
//        Bukkit.getLogger().info("All player block burn counters have been reset.");
//    }
//}