package me.wowkfccc.logplayeraction.logPlayerAction_paper.event.plugin;

//import me.wowkfccc.logplayeraction.logPlayerAction_paper.event.plugin.API.EssentialsHook;
//import net.ess3.api.events.AfkStatusChangeEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import me.wowkfccc.logplayeraction.logPlayerAction_paper.LogPlayerAction_paper;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class onEssentialsAFK implements Listener {
    private final LogPlayerAction_paper plugin;
//    private final EssentialsHook hook;

    // 記錄玩家進入 AFK 的時間戳 (用 long 才不會溢位)
    private final Map<UUID, Long> afkStartTime = new HashMap<>();
    // 累積每位玩家的 AFK 時數（秒）
    public static final Map<UUID, Integer> afkTotalSeconds = new HashMap<>();

    public onEssentialsAFK(LogPlayerAction_paper plugin) {
        this.plugin = plugin;
//        this.hook   = essentialsHook;
    }

    public void onAfkStatusChange(LogPlayerAction_paper plugin) {
        Plugin essentials = plugin.getServer().getPluginManager().getPlugin("Essentials");
        try {
            Class<? extends Event> afkClass =
                    (Class<? extends Event>) Class.forName("net.ess3.api.events.AfkStatusChangeEvent",
                            true,
                            essentials.getClass().getClassLoader());
            Method getValue = afkClass.getMethod("getValue");
            Method getAffected = afkClass.getMethod("getAffected");

            PluginManager pm = Bukkit.getPluginManager();
            Listener dummyListener = new Listener() {};

            pm.registerEvent(
                    afkClass,
                    dummyListener,
                    EventPriority.MONITOR,
                    (listener, event) -> {
                        try {
                            Object affected = getAffected.invoke(event);
                            Method getBase = affected.getClass().getMethod("getBase");
                            Player player = (Player) getBase.invoke(affected);
                            UUID uuid = player.getUniqueId();

                            boolean isAfk = (boolean) getValue.invoke(event);

                            if (isAfk) {
                                afkStartTime.put(uuid, System.currentTimeMillis());
//                                plugin.getLogger().info(player + "is now AFK");
                            } else if (afkStartTime.containsKey(uuid)) {
//                                plugin.getLogger().info(player + "is no longer AFK");
                                long start = afkStartTime.remove(uuid);
                                int duration = (int) ((System.currentTimeMillis() - start) / 1000);
                                afkTotalSeconds.put(uuid, afkTotalSeconds.getOrDefault(uuid, 0) + duration);
                            }
                        } catch (Exception ex) {
                            plugin.getLogger().warning("AFK 處理失敗：" + ex.getMessage());
                        }
                    },
                    plugin,
                    true
            );

            plugin.getLogger().info("✔ 成功使用反射註冊 EssentialsX AFK 事件");

        } catch (ClassNotFoundException e) {
            plugin.getLogger().warning("❌ 找不到 EssentialsX 的 AFK 事件類別，跳過註冊");
        } catch (Exception e) {
            plugin.getLogger().warning("❌ 註冊 AFK 反射事件時發生錯誤：" + e.getMessage());
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
