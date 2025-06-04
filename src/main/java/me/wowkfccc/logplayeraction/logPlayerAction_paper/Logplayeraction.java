//package me.wowkfccc.logplayeraction.logplayeraction;
//
//import com.earth2me.essentials.Essentials;
//import com.earth2me.essentials.api.Economy;
//import me.wowkfccc.logplayeraction.logplayeraction.event.plugin.API.EssentialsHook;
////import me.wowkfccc.logplayeraction.logplayeraction.event.plugin.onEssentialsAFK;
//import org.bukkit.plugin.RegisteredServiceProvider;
//import org.bukkit.plugin.java.JavaPlugin;
//import me.wowkfccc.logplayeraction.logPlayerAction_paper.event.*;
//import me.wowkfccc.logplayeraction.logPlayerAction_paper.commandmanager;
//
//public final class Logplayeraction extends JavaPlugin {
//    private boolean databaseEnable;
//    private EssentialsHook essentialsHook;
//    private boolean Essentials;
//    private mySQLConnect mySQL;
//    private boolean AFKEnable;
//    //private final PlayerActionListener actionListener = new PlayerActionListener();
//    private PlayerActionListener actionListener;
//    private mySQLInsertData mySQLInsert;
//    private Economy economy;
//    private Essentials essentials;
//    private static Logplayeraction instance;
//    private com.earth2me.essentials.api.Economy essEconomy;
//
//    @Override
//    public void onEnable() {
//        instance = this;
//        this.saveDefaultConfig();
//        this.commcand();
//
//
//
//        // Database connection
//        databaseEnable = getConfig().getBoolean("database.enable", false);
//        if (databaseEnable) {
//            mySQL = new mySQLConnect(this);
//            mySQL.connect();
//        }
//
//        // Initialize mySQLInsertData
//        mySQLInsert = new mySQLInsertData(mySQL);
//        actionListener = new PlayerActionListener(this);
//        // Command executor
//        PlayerActionListener actionListener = new PlayerActionListener(this);
//        PlayerSessionListener sessionListener =
//                new PlayerSessionListener(this, actionListener, mySQLInsert);
//
//        getServer().getPluginManager().registerEvents(actionListener, this);
//        getServer().getPluginManager().registerEvents(sessionListener, this);
//
//        //  /logplayeraction command
//        commandmanager cmdMgr = new commandmanager(this, sessionListener);
//        getCommand("logplayeraction").setExecutor(cmdMgr);
//
//        getLogger().info("Logplayeraction 啟動完成！");
////        getServer().getPluginManager().registerEvents(
////                new PlayerSessionListener(this, actionListener, mySQLInsert),
////                this
////        );
//        getServer().getPluginManager().registerEvents(actionListener, this);
//        this.eventListener();
//        sessionListener.cancelAllTasks();
//        // Start periodic task
////        int interval = getConfig().getInt("database.insert_interval", 300); // Default 300 seconds
////        new ActionRecorderTask(actionListener, mySQLInsert).runTaskTimer(this, interval * 20L, interval * 20L);
//        // --- Vault Economy Hook ---
////        RegisteredServiceProvider<Economy> rsp =
////                getServer().getServicesManager().getRegistration(Economy.class);
////        if (rsp != null) {
////            economy = rsp.getProvider();
////            //getLogger().info("Hooked Vault Economy: " + economy.getName());
////        } else {
////            getLogger().warning("Vault Economy provider not found");
////        }
////
////        // --- Essentials Hook ---
////        Essentials = getConfig().getBoolean("Enable.Balance", false);
////        if (Essentials){
////        if (getServer().getPluginManager().isPluginEnabled("Essentials")) {
////            essentials = (Essentials) getServer()
////                    .getPluginManager()
////                    .getPlugin("Essentials");
////            getLogger().info("Hooked Essentials v" +
////                    essentials.getDescription().getVersion());
////        } else {
////            getLogger().warning("Essentials not found or disabled");
////        }}
//        if (getConfig().getBoolean("Enable.Balance", false)) {
//            try {
//                essentialsHook = new EssentialsHook(this);
//                getLogger().info("✔ 已透過反射 Hook Essentials v" +
//                        essentialsHook.getClass().getPackage().getImplementationVersion());
//            } catch (ClassNotFoundException e) {
//                getLogger().info("Essentials 不存在，跳過經濟整合");
//            } catch (Exception e) {
//                getLogger().warning("Essentials Hook 失敗：" + e.getMessage());
//            }
//        }
//
//        // AFK 功能同理，只在 essentialsHook != null 時，註冊 onEssentialsAFK
////        if (getConfig().getBoolean("Enable.AFK", false) && essentialsHook != null) {
////            getServer().getPluginManager().registerEvents(
////                    new onEssentialsAFK(this, essentialsHook),
////                    this
////            );
////            getLogger().info("✔ 已註冊 AFK 計數監聽器");
////        }
//
////        getServer().getPluginManager().registerEvents(
////                new PlayerChunkLoadListener(this),
////                this
////        );
//
//
//        // Register events
//
//        //getServer().getPluginManager().registerEvents(actionListener, this);
//           }
//
//    @Override
//    public void onDisable() {
//        // Plugin shutdown logic
//        if (databaseEnable && mySQL.isConnected()) {
//            mySQL.disconnect();
//        }
//
//    }
//
//    private void eventListener() {
//        this.getServer().getPluginManager().registerEvents(new onEntityPickupItem(this), this);
//        this.getServer().getPluginManager().registerEvents(new onBlockBreak(this), this);
//        this.getServer().getPluginManager().registerEvents(new onTNTPrime(this), this);
//        this.getServer().getPluginManager().registerEvents(new onBlockMultiPlace(this), this);
//        this.getServer().getPluginManager().registerEvents(new onAsyncPlayerChat(this), this);
//        //this.getServer().getPluginManager().registerEvents(new onBlockBurn(this), this);
//        //this.getServer().getPluginManager().registerEvents(new onBlockCanBuild(this), this);
//        this.getServer().getPluginManager().registerEvents(new onBlockDamage(this), this);
//        //this.getServer().getPluginManager().registerEvents(new onBlockPhysics(this), this);
//        this.getServer().getPluginManager().registerEvents(new onBlockPlace(this), this);
//        //this.getServer().getPluginManager().registerEvents(new onBlockRedstone(this), this);
//        this.getServer().getPluginManager().registerEvents(new onCraftItem(this), this);
//        //this.getServer().getPluginManager().registerEvents(new onEntityDamage(this), this);
//        this.getServer().getPluginManager().registerEvents(new onEntityDamageByEntity(this), this);
//        this.getServer().getPluginManager().registerEvents(new onEntityDeath(this), this);
//        this.getServer().getPluginManager().registerEvents(new onExplosionPrime(this), this);
//        //this.getServer().getPluginManager().registerEvents(new onFurnaceBurn(this), this);
//        this.getServer().getPluginManager().registerEvents(new onFurnaceExtract(this), this);
//        //this.getServer().getPluginManager().registerEvents(new onFurnaceSmelt(this), this);
//        this.getServer().getPluginManager().registerEvents(new onInventoryClose(this), this);
//        this.getServer().getPluginManager().registerEvents(new onInventoryOpen(this), this);
//        this.getServer().getPluginManager().registerEvents(new onPlayerBucketEmpty(this), this);
//        this.getServer().getPluginManager().registerEvents(new onPlayerBucketFill(this), this);
//        this.getServer().getPluginManager().registerEvents(new onPlayerCommandPreprocess(this), this);
//        this.getServer().getPluginManager().registerEvents(new onPlayerCommandSend(this), this);
//        this.getServer().getPluginManager().registerEvents(new onPlayerDeath(this), this);
//        this.getServer().getPluginManager().registerEvents(new onPlayerDropItem(this), this);
//        this.getServer().getPluginManager().registerEvents(new onPlayerExpChange(this), this);
//        this.getServer().getPluginManager().registerEvents(new onPlayerInteract(this), this);
//        this.getServer().getPluginManager().registerEvents(new onPlayerLevelChange(this), this);
//        this.getServer().getPluginManager().registerEvents(new onPlayerQuit(this), this);
//        this.getServer().getPluginManager().registerEvents(new onPlayerRespawn(this), this);
//        this.getServer().getPluginManager().registerEvents(new onPlayerTeleport(this), this);
//        this.getServer().getPluginManager().registerEvents(new PlayerChunkLoadListener(this), this);
//
//    }
//
//    //public static Logplayeraction getInstance() {
//        //return JavaPlugin.getPlugin(Logplayeraction.class);
//    //}
//    public static Logplayeraction getInstance() {return JavaPlugin.getPlugin(Logplayeraction.class);}
//    private void commcand() {
//    }
//
//    public boolean isDatabaseEnable() {
//        return databaseEnable;
//    }
//    private boolean setupEconomy() {
//        if (getServer().getPluginManager().getPlugin("Vault") == null || getConfig().getBoolean("Enable.Balance", false) == false) {
//            return false;
//        }
//        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
//        if (rsp == null) {
//            return false;
//        }
//        economy = rsp.getProvider();
//        return economy != null;
//    }
//
//    public Economy getEconomy() {
//        return economy;
//    }
//    public Object getEssEconomy() {
//        if (essentialsHook == null) return null;
//        try {
//            return essentialsHook.getEconomy();
//        } catch (Exception e) {
//            getLogger().warning("取 Essentials Economy 失敗：" + e.getMessage());
//            return null;
//        }
//    }
//}