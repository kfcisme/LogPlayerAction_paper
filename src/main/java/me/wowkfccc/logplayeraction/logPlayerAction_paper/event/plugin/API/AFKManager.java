package me.wowkfccc.logplayeraction.logPlayerAction_paper.event.plugin.API;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AFKManager {
    private static final Map<UUID, Long> lastActivity = new HashMap<>();
    private static final Map<UUID, Boolean> isAfk = new HashMap<>();
    private static final Map<UUID, Long> afkStartTime = new HashMap<>();
    private static final Map<UUID, Long> afkTotalSeconds = new HashMap<>();

    // 閾值：300秒
    private static final long THRESHOLD_MS = 300L * 1000L;

    public static void recordActivity(Player p) {
        UUID uuid = p.getUniqueId();
        long now = System.currentTimeMillis();

        if (Boolean.TRUE.equals(isAfk.get(uuid))) {
            Long start = afkStartTime.remove(uuid);
            if (start != null) {
                long durationSec = (now - start) / 1000;
                afkTotalSeconds.put(uuid,
                        afkTotalSeconds.getOrDefault(uuid, 0L) + durationSec
                );
//                p.sendMessage("§a你已從 AFK 狀態中恢復！本次 AFK 持續 " + durationSec + " 秒");
            }
            isAfk.put(uuid, false);
        }

        lastActivity.put(uuid, now);
    }

    /**
     * 定時檢查所有上線玩家是否超過閾值未動，標記 AFK
     */
    public static void checkAllAfk() {
        long now = System.currentTimeMillis();
        for (Player p : Bukkit.getOnlinePlayers()) {
            UUID uuid = p.getUniqueId();
            long last = lastActivity.getOrDefault(uuid, now);

            if (!Boolean.TRUE.equals(isAfk.get(uuid)) && now - last >= THRESHOLD_MS) {
                isAfk.put(uuid, true);
                afkStartTime.put(uuid, now);
//                p.sendMessage("§7你已進入 §eAFK §7狀態...");
            }
        }
    }

    /**
     * 取得玩家累計 AFK 時長 (秒)，以 int 回傳
     */
    public static int getAfkTotalSeconds(UUID uuid) {
        long total = afkTotalSeconds.getOrDefault(uuid, 0L);
        long now = System.currentTimeMillis();

        if (Boolean.TRUE.equals(isAfk.get(uuid))) {
            long start = afkStartTime.getOrDefault(uuid, now);
            long currentDuration = (now - start) / 1000;
            total += currentDuration;

            afkTotalSeconds.put(uuid, 0L);
            afkStartTime.put(uuid, now);
        }

        return (int) total;
    }

    /**
     * 重置單一玩家 AFK 計數
     */
    public static void resetAfkCounters(UUID uuid) {
        lastActivity.remove(uuid);
        isAfk.remove(uuid);
        afkStartTime.remove(uuid);
        afkTotalSeconds.remove(uuid);
    }

    /**
     * 清除所有 AFK 資料 (例如伺服器關閉)
     */
    public static void clear() {
        lastActivity.clear();
        isAfk.clear();
        afkStartTime.clear();
        afkTotalSeconds.clear();
    }
}