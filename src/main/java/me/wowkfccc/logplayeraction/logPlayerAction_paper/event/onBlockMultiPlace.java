package me.wowkfccc.logplayeraction.logPlayerAction_paper.event;
import me.wowkfccc.logplayeraction.logPlayerAction_paper.LogPlayerAction_paper;
//import me.wowkfccc.logplayeraction.logplayeraction.Logplayeraction;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockMultiPlaceEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class onBlockMultiPlace implements Listener {
    private final LogPlayerAction_paper plugin;
    public static final Map<UUID, Integer> multiPlaceCounts = new HashMap<>();

    public onBlockMultiPlace(LogPlayerAction_paper plugin) {
        this.plugin = plugin;
//        int timer = plugin.getConfig().getInt("database.insert_interval", 3600);
//        new BukkitRunnable() {
//            @Override
//            public void run() { resetCounters(); }
//        }.runTaskTimer(plugin, 0L, timer * 20L);
    }

    @EventHandler
    public void onBlockMultiPlace(BlockMultiPlaceEvent event) {
        Player player = event.getPlayer();
        UUID playerId = player.getUniqueId();
        multiPlaceCounts.put(playerId, multiPlaceCounts.getOrDefault(playerId, 0) + 1);
       // Bukkit.getLogger().info("Player " + player.getName() + " multi-place count: " + multiPlaceCounts.get(playerId));
    }

    public static int SendInsertData(UUID playerId){
        int v = multiPlaceCounts.getOrDefault(playerId, 0);
      //  Bukkit.getLogger().info("DEBUG multiPlace SendInsertData for " + playerId + ": " + v);
        return v;
    }

    public static void resetCounters(UUID playerId) {
        Player player = Bukkit.getPlayer(playerId);
        for (Map.Entry<UUID, Integer> entry : multiPlaceCounts.entrySet()) {
            //Player player = Bukkit.getPlayer(entry.getKey());
            if (player != null) {
          //      Bukkit.getLogger().info("Player " + player.getName() + " total multi-place count: " + entry.getValue());
            }
        }
        multiPlaceCounts.remove(playerId);
      //  Bukkit.getLogger().info("All player multi-place counters have been reset.");
    }
}