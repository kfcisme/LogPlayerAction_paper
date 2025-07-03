package me.wowkfccc.logplayeraction.logPlayerAction_paper.event;

import me.wowkfccc.logplayeraction.logPlayerAction_paper.LogPlayerAction_paper;
import me.wowkfccc.logplayeraction.logPlayerAction_paper.event.*;
import me.wowkfccc.logplayeraction.logPlayerAction_paper.LogPlayerAction_paper;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitTask;

import org.jetbrains.annotations.Async;
import me.wowkfccc.logplayeraction.logPlayerAction_paper.PlatformScheduler;
import io.papermc.paper.threadedregions.scheduler.ScheduledTask;
import io.papermc.paper.threadedregions.scheduler.*;
import java.time.Duration;



import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerSessionListener implements Listener {
    private final LogPlayerAction_paper plugin;
    private final PlayerActionListener actionListener;
    private final me.wowkfccc.logplayeraction.logPlayerAction_paper.mySQLInsertData mySQLInsert;
    private final Map<UUID, PlayerActionListener.EventCounts> sessionCounts = new HashMap<>();
    private final Map<UUID, BukkitTask> sessionTasks = new HashMap<>();
    private final Map<UUID, ScheduledTask> foliaSessionTasks = new HashMap<>();
    private final int sessionSeconds;
    private io.papermc.paper.threadedregions.scheduler.ScheduledTask ScheduledTask;

    public PlayerSessionListener(
            LogPlayerAction_paper plugin,
            PlayerActionListener actionListener,
            me.wowkfccc.logplayeraction.logPlayerAction_paper.mySQLInsertData mySQLInsert
    ) {
        this.plugin = plugin;
        this.actionListener = actionListener;
        this.mySQLInsert = mySQLInsert;
        this.sessionSeconds = plugin.getConfig().getInt("database.insert_interval", 300);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        UUID id = p.getUniqueId();

        // 先建立表格（如果尚未建立）
        String table = "player_" + id.toString().replace("-", "");
        mySQLInsert.createPlayerTable(table);



            plugin.getLogger().info("You are using folia, using ScheduledTask");
            if (foliaSessionTasks.containsKey(id)) {
                foliaSessionTasks.get(id).cancel();
                foliaSessionTasks.remove(id);
            }
        ScheduledTask old = foliaSessionTasks.remove(id);
        if (old != null) old.cancel();
        GlobalRegionScheduler globalScheduler = plugin.getServer().getGlobalRegionScheduler();

        // 3. 用 runAtFixedRate() 回傳的一定是 ScheduledTask
        ScheduledTask newTask = globalScheduler.runAtFixedRate(
                plugin,
                (ScheduledTask task) -> {
                    PlayerActionListener.EventCounts counts = actionListener.getAndResetCounts(id);
                    if (plugin.isDatabaseEnable() &&
                            (counts.pickup + counts.blockBreak + counts.chat
                                    + counts.blockDamage + counts.blockPlace
                                    + counts.bucketFill + counts.bucketEmpty + counts.afktime + counts.redstoneCounts
                                    + counts.chunkLoadCounts + counts.cmdPre + counts.craft  + counts.death
                                    + counts.dmgByEntity + counts.expChange + counts.explosion + counts.furnaceExtract + counts.interact
                                    + counts.cmdSend + counts.invClose + counts.invOpen + counts.itemDrop + counts.levelChange
                                    + counts.multiPlace + counts.playerDeath + counts.quit + counts.respawn
                                    + counts.teleport + counts.tntPrime) > 0) {

                       plugin.getLogger().info("正在為玩家 " + p.getName() + " 寫入行為資料，共 " + counts.pickup + counts.blockBreak + counts.chat
                                   + counts.blockDamage + counts.blockPlace + counts.bucketFill + counts.bucketEmpty + " 項");
                        try {
                            mySQLInsert.insertEventCounts(id, counts);
                        } catch (Exception ex) {
                            plugin.getLogger().warning("寫入資料庫失敗，請檢查資料庫連線設定");
                            ex.printStackTrace();
                        }
                    }
                },
                sessionSeconds* 20L,
                sessionSeconds * 20L
        );
        foliaSessionTasks.put(id, newTask);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        UUID id = e.getPlayer().getUniqueId();
            ScheduledTask foliatask = foliaSessionTasks.remove(id);
            if (foliatask != null) foliatask.cancel();
            actionListener.getAndResetCounts(id);
            resetTimer(e.getPlayer().getUniqueId());
        // }
    }

    public void resetTimer(UUID id) {

            ScheduledTask foliaold = foliaSessionTasks.remove(id);
            if (foliaold != null) foliaold.cancel();

            // 清空累積次數
            PlayerActionListener.ResetCounters(id);

            // 直接重用 join 時的排程邏輯
            Player p = plugin.getServer().getPlayer(id);
            if (p != null && p.isOnline()) {
                startSchedule(p);
            }
        //}
    }

    public boolean hasTimer(UUID id) {
        return sessionTasks.containsKey(id);
    }
    private void startSchedule(Player p) {
        UUID id = p.getUniqueId();
        String table = "player_" + id.toString().replace("-", "");
        mySQLInsert.createPlayerTable(table);
    }
    public void cancelAllTasks() {
//        if (!PlatformScheduler.IS_FOLIA) {
//            for (BukkitTask task : sessionTasks.values()) {
//                task.cancel();
//            }
//            sessionTasks.clear();
//        } else {
            for (ScheduledTask foliatask : foliaSessionTasks.values()) {
                foliatask.cancel();
            }
        //}
    }
}
