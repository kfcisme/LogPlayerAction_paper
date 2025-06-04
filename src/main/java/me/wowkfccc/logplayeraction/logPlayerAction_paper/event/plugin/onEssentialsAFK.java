//package me.wowkfccc.logplayeraction.logplayeraction.event.plugin.;
//
//import me.wowkfccc.logplayeraction.logplayeraction.event.plugin.API.EssentialsHook;
//import net.ess3.api.events.AfkStatusChangeEvent;
//import org.bukkit.event.EventHandler;
//import org.bukkit.event.Listener;
//import me.wowkfccc.logplayeraction.logplayeraction.Logplayeraction;
//
//import java.lang.reflect.Method;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.UUID;
//
//public class onEssentialsAFK implements Listener {
//    private final Logplayeraction plugin;
//    private final Map<UUID, Integer> afkCounts = new HashMap<>();
//
//    public onEssentialsAFK(Logplayeraction plugin, EssentialsHook essentialsHook) {
//        this.plugin = plugin;
//        int timer = plugin.getConfig().getInt("database.insert_interval", 3600);
//        // 排程重置計數
//        new org.bukkit.scheduler.BukkitRunnable() {
//            @Override
//            public void run() {
//                afkCounts.forEach((uuid, count) -> {
//                    org.bukkit.entity.Player p = org.bukkit.Bukkit.getPlayer(uuid);
//                    if (p != null) {
//                        plugin.getLogger().info(
//                                "Player " + p.getName() + " total AFK count: " + count
//                        );
//                    }
//                });
//                afkCounts.clear();
//            }
//        }.runTaskTimer(plugin, 0L, timer * 20L);
//    }
//
//    @EventHandler
//    public void onAfkStatusChange(AfkStatusChangeEvent event) {
//        UUID playerId = event.getAffected().getBase().getUniqueId();
//        afkCounts.put(playerId, afkCounts.getOrDefault(playerId, 0) + 1);
//
//        Object essEcon = plugin.getEssEconomy();
//        if (essEcon != null) {
//            try {
//                // com.earth2me.essentials.api.Economy#take(String,double)
//                Method take = essEcon.getClass()
//                        .getMethod("take", String.class, double.class);
//                String name = event.getAffected().getBase().getName();
//                take.invoke(essEcon, name, 1.0);
//            } catch (Exception ex) {
//                plugin.getLogger().warning("扣款失敗：" + ex.getMessage());
//            }
//        }
//    }
//}
