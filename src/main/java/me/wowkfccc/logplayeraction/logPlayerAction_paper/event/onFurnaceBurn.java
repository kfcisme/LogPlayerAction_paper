//package me.wowkfccc.logplayeraction.logplayeraction.event;
//import me.wowkfccc.logplayeraction.logplayeraction.Logplayeraction;
//import org.bukkit.Bukkit;
//import org.bukkit.entity.HumanEntity;
//import org.bukkit.event.EventHandler;
//import org.bukkit.event.Listener;
//import org.bukkit.event.inventory.FurnaceBurnEvent;
//import org.bukkit.scheduler.BukkitRunnable;
//
//import java.util.HashMap;
//import java.util.Map;
//import java.util.UUID;
//import org.bukkit.entity.Player;
//
//public class onFurnaceBurn implements Listener {
//    private final Logplayeraction plugin;
//    private final Map<UUID, Integer> furnaceBurnCounts = new HashMap<>();
//
//    public onFurnaceBurn(Logplayeraction plugin) {
//        this.plugin = plugin;
//        int timer = plugin.getConfig().getInt("database.insert_interval", 3600);
//        new BukkitRunnable() {
//            @Override
//            public void run() { resetCounters(); }
//        }.runTaskTimer(plugin, 0L, timer * 20L);
//    }
//
//    @EventHandler
//    public void onFurnaceBurn(FurnaceBurnEvent event) {
//        HumanEntity human = event.getWhoBurned();
//        if (human instanceof Player) {
//            Player player = (Player) human;
//            UUID playerId = player.getUniqueId();
//            furnaceBurnCounts.put(playerId, furnaceBurnCounts.getOrDefault(playerId, 0) + 1);
//            Bukkit.getLogger().info("Player " + player.getName() + " burn count: " + furnaceBurnCounts.get(playerId));
//        }
//    }
//
//    private void resetCounters() {
//        for (Map.Entry<UUID, Integer> entry : furnaceBurnCounts.entrySet()) {
//            Player player = Bukkit.getPlayer(entry.getKey());
//            if (player != null) {
//                Bukkit.getLogger().info("Player " + player.getName() + " total burn count: " + entry.getValue());
//            }
//        }
//        furnaceBurnCounts.clear();
//        Bukkit.getLogger().info("All player burn counters have been reset.");
//    }
//}
