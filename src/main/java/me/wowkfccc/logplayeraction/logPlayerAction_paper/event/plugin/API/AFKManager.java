package me.wowkfccc.logplayeraction.logPlayerAction_paper.event.plugin.API;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AFKManager {
    private static final Map<UUID, Long> lastActivity = new HashMap<>();
    private static final Map<UUID, Boolean> isAfk = new HashMap<>();
    private static final Map<UUID, Long> afkStartTime = new HashMap<>();
    private static final Map<UUID, Long> afkTotalSeconds = new HashMap<>();

    // 閾值：300 秒（5 分鐘）
    private static final long THRESHOLD_MS = 300L * 1000L;

    /**
     * 當玩家有動作時呼叫
     */
    public static void recordActivity(Player p) {
        UUID uuid = p.getUniqueId();
        long now = System.currentTimeMillis();

        // 如果玩家是 AFK 狀態，計算這段 AFK 的持續時間
        if (Boolean.TRUE.equals(isAfk.get(uuid))) {
            Long start = afkStartTime.remove(uuid);
            if (start != null) {
                long durationSec = (now - start) / 1000;
                afkTotalSeconds.put(uuid,
                        afkTotalSeconds.getOrDefault(uuid, 0L) + durationSec);
            }
            isAfk.put(uuid, false);
        }

        lastActivity.put(uuid, now);
    }

    /**
     * 每 N 秒執行一次，用來檢查是否進入 AFK 狀態
     */
    public static void checkAllAfk() {
        long now = System.currentTimeMillis();
        for (Player p : Bukkit.getOnlinePlayers()) {
            UUID uuid = p.getUniqueId();
            long last = lastActivity.getOrDefault(uuid, now);

            // 如果尚未 AFK 且超過閾值
            if (!Boolean.TRUE.equals(isAfk.get(uuid)) && now - last >= THRESHOLD_MS) {
                isAfk.put(uuid, true);
                afkStartTime.put(uuid, now);
            }
        }
    }

    /**
     * 不會清除的查看方式：用於每小時記錄狀況
     */
    public static int peekAfkTotalSeconds(UUID uuid) {
        long total = afkTotalSeconds.getOrDefault(uuid, 0L);
        if (Boolean.TRUE.equals(isAfk.get(uuid))) {
            long now = System.currentTimeMillis();
            long start = afkStartTime.getOrDefault(uuid, now);
            total += (now - start) / 1000;
        }
        return (int) total;
    }

    /**
     * 真正清除計數器的方式，用於記錄後歸零
     */
    public static int consumeAfkTotalSeconds(UUID uuid) {
        int seconds = peekAfkTotalSeconds(uuid);

        // 重設所有記錄
        afkTotalSeconds.put(uuid, 0L);
        if (Boolean.TRUE.equals(isAfk.get(uuid))) {
            afkStartTime.put(uuid, System.currentTimeMillis());
        }

        return seconds;
    }

    /**
     * 完整重置（離線時呼叫）
     */
    public static void resetAfkCounters(UUID uuid) {
        lastActivity.remove(uuid);
        isAfk.remove(uuid);
        afkStartTime.remove(uuid);
        afkTotalSeconds.remove(uuid);
    }

    public static void clear() {
        lastActivity.clear();
        isAfk.clear();
        afkStartTime.clear();
        afkTotalSeconds.clear();
    }

    public static boolean isCurrentlyAfk(UUID uuid) {
        return Boolean.TRUE.equals(isAfk.get(uuid));
    }
}
