//package me.wowkfccc.logplayeraction.logplayeraction.event;
////
////import me.wowkfccc.logplayeraction.logplayeraction.Logplayeraction;
////import org.bukkit.Bukkit;
////import org.bukkit.entity.Player;
////import org.bukkit.event.EventHandler;
////import org.bukkit.event.Listener;
////import org.bukkit.event.block.BlockPlaceEvent;
////import org.bukkit.scheduler.BukkitRunnable;
////
////import java.util.HashMap;
////import java.util.Map;
////import java.util.UUID;
////
////public class onBlockRedstone implements Listener {
////    private final Logplayeraction plugin;
////    private final Map<UUID, Integer> playerBlockRedstoneCount = new HashMap<>();
////
////    public onBlockRedstone(Logplayeraction plugin) {
////        this.plugin = plugin;
////
////        // Schedule a task to reset counters periodically
////        int timer = plugin.getConfig().getInt("database.insert_interval", 3600); // Default to 1 hour
////        new BukkitRunnable() {
////            @Override
////            public void run() {
////                resetCounters();
////            }
////        }.runTaskTimer(plugin, 0L, timer * 20L);
////    }
////
////    @EventHandler
////    public void onBlockRedstone(BlockPlaceEven event) {
////        Player player = event.getPlayer();
////        UUID playerId = player.getUniqueId();
////
////        // Increment the player's block break counter
////        playerBlockRedstoneCount.put(playerId, playerBlockRedstoneCount.getOrDefault(playerId, 0) + 1);
////
////        // Log the current block break count for the player
////        Bukkit.getLogger().info("Player " + player.getName() + " Block Place count: " + playerBlockRedstoneCount.get(playerId));
////    }
////
////    private void resetCounters() {
////        // Log the counters before resetting
////        for (Map.Entry<UUID, Integer> entry : playerBlockRedstoneCount.entrySet()) {
////            Player player = Bukkit.getPlayer(entry.getKey());
////            if (player != null) {
////                Bukkit.getLogger().info("Player " + player.getName() + " total Block Place count: " + entry.getValue());
////            }
////        }
////
////        // Clear the counters
////        playerBlockRedstoneCount.clear();
////        Bukkit.getLogger().info("All player Block Place counters have been reset.");
////    }
////}
////
//import me.wowkfccc.logplayeraction.logplayeraction.Logplayeraction;
//import org.bukkit.Bukkit;
//import org.bukkit.entity.Player;
//import org.bukkit.event.EventHandler;
//import org.bukkit.event.Listener;
//import org.bukkit.event.block.BlockRedstoneEvent;
//import org.bukkit.scheduler.BukkitRunnable;
//
//import java.util.HashMap;
//import java.util.Map;
//import java.util.UUID;
//
//public class onBlockRedstone implements Listener {
//    private final Logplayeraction plugin;
//    private final Map<UUID, Integer> redstoneCounts = new HashMap<>();
//
//    public onBlockRedstone(Logplayeraction plugin) {
//        this.plugin = plugin;
//        int timer = plugin.getConfig().getInt("database.insert_interval", 3600);
//        new BukkitRunnable() {
//            @Override
//            public void run() { resetCounters(); }
//        }.runTaskTimer(plugin, 0L, timer * 20L);
//    }
//
//    @EventHandler
//    public void onBlockRedstone(BlockRedstoneEvent event) {
//        // BlockRedstoneEvent does not directly provide a player; skip or attribute generically
//        Bukkit.getLogger().info("Redstone signal changed at " + event.getBlock().getLocation());
//    }
//
//    private void resetCounters() {
//        redstoneCounts.clear();
//        Bukkit.getLogger().info("All player redstone counters have been reset.");
//    }
//}
