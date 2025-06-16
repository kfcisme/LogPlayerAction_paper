package me.wowkfccc.logplayeraction.logPlayerAction_paper.event.plugin;

import me.wowkfccc.logplayeraction.logplayeraction.event.plugin.API.EssentialsHook;
import net.ess3.api.events.AfkStatusChangeEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import me.wowkfccc.logplayeraction.logPlayerAction_paper.LogPlayerAction_paper;
import com.earth2me.essentials.Essentials;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class onEssentialsAFK implements Listener {
    private final LogPlayerAction_paper plugin;
    private final Map<UUID, Integer> afkCounts = new HashMap<>();
    // 記錄玩家進入 AFK 的時間戳（毫秒）
    private final Map<UUID, Integer> afkStartTime = new HashMap<>();

    // 累積每位玩家的 AFK 時數（秒）
    public static final Map<UUID, Integer> afkTotalSeconds = new HashMap<>();


    public onEssentialsAFK(LogPlayerAction_paper plugin, EssentialsHook essentialsHook) {
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
        this.plugin = plugin;
    }

    @EventHandler
    public void onAfkStatusChange(AfkStatusChangeEvent event) {
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
        Player player = event.getAffected().getBase();
        UUID uuid = player.getUniqueId();
        boolean isAfk = event.getValue();

        if (isAfk) {
            // 進入 AFK：記錄當下時間
            afkStartTime.put(uuid, (int)System.currentTimeMillis());
      //      plugin.getLogger().info(player.getName() + " 進入 AFK");
        } else {
            // 離開 AFK：計算差距時間並累加
            if (afkStartTime.containsKey(uuid)) {
                int start = afkStartTime.remove(uuid);
                int durationSec = ((int)System.currentTimeMillis() - start) / 1000;
//                afkCounts.put(uuid, (afkCounts.getOrDefault(uuid, 0L) + durationSec));
//                int current = ;
                afkCounts.put(uuid,afkCounts.getOrDefault(uuid, 0));
            //    plugin.getLogger().info(player.getName() + " 離開 AFK，時長：" + durationSec + " 秒");
            }
        }
    }
    public int getAfkTotalSeconds(UUID uuid) {
        return afkTotalSeconds.getOrDefault(uuid, 0);
    }

    public static int SendInsertData(UUID playerId){
        return afkTotalSeconds.getOrDefault(playerId, 0);
    }

    public static void resetCounters(UUID playerId) {
        Player player = Bukkit.getPlayer(playerId);
        // Log the counters before resetting
        for (Map.Entry<UUID, Integer> entry : afkTotalSeconds.entrySet()) {
            if (player != null) {
              //  Bukkit.getLogger().info("Player " + player.getName() + " total chat time: " + entry.getValue() + " seconds.");
            }
        }

        // Clear the counters
        afkTotalSeconds.remove(playerId);
        //Bukkit.getLogger().info("All player chat counters have been reset.");
    }
}
