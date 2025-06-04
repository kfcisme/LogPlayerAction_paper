//package me.wowkfccc.logplayeraction.logplayeraction;
//
//import me.wowkfccc.logplayeraction.logplayeraction.event.PlayerActionListener;
//import org.bukkit.Bukkit;
//import org.bukkit.scheduler.BukkitRunnable;
//
//import java.util.UUID;
//
//public class ActionRecorderTask extends BukkitRunnable {
//    private final PlayerActionListener actionListener;
//    private final mySQLInsertData mySQLInsert;
//
//    public ActionRecorderTask(PlayerActionListener actionListener, mySQLInsertData mySQLInsert) {
//        this.actionListener = actionListener;
//        this.mySQLInsert = mySQLInsert;
//    }
//
//    @Override
//    public void run() {
//        Bukkit.getOnlinePlayers().forEach(player -> {
//            UUID playerId = player.getUniqueId();
//            String tableName = "player_" + playerId.toString().replace("-", "");
//
//            // 1. 取得所有事件計數並重置
//            PlayerActionListener.EventCounts counts =
//                    actionListener.getAndResetCounts(playerId);
//
//            // 2. 呼叫新的多欄位寫入方法
//            mySQLInsert.insertEventCounts(tableName, counts);
//        });
//    }
//}
