package me.wowkfccc.logplayeraction.logPlayerAction_paper;

//import com.earth2me.essentials.Essentials;
//import com.earth2me.essentials.api.Economy;
import com.destroystokyo.paper.utils.PaperPluginLogger;
import me.wowkfccc.logplayeraction.logplayeraction.event.plugin.API.EssentialsHook;
//import me.wowkfccc.logplayeraction.logplayeraction.event.plugin.onEssentialsAFK;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import me.wowkfccc.logplayeraction.logPlayerAction_paper.event.*;
import me.wowkfccc.logplayeraction.logPlayerAction_paper.commandmanager;
//import io.papermc.paper.plugin.PaperPlugin;
import java.awt.print.Paper;
import java.util.List;

public final class LogPlayerAction_paper extends JavaPlugin {


        private boolean databaseEnable;
        private me.wowkfccc.logplayeraction.logplayeraction.event.plugin.API.EssentialsHook essentialsHook;
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
//        getServer().getPluginManager().registerEvents(
//                new PlayerSessionListener(this, actionListener, mySQLInsert),
//                this
//        );
            getServer().getPluginManager().registerEvents(actionListener, this);
            this.eventListener();
            sessionListener.cancelAllTasks();

            if (getConfig().getBoolean("Enable.Balance", false)) {
                try {
                    essentialsHook = new me.wowkfccc.logplayeraction.logplayeraction.event.plugin.API.EssentialsHook(this);
                    getLogger().info("✔ 已透過反射 Hook Essentials v" +
                            essentialsHook.getClass().getPackage().getImplementationVersion());
                } catch (ClassNotFoundException e) {
                    getLogger().info("Essentials 不存在，跳過經濟整合");
                } catch (Exception e) {
                    getLogger().warning("Essentials Hook 失敗：" + e.getMessage());
                }
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

        //public static Logplayeraction getInstance() {
        //return JavaPlugin.getPlugin(Logplayeraction.class);
        //}
        public static me.wowkfccc.logplayeraction.logPlayerAction_paper.LogPlayerAction_paper getInstance() {return JavaPlugin.getPlugin(me.wowkfccc.logplayeraction.logPlayerAction_paper.LogPlayerAction_paper.class);}
        private void commcand() {
        }

        public boolean isDatabaseEnable() {
            return databaseEnable;
        }

        public Object getEssEconomy() {
            if (essentialsHook == null) return null;
            try {
                return essentialsHook.getEconomy();
            } catch (Exception e) {
                getLogger().warning("取 Essentials Economy 失敗：" + e.getMessage());
                return null;
            }
        }
//    private void registerCommands(PlayerSessionListener sessionListener) {
//        // 1. 用 registerCommand 動態建立一個 PluginCommand
//        //PluginCommand cmd = this.registerCommand("logplayeraction");
//
//        // 2. 設定執行器
//        cmd.setExecutor(new commandmanager(this, sessionListener));
//
//        // 3. (可選) 補上描述、使用說明、別名、權限等等
//        cmd.setDescription("LogPlayerAction 核心指令，用於 reload 或 time_reset");
//        cmd.setUsage("/logplayeraction reload | time_reset [player|all]");
//        cmd.setAliases(List.of("lpa"));  // 如果想加別名
//        // cmd.setPermission("logplayeraction.reload"); // 如果要綁權限也可以在這設定
//    }

}
