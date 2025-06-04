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


import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerSessionListener implements Listener {
    private final LogPlayerAction_paper plugin;
    private final PlayerActionListener actionListener;
    private final me.wowkfccc.logplayeraction.logPlayerAction_paper.mySQLInsertData mySQLInsert;
    private final Map<UUID, PlayerActionListener.EventCounts> sessionCounts = new HashMap<>();
    private final Map<UUID, BukkitTask> sessionTasks = new HashMap<>();
    private final int sessionSeconds;

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
        if (sessionTasks.containsKey(id)) {
            sessionTasks.get(id).cancel();
            sessionTasks.remove(id);
        }
        // 每隔 sessionSeconds 秒，抓取並重置計數，寫入資料庫
//        BukkitTask task = plugin.getServer().getScheduler().runTaskTimer(
//                plugin,
//                () -> {
//                    PlayerActionListener.EventCounts counts = actionListener.getAndResetCounts(id);
//                    // 檢查至少有一項 > 0，才寫入
//                    if (plugin.isDatabaseEnable() && counts.pickup + counts.blockBreak + counts.chat + counts.blockDamage + counts.blockPlace + counts.bucketFill + counts.bucketEmpty > 0) {
//                        plugin.getLogger().info(
//                                "正在為玩家 " + p.getName() + " 寫入行為資料："
//                                        + "break=" + counts.blockBreak
//                                        + ", place=" + counts.blockPlace
//                                        + " … 共" + (
//                                        counts.pickup + counts.blockBreak + counts.blockPlace /*…*/
//                                ) + " 項");
//                        try {
//                            mySQLInsert.insertEventCounts(id, counts);
//                        } catch (Exception ex) {
//                            plugin.getLogger().warning("寫入資料庫失敗，請檢查資料庫連線設定");
//                            ex.printStackTrace();
//                        }
////                        //mySQLInsert.insertEventCounts(table, counts);
//                    }
//                },
//                sessionSeconds * 20L,
//                sessionSeconds * 20L
////        );
//        sessionTasks.put(id, task);
        startSchedule(e.getPlayer());
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        UUID id = e.getPlayer().getUniqueId();
        BukkitTask task = sessionTasks.remove(id);
        if (task != null) task.cancel();
        // 清除剩餘計數，避免下次登入誤用
        actionListener.getAndResetCounts(id);
        resetTimer(e.getPlayer().getUniqueId());
    }
    public void resetTimer(UUID id) {
        // 取消舊任務
        BukkitTask old = sessionTasks.remove(id);
        if (old != null) old.cancel();

        // 清空累積次數
        PlayerActionListener.ResetCounters(id);

        // 直接重用 join 時的排程邏輯
        Player p = plugin.getServer().getPlayer(id);
        if (p != null && p.isOnline()) {
            startSchedule(p);
        }
    }


    public boolean hasTimer(UUID id) {
        return sessionTasks.containsKey(id);
    }
    private void startSchedule(Player p) {
        UUID id = p.getUniqueId();
        String table = "player_" + id.toString().replace("-", "");
        mySQLInsert.createPlayerTable(table);

        BukkitTask task = plugin.getServer().getScheduler().runTaskTimer(
                plugin,
                () -> {
                    PlayerActionListener.EventCounts counts =
                            actionListener.getAndResetCounts(id);
                    if (plugin.isDatabaseEnable()
                            && counts.pickup + counts.blockBreak + counts.chat
                            + counts.blockDamage + counts.blockPlace
                            + counts.bucketFill + counts.bucketEmpty > 0) {

                        mySQLInsert.insertEventCounts(id, counts);
                    }
                },
                sessionSeconds * 20L,
                sessionSeconds * 20L
        );
        sessionTasks.put(id, task);
//        startSchedule(e.getPlayer());
    }
    public void cancelAllTasks() {
        for (BukkitTask task : sessionTasks.values()) {
            task.cancel();
        }
        sessionTasks.clear();
    }
}
