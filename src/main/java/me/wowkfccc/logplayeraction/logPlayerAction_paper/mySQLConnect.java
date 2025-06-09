//package me.wowkfccc.logplayeraction.logplayeraction;
//
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.SQLException;
//
////public class mySQLConnect {
////    private final Logplayeraction plugin;
////    private Connection connection;
////
////    private String host = "localhost";
////    private String database = "your_database";
////    private String username = "your_username";
////    private String password = "your_password";
////    private int port = 3306;
////
////    public mySQLConnect(Logplayeraction plugin) {
////        this.plugin = plugin;
////        host = plugin.getConfig().getString("database.host", "localhost");
////        database = plugin.getConfig().getString("database.database");
////        username = plugin.getConfig().getString("database.username", "user");
////        password = plugin.getConfig().getString("database.password", "password");
////        port = plugin.getConfig().getInt("database.port", 3306);
////    }
////
////    public void connect() {
////        if (!isConnected()) {
////            try {
////                connection = DriverManager.getConnection(
////                        "jdbc:mysql://" + host + ":" + port + "/" + database + "?useSSL=false&autoReconnect=true",
////                        username,
////                        password
////                );
////                System.out.println("已連接至 MySQL 資料庫");
////            } catch (SQLException e) {
////                e.printStackTrace();
////            }
////        }
////    }
////
////    public void disconnect() {
////        if (isConnected()) {
////            try {
////                connection.close();
////                System.out.println("已關閉 MySQL 連線！");
////            } catch (SQLException e) {
////                e.printStackTrace();
////            }
////        }
////    }
////
////    public boolean isConnected() {
////        try {
////            return connection != null && !connection.isClosed();
////        } catch (SQLException e) {
////            e.printStackTrace();
////            return false;
////        }
////    }
////    public Connection getConnection() {
////        return connection;
////    }
////}
//import com.zaxxer.hikari.HikariConfig;
//import com.zaxxer.hikari.HikariDataSource;
//import java.sql.Connection;
//import java.sql.SQLException;
//
//public class mySQLConnect {
//    private final Logplayeraction plugin;
//    private HikariDataSource ds;
//
//    public mySQLConnect(Logplayeraction plugin) {
//        this.plugin = plugin;
//    }
//
//    public void connect() {
//        if (ds != null && !ds.isClosed()) return;
//
//        HikariConfig cfg = new HikariConfig();
//        cfg.setJdbcUrl(String.format(
//                "jdbc:mysql://%s:%d/%s?useSSL=false",
//                plugin.getConfig().getString("database.host"),
//                plugin.getConfig().getInt("database.port"),
//                plugin.getConfig().getString("database.database")
//        ));
//        cfg.setUsername(plugin.getConfig().getString("database.username"));
//        cfg.setPassword(plugin.getConfig().getString("database.password"));
//
//        // 保活與自動重連設定
//        cfg.setMaximumPoolSize(10);
//        cfg.setConnectionTimeout(30000);
//        cfg.setIdleTimeout(600000);
//        cfg.setMaxLifetime(1800000);
//        cfg.setValidationTimeout(5000);
//        cfg.setLeakDetectionThreshold(60_000);
//        cfg.addDataSourceProperty("cachePrepStmts", "true");
//        cfg.addDataSourceProperty("prepStmtCacheSize", "250");
//        cfg.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
//
//        ds = new HikariDataSource(cfg);
//        plugin.getLogger().info("HikariCP pool created");
//    }
//
//    public Connection getConnection() throws SQLException {
//        if (ds == null || ds.isClosed()) {
//            connect();
//        }
//        return ds.getConnection();
//    }
//
//    public boolean isConnected() {
//        if (ds == null || ds.isClosed()) {
//            return false;
//        }
//        try (Connection conn = ds.getConnection()) {
//            return conn.isValid(2);
//        } catch (SQLException e) {
//            return false;
//        }
//    }
//
//    public void disconnect() {
//        if (ds != null && !ds.isClosed()) {
//            ds.close();
//            plugin.getLogger().info("HikariCP pool closed");
//        }
//    }
//}
package me.wowkfccc.logplayeraction.logPlayerAction_paper;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class mySQLConnect {
    private final LogPlayerAction_paper plugin;
    private HikariDataSource ds;

    public mySQLConnect(LogPlayerAction_paper plugin) {
        this.plugin = plugin;
    }

    /**
     * Initialize HikariCP pool. Automatically includes public key retrieval.
     */
    public void connect() {
        if (ds != null && !ds.isClosed()) {
            return;
        }

        String host = plugin.getConfig().getString("database.host", "localhost");
        int port = plugin.getConfig().getInt("database.port", 3306);
        String database = plugin.getConfig().getString("database.database");
        String user = plugin.getConfig().getString("database.username");
        String pass = plugin.getConfig().getString("database.password");
        boolean useSSL = plugin.getConfig().getBoolean("SSL.enable", false);

        String jdbcUrl = String.format(
                "jdbc:mysql://%s:%d/%s?useSSL=%b&allowPublicKeyRetrieval=true&serverTimezone=UTC",
                host, port, database, useSSL
        );

        HikariConfig cfg = new HikariConfig();
        cfg.setJdbcUrl(jdbcUrl);
        cfg.setUsername(user);
        cfg.setPassword(pass);

        // Pool size and timeout settings
        cfg.setMaximumPoolSize(10);
        cfg.setConnectionTimeout(30000);
        cfg.setIdleTimeout(600000);
        cfg.setMaxLifetime(30000);
        cfg.setValidationTimeout(5000);
        cfg.setKeepaliveTime(150000);

        ds = new HikariDataSource(cfg);
        plugin.getLogger().info("MySQL connection pool initialized");
    }

    /**
     * Get a connection from the pool, reconnecting if necessary.
     */
    public Connection getConnection() throws SQLException {
        if (ds == null || ds.isClosed()) {
            connect();
        }
        return ds.getConnection();
    }

    /**
     * Check pool connectivity without leaking a connection.
     */
    public boolean isConnected() {
        if (ds == null || ds.isClosed()) {
            return false;
        }
        try (Connection conn = ds.getConnection()) {
            return conn != null && conn.isValid(2);
        } catch (SQLException e) {
            return false;
        }
    }

    /**
     * Close the pool when plugin disables.
     */
    public void disconnect() {
        if (ds != null && !ds.isClosed()) {
            ds.close();
            plugin.getLogger().info("MySQL connection pool close");
        }
    }
}
