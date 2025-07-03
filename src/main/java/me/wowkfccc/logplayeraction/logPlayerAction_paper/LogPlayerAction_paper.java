package me.wowkfccc.logplayeraction.logPlayerAction_paper;

import me.wowkfccc.logplayeraction.logPlayerAction_paper.event.plugin.API.AFKActivityListener;
import me.wowkfccc.logplayeraction.logPlayerAction_paper.event.plugin.API.AFKManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import me.wowkfccc.logplayeraction.logPlayerAction_paper.event.*;
import me.wowkfccc.logplayeraction.logPlayerAction_paper.event.plugin.*;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;


public final class LogPlayerAction_paper extends JavaPlugin {


        private boolean databaseEnable;
        private boolean Essentials;
        private mySQLConnect mySQL;
        private boolean AFKEnable;
        private PlayerActionListener actionListener;
        private mySQLInsertData mySQLInsert;
        private static me.wowkfccc.logplayeraction.logPlayerAction_paper.LogPlayerAction_paper instance;

    private final Map<UUID, Integer> afkCounts = new HashMap<>();
    // 記錄玩家進入 AFK 的時間戳（毫秒）
    private final Map<UUID, Integer> afkStartTime = new HashMap<>();

    // 累積每位玩家的 AFK 時數（秒）
    public static final Map<UUID, Integer> afkTotalSeconds = new HashMap<>();
        @Override
        public void onEnable() {
            instance = this;
            this.saveDefaultConfig();
            this.commcand();

            // Database connection
            databaseEnable = getConfig().getBoolean("database.enable", false);
            if (databaseEnable) {
                mySQL = new mySQLConnect(this);
                mySQL.connect();
            }

            // Initialize mySQLInsertData
            mySQLInsert = new mySQLInsertData(mySQL);
            actionListener = new PlayerActionListener(this);
            // Command executor
            PlayerActionListener actionListener = new PlayerActionListener(this);
            PlayerSessionListener sessionListener =
                    new PlayerSessionListener(this, actionListener, mySQLInsert);
            // registerCommands(sessionListener);

            getServer().getPluginManager().registerEvents(actionListener, this);
            getServer().getPluginManager().registerEvents(sessionListener, this);

            //  /logplayeraction command
            me.wowkfccc.logplayeraction.logPlayerAction_paper.commandmanager cmdMgr = new me.wowkfccc.logplayeraction.logPlayerAction_paper.commandmanager(this, sessionListener);
//            getCommand("logplayeraction").setExecutor(cmdMgr);
            //getCommand("logplayeraction").setExecutor(new commandmanager(this, sessionListener));


            getLogger().info("Logplayeraction 啟動完成！");

            getServer().getPluginManager().registerEvents(actionListener, this);
            this.eventListener();
            sessionListener.cancelAllTasks();
            getServer().getPluginManager().registerEvents(new AFKActivityListener(), this);
                if (getConfig().getBoolean("Enable.AFK", false) ) {
//                    getServer().getGlobalRegionScheduler().runAtFixedRate(
//                            this,
//                            task -> onEssentialsAFK.tickAll(),
//                            20L, 20L // 延遲1秒啟動，每秒執行一次
//                    ); // 每秒跑一次
//                    getServer().getPluginManager().registerEvents(new AFKActivityListener(), this);
                    getServer().getGlobalRegionScheduler().runAtFixedRate(
                            this,
                            task ->
                                AFKManager.checkAllAfk()
                            ,
                            2L,20L
                    );

                    // 註冊行為監聽器
                    getServer().getPluginManager().registerEvents(new onEssentialsAFK(), this);
                }

        }

        @Override
        public void onDisable() {
            // Plugin shutdown logic
            if (databaseEnable && mySQL.isConnected()) {
                mySQL.disconnect();
            }

        }

        private void eventListener() {
            this.getServer().getPluginManager().registerEvents(new onEntityPickupItem(this), this);
            this.getServer().getPluginManager().registerEvents(new onBlockBreak(this), this);
            this.getServer().getPluginManager().registerEvents(new onTNTPrime(this), this);
            this.getServer().getPluginManager().registerEvents(new onBlockMultiPlace(this), this);
            this.getServer().getPluginManager().registerEvents(new onAsyncPlayerChat(this), this);
            //this.getServer().getPluginManager().registerEvents(new onBlockBurn(this), this);
            //this.getServer().getPluginManager().registerEvents(new onBlockCanBuild(this), this);
            this.getServer().getPluginManager().registerEvents(new onBlockDamage(this), this);
            //this.getServer().getPluginManager().registerEvents(new onBlockPhysics(this), this);
            this.getServer().getPluginManager().registerEvents(new onBlockPlace(this), this);
            //this.getServer().getPluginManager().registerEvents(new onBlockRedstone(this), this);
            this.getServer().getPluginManager().registerEvents(new onCraftItem(this), this);
            //this.getServer().getPluginManager().registerEvents(new onEntityDamage(this), this);
            this.getServer().getPluginManager().registerEvents(new onEntityDamageByEntity(this), this);
            this.getServer().getPluginManager().registerEvents(new onEntityDeath(this), this);
            this.getServer().getPluginManager().registerEvents(new onExplosionPrime(this), this);
            //this.getServer().getPluginManager().registerEvents(new onFurnaceBurn(this), this);
            this.getServer().getPluginManager().registerEvents(new onFurnaceExtract(this), this);
            //this.getServer().getPluginManager().registerEvents(new onFurnaceSmelt(this), this);
            this.getServer().getPluginManager().registerEvents(new onInventoryClose(this), this);
            this.getServer().getPluginManager().registerEvents(new onInventoryOpen(this), this);
            this.getServer().getPluginManager().registerEvents(new onPlayerBucketEmpty(this), this);
            this.getServer().getPluginManager().registerEvents(new onPlayerBucketFill(this), this);
            this.getServer().getPluginManager().registerEvents(new onPlayerCommandPreprocess(this), this);
            this.getServer().getPluginManager().registerEvents(new onPlayerCommandSend(this), this);
            this.getServer().getPluginManager().registerEvents(new onPlayerDeath(this), this);
            this.getServer().getPluginManager().registerEvents(new onPlayerDropItem(this), this);
            this.getServer().getPluginManager().registerEvents(new onPlayerExpChange(this), this);
            this.getServer().getPluginManager().registerEvents(new onPlayerInteract(this), this);
            this.getServer().getPluginManager().registerEvents(new onPlayerLevelChange(this), this);
            this.getServer().getPluginManager().registerEvents(new onPlayerQuit(this), this);
            this.getServer().getPluginManager().registerEvents(new onPlayerRespawn(this), this);
            this.getServer().getPluginManager().registerEvents(new onPlayerTeleport(this), this);
            this.getServer().getPluginManager().registerEvents(new PlayerChunkLoadListener(this), this);
            this.getServer().getPluginManager().registerEvents(new onRedstoneTracker(this), this);
        }

        public static me.wowkfccc.logplayeraction.logPlayerAction_paper.LogPlayerAction_paper getInstance() {return JavaPlugin.getPlugin(me.wowkfccc.logplayeraction.logPlayerAction_paper.LogPlayerAction_paper.class);}
        private void commcand() {
        }

        public boolean isDatabaseEnable() {
            return databaseEnable;
        }
}
