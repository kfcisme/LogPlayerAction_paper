package me.wowkfccc.logplayeraction.logPlayerAction_paper;

import me.wowkfccc.logplayeraction.logPlayerAction_paper.event.plugin.onEssentialsAFK;
import org.bukkit.plugin.java.JavaPlugin;
import me.wowkfccc.logplayeraction.logPlayerAction_paper.event.*;


public final class LogPlayerAction_paper extends JavaPlugin {


        private boolean databaseEnable;
        private me.wowkfccc.logplayeraction.logPlayerAction_paper.event.plugin.API.EssentialsHook essentialsHook;
        private boolean Essentials;
        private mySQLConnect mySQL;
        private boolean AFKEnable;
        //private final PlayerActionListener actionListener = new PlayerActionListener();
        private PlayerActionListener actionListener;
        private mySQLInsertData mySQLInsert;
//        private Economy economy;
//        private Essentials essentials;
        private static me.wowkfccc.logplayeraction.logPlayerAction_paper.LogPlayerAction_paper instance;
//        private com.earth2me.essentials.api.Economy essEconomy;

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
            boolean hasEssentials = getServer().getPluginManager().getPlugin("Essentials") != null;
            getLogger().info("Essentials 是否啟用: " + hasEssentials);
            boolean afkEnabled = getConfig().getBoolean("Enable.AFK", false);
            if (getConfig().getBoolean("Enable.AFK", false) && hasEssentials) {
                getServer().getPluginManager().registerEvents(
                        new onEssentialsAFK(this, essentialsHook),
                        this
                );

                getLogger().info("✔ 已註冊 AFK 計數監聽器");
            }
//            if (afkEnabled && hasEssentials) {
//                try {
//                    EssentialsHook hook = new EssentialsHook(this);
//                    onEssentialsAFK listener = new onEssentialsAFK(this, hook);
//                    pm.registerEvents(listener, this);
//                    getLogger().info("✔ register AFK listener");
//                } catch (Exception e) {
//                    getLogger().warning("⚠ AFK 模組初始化失敗：" + e.getMessage());
//                }
//            } else {
//                getLogger().info("⚠ skip EssentialsX AFK ");
//            }
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

        //public static Logplayeraction getInstance() {
        //return JavaPlugin.getPlugin(Logplayeraction.class);
        //}
        public static me.wowkfccc.logplayeraction.logPlayerAction_paper.LogPlayerAction_paper getInstance() {return JavaPlugin.getPlugin(me.wowkfccc.logplayeraction.logPlayerAction_paper.LogPlayerAction_paper.class);}
        private void commcand() {
        }

        public boolean isDatabaseEnable() {
            return databaseEnable;
        }

//        public Object getEssEconomy() {
//            if (essentialsHook == null) return null;
//            try {
//                return essentialsHook.getEconomy();
//            } catch (Exception e) {
//                getLogger().warning("取 Essentials Economy 失敗：" + e.getMessage());
//                return null;
//            }
//        }

}
