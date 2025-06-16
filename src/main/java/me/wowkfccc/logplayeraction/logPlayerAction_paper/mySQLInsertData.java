package me.wowkfccc.logplayeraction.logPlayerAction_paper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import java.sql.*;
import java.util.UUID;

import me.wowkfccc.logplayeraction.logPlayerAction_paper.event.PlayerActionListener;
public class mySQLInsertData {
    private final mySQLConnect mySQL;
    private final LogPlayerAction_paper plugin = LogPlayerAction_paper.getInstance();
    public mySQLInsertData(mySQLConnect mySQL) {
        this.mySQL = mySQL;
    }

    public void createPlayerTable(String tableName) {
        if (!mySQL.isConnected()) return;

        String sql = "CREATE TABLE IF NOT EXISTS `" + tableName + "` ("
                + "`record_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP, "
                + "`pickup` INT NOT NULL DEFAULT 0, "
                + "`block_break` INT NOT NULL DEFAULT 0, "
                + "`tnt_prime` INT NOT NULL DEFAULT 0, "
                + "`multi_place` INT NOT NULL DEFAULT 0, "
                + "`chat` INT NOT NULL DEFAULT 0, "
                + "`block_damage` INT NOT NULL DEFAULT 0, "
                + "`block_place` INT NOT NULL DEFAULT 0, "
                + "`craft` INT NOT NULL DEFAULT 0, "
                + "`dmg_by_entity` INT NOT NULL DEFAULT 0, "
                + "`death` INT NOT NULL DEFAULT 0, "
                + "`explosion` INT NOT NULL DEFAULT 0, "
                + "`furnace_extract` INT NOT NULL DEFAULT 0, "
                + "`inv_close` INT NOT NULL DEFAULT 0, "
                + "`inv_open` INT NOT NULL DEFAULT 0, "
                + "`bucket_empty` INT NOT NULL DEFAULT 0, "
                + "`bucket_fill` INT NOT NULL DEFAULT 0, "
                + "`cmd_pre` INT NOT NULL DEFAULT 0, "
                + "`cmd_send` INT NOT NULL DEFAULT 0, "
                + "`player_death` INT NOT NULL DEFAULT 0, "
                + "`item_drop` INT NOT NULL DEFAULT 0, "
                + "`exp_change` INT NOT NULL DEFAULT 0, "
                + "`interact` INT NOT NULL DEFAULT 0, "
                + "`level_change` INT NOT NULL DEFAULT 0, "
                + "`quit` INT NOT NULL DEFAULT 0, "
                + "`respawn` INT NOT NULL DEFAULT 0, "
                + "`teleport` INT NOT NULL DEFAULT 0, "
                + "`ChunkLoad` INT NOT NULL DEFAULT 0, "
                + "`redstone` INT NOT NULL DEFAULT 0, "
                + "`AFKtime` INT NOT NULL DEFAULT 0, "
                + "PRIMARY KEY (`record_time`)"
                + ")";
        plugin.getLogger().info("Creating table: " + tableName);
        try (Connection conn = mySQL.getConnection();
//             Statement stmt = conn.createStatement()) {
//            stmt.executeUpdate(sql);
//        }
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.execute();
            plugin.getLogger().info("[logplayeraction] Creating table: " + tableName);}
        catch (SQLException e) {
            plugin.getLogger().warning("[logplayeraction] 建表失敗：" + e.getMessage());
        }
    }

    public void insertEventCounts(UUID uuid, PlayerActionListener.EventCounts c) {
        String tableName = "player_" + uuid.toString().replace("-", "");
//        if (!mySQL.isConnected()) return;
//        createPlayerTable(tableName);
        plugin.getLogger().info("⚙ insertEventCounts(): 表格=" + tableName + "，counts=" + c);
        if (!mySQL.isConnected()) {
            plugin.getLogger().warning("⚠ MySQL 未連線，跳過插入");
            return;
        }
        createPlayerTable(tableName);

        String sql = "INSERT INTO `" + tableName + "` ("
                + "pickup, block_break, tnt_prime, multi_place, chat, block_damage, "
                + "block_place, craft, dmg_by_entity, death, explosion, furnace_extract, "
                + "inv_close, inv_open, bucket_empty, bucket_fill, cmd_pre, cmd_send, "
                + "player_death, item_drop, exp_change, interact, level_change, quit, "
                + "respawn, teleport, ChunkLoad, redstone, AFKtime"
                + ") VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
//        plugin.getLogger().info("database insert success");
        try (Connection conn = mySQL.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1,  c.pickup);
            ps.setInt(2,  c.blockBreak);
            ps.setInt(3,  c.tntPrime);
            ps.setInt(4,  c.multiPlace);
            ps.setInt(5,  c.chat);
            ps.setInt(6,  c.blockDamage);
            ps.setInt(7,  c.blockPlace);
            ps.setInt(8,  c.craft);
            ps.setInt(9,  c.dmgByEntity);
            ps.setInt(10, c.death);
            ps.setInt(11, c.explosion);
            ps.setInt(12, c.furnaceExtract);
            ps.setInt(13, c.invClose);
            ps.setInt(14, c.invOpen);
            ps.setInt(15, c.bucketEmpty);
            ps.setInt(16, c.bucketFill);
            ps.setInt(17, c.cmdPre);
            ps.setInt(18, c.cmdSend);
            ps.setInt(19, c.playerDeath);
            ps.setInt(20, c.itemDrop);
            ps.setInt(21, c.expChange);
            ps.setInt(22, c.interact);
            ps.setInt(23, c.levelChange);
            ps.setInt(24, c.quit);
            ps.setInt(25, c.respawn);
            ps.setInt(26, c.teleport);
            ps.setInt(27, c.chunkLoadCounts);
            ps.setInt(28, c.redstoneCounts);
            ps.setInt(29, c.afktime);
            int rows = ps.executeUpdate();  // 真正去執行
                plugin.getLogger().info("✅ 資料庫插入成功，重置玩家計數器：" + uuid);
                // 用正確的變數呼叫
                PlayerActionListener.ResetCounters(uuid);

        } catch (SQLException e) {
            plugin.getLogger().severe("❌ insertEventCounts SQLException：" + e.getMessage());
            e.printStackTrace();
        }



    }
}
