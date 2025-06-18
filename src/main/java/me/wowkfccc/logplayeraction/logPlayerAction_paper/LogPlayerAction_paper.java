package me.wowkfccc.logplayeraction.logPlayerAction_paper;

//import com.earth2me.essentials.Essentials;
//import com.earth2me.essentials.api.Economy;
import com.destroystokyo.paper.utils.PaperPluginLogger;
import me.wowkfccc.logplayeraction.logPlayerAction_paper.event.plugin.onEssentialsAFK;
import me.wowkfccc.logplayeraction.logplayeraction.event.plugin.API.EssentialsHook;
//import me.wowkfccc.logplayeraction.logplayeraction.event.plugin.onEssentialsAFK;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import me.wowkfccc.logplayeraction.logPlayerAction_paper.event.*;
import me.wowkfccc.logplayeraction.logPlayerAction_paper.commandmanager;
//import io.papermc.paper.plugin.PaperPlugin;
import java.awt.print.Paper;
import java.util.List;
import com.earth2me.essentials.Essentials;
import net.ess3.api.events.AfkStatusChangeEvent;

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

            getServer().getPluginManager().registerEvents(actionListener, this);
            this.eventListener();
            sessionListener.cancelAllTasks();

//            if(getConfig().getBoolean("Enable.AFK", false)) {
//                try {
//                    essentialsHook = new EssentialsHook(this);
//                    getLogger().info("✔ 已透過反射 Hook Essentials v" +
//                            essentialsHook.getClass().getPackage().getImplementationVersion());
//                    getServer().getPluginManager().registerEvents(
//                            new onEssentialsAFK(this, essentialsHook),
//                            this
//                    );
//                    getLogger().info("✔ 已註冊 AFK 計數監聽器");
//                } catch (ClassNotFoundException e) {
//                    getLogger().info("Essentials 不存在，跳過經濟整合");
//                } catch (Exception e) {
//                    getLogger().warning("Essentials Hook 失敗：" + e.getMessage());
//                }
//            }
//            if (getConfig().getBoolean("Enable.AFK", false)) {
//                getServer().getGlobalRegionScheduler().runDelayed(this, task -> {
//                    try {
//                        // 確保 Essentials 插件存在
//                        if (getServer().getPluginManager().getPlugin("EssentialsX") == null ||
//                                !getServer().getPluginManager().getPlugin("EssentialsX").isEnabled()) {
//                            getLogger().info("EssentialsX 尚未啟用，跳過 AFK 功能");
//                            return;
//                        }
//
//                        // 僅在這裡動態載入該 class
//                        Class<?> afkClass = Class.forName("net.ess3.api.events.AfkStatusChangeEvent");
//
//                        // 所有引用 onEssentialsAFK 的動作也放在這裡，確保延遲觸發
//                        essentialsHook = new EssentialsHook(this);
//                        Object afkListener = Class.forName("me.wowkfccc.logplayeraction.logPlayerAction_paper.event.plugin.onEssentialsAFK")
//                                .getConstructor(JavaPlugin.class, EssentialsHook.class)
//                                .newInstance(this, essentialsHook);
//
//                        getServer().getPluginManager().registerEvents((org.bukkit.event.Listener) afkListener, this);
//                        getLogger().info("✔ 成功註冊 AFK 功能");
//
//                    } catch (ClassNotFoundException e) {
//                        getLogger().warning("找不到 Essentials AFK 類別，跳過");
//                    } catch (Throwable e) {
//                        getLogger().warning("AFK 模組初始化失敗：" + e.getMessage());
//                    }
//                }, 20L);
//            }
            PluginManager pm = getServer().getPluginManager();
            boolean afkEnabled = getConfig().getBoolean("Enable.AFK", false);
            boolean hasEssentials = pm.isPluginEnabled("Essentials") || pm.isPluginEnabled("EssentialsX");
            getLogger().info("讀到 Enable.AFK = " + afkEnabled);
            getLogger().info("Essentials loaded? Essentials="
                    + pm.isPluginEnabled("Essentials")
                    + ", EssentialsX=" + pm.isPluginEnabled("EssentialsX"));

            if (afkEnabled && hasEssentials) {
                try {
                    EssentialsHook hook = new EssentialsHook(this);
                    onEssentialsAFK listener = new onEssentialsAFK(this, hook);
                    pm.registerEvents(listener, this);
                    getLogger().info("✔ 已註冊 AFK 監聽器");
                } catch (Exception e) {
                    getLogger().warning("⚠ AFK 模組初始化失敗：" + e.getMessage());
                }
            } else {
                getLogger().info("⚠ 跳過 AFK 功能註冊");
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

}
