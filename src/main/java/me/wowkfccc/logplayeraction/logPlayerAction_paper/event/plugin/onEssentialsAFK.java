package me.wowkfccc.logplayeraction.logPlayerAction_paper.event.plugin;

import me.wowkfccc.logplayeraction.logPlayerAction_paper.event.plugin.API.EssentialsHook;
import net.ess3.api.events.AfkStatusChangeEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import me.wowkfccc.logplayeraction.logPlayerAction_paper.LogPlayerAction_paper;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class onEssentialsAFK implements Listener {
    private final LogPlayerAction_paper plugin;
    private final EssentialsHook hook;

    // 記錄玩家進入 AFK 的時間戳 (用 long 才不會溢位)
    private final Map<UUID, Long> afkStartTime = new HashMap<>();
    // 累積每位玩家的 AFK 時數（秒）
    public static final Map<UUID, Integer> afkTotalSeconds = new HashMap<>();

    public onEssentialsAFK(LogPlayerAction_paper plugin, EssentialsHook essentialsHook) {
        this.plugin = plugin;
        this.hook   = essentialsHook;
    }

    @EventHandler
    public void onAfkStatusChange(AfkStatusChangeEvent event) {
        Player player = event.getAffected().getBase();
        UUID uuid = player.getUniqueId();
        boolean isAfk = event.getValue();

        if (isAfk) {
            afkStartTime.put(uuid, System.currentTimeMillis());
        } else if (afkStartTime.containsKey(uuid)) {
            long start = afkStartTime.remove(uuid);
            int durationSec = (int)((System.currentTimeMillis() - start) / 1000);
            afkTotalSeconds.put(uuid, afkTotalSeconds.getOrDefault(uuid, 0) + durationSec);
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
