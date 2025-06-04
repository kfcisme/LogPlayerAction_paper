package me.wowkfccc.logplayeraction.logPlayerAction_paper.event;

import me.wowkfccc.logplayeraction.logPlayerAction_paper.LogPlayerAction_paper;
//import me.wowkfccc.logplayeraction.logplayeraction.Logplayeraction;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class onCraftItem implements Listener {
    private final LogPlayerAction_paper plugin;
    public static final Map<UUID, Integer> craftCounts = new HashMap<>();

    public onCraftItem(LogPlayerAction_paper plugin) {
        this.plugin = plugin;
//        int timer = plugin.getConfig().getInt("database.insert_interval", 3600);
//        new BukkitRunnable() {
//            @Override
//            public void run() {
//                resetCounters();
//            }
//        }.runTaskTimer(plugin, 0L, timer * 20L);
    }

    @EventHandler
    public void onCraftItem(CraftItemEvent event) {
        if (event.getWhoClicked() instanceof Player) {
            Player player = (Player) event.getWhoClicked();
            UUID playerId = player.getUniqueId();
            craftCounts.put(playerId, craftCounts.getOrDefault(playerId, 0) + 1);
            Bukkit.getLogger().info("Player " + player.getName() + " craft item count: " + craftCounts.get(playerId));
        }
    }

    public static int SendInsertData(UUID playerId){
        return craftCounts.getOrDefault(playerId, 0);
    }

    public static void resetCounters(UUID playerId) {
        Player player = Bukkit.getPlayer(playerId);
        for (Map.Entry<UUID, Integer> entry : craftCounts.entrySet()) {
//            Player player = Bukkit.getPlayer(entry.getKey());
            if (player != null) {
                Bukkit.getLogger().info("Player " + player.getName() + " total craft item count: " + entry.getValue());
            }
        }
        craftCounts.remove(playerId);
        Bukkit.getLogger().info("All player craft item counters have been reset.");
    }
}
