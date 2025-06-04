//package me.wowkfccc.logplayeraction.logplayeraction.event;
//import me.wowkfccc.logplayeraction.logplayeraction.Logplayeraction;
//import org.bukkit.Bukkit;
//import org.bukkit.entity.HumanEntity;
//import org.bukkit.entity.Player;
//import org.bukkit.event.EventHandler;
//import org.bukkit.event.Listener;
//import org.bukkit.event.inventory.FurnaceSmeltEvent;
//import org.bukkit.scheduler.BukkitRunnable;
//
//import java.util.HashMap;
//import java.util.Map;
//import java.util.UUID;
//
//public class onFurnaceSmelt implements Listener {
//    private final Logplayeraction plugin;
//    private final Map<UUID, Integer> furnaceSmeltCounts = new HashMap<>();
//
//    public onFurnaceSmelt(Logplayeraction plugin) {
//        this.plugin = plugin;
//        int timer = plugin.getConfig().getInt("database.insert_interval", 3600);
//        new BukkitRunnable() {
//            @Override
//            public void run() { resetCounters(); }
//        }.runTaskTimer(plugin, 0L, timer * 20L);
//    }
//
//    @EventHandler
//    public void onFurnaceSmelt(FurnaceSmeltEvent event) {
//        HumanEntity human = event.getWhoClicked();
//        if (human instanceof Player) {
//            Player player = (Player) human;
//            UUID playerId = player.getUniqueId();
//            furnaceSmeltCounts.put(playerId, furnaceSmeltCounts.getOrDefault(playerId, 0) + 1);
//            Bukkit.getLogger().info("Player " + player.getName() + " smelt count: " + furnaceSmeltCounts.get(playerId));
//        }
//    }
//
//    private void resetCounters() {
//        for (Map.Entry<UUID, Integer> entry : furnaceSmeltCounts.entrySet()) {
//            Player player = Bukkit.getPlayer(entry.getKey());
//            if (player != null) {
//                Bukkit.getLogger().info("Player " + player.getName() + " total smelt count: " + entry.getValue());
//            }
//        }
//        furnaceSmeltCounts.clear();
//        Bukkit.getLogger().info("All player smelt counters have been reset.");
//    }
//}
