//package me.wowkfccc.logplayeraction.logplayeraction.event.plugin;
//
//import me.wowkfccc.logplayeraction.logplayeraction.Logplayeraction;
//import net.milkbowl.vault.economy.Economy;
//import net.milkbowl.vault.economy.
//import net.milkbowl.vault.economy.events.EconomyBalanceChangeEvent;
//import org.bukkit.Bukkit;
//import org.bukkit.entity.Player;
//import org.bukkit.event.EventHandler;
//import org.bukkit.event.Listener;
//import org.bukkit.scheduler.BukkitRunnable;
//
//import java.util.HashMap;
//import java.util.Map;
//import java.util.UUID;
//
//public class onVaultBalanceChange implements Listener {
//    private final Economy economy;
//    private final Map<UUID, Integer> balanceChangeCounts = new HashMap<>();
//
//    public onVaultBalanceChange(Logplayeraction plugin) {
//        this.economy = plugin.getEconomy();
//
//        int timer = plugin.getConfig().getInt("database.insert_interval", 3600);
//        new BukkitRunnable() {
//            @Override
//            public void run() {
//                resetCounters();
//            }
//        }.runTaskTimer(plugin, 0L, timer * 20L);
//    }
//
//    @EventHandler
//    public void onEconomyBalanceChange(EconomyBalanceChangeEvent event) {
//        // Vault 事件提供 accountName，但最常用的是 Player 版：
//        Player player = Bukkit.getPlayer(event.getAffected().getUniqueId());
//        if (player == null) return;
//
//        UUID id = player.getUniqueId();
//        balanceChangeCounts.put(id, balanceChangeCounts.getOrDefault(id, 0) + 1);
//
//        // 額外示範：用 Economy 查餘額
//        if (economy != null) {
//            double newBal = economy.getBalance(player);
//            Bukkit.getLogger().info(
//                    "Player " + player.getName() +
//                            " balance changes #" + balanceChangeCounts.get(id) +
//                            ", new balance: " + newBal
//            );
//        } else {
//            Bukkit.getLogger().info(
//                    "Player " + player.getName() +
//                            " balance changes #" + balanceChangeCounts.get(id)
//            );
//        }
//    }
//
//    private void resetCounters() {
//        balanceChangeCounts.forEach((uuid, count) -> {
//            Player p = Bukkit.getPlayer(uuid);
//            if (p != null) {
//                Bukkit.getLogger().info(
//                        "Player " + p.getName() +
//                                " total Vault balance changes: " + count
//                );
//            }
//        });
//        balanceChangeCounts.clear();
//        Bukkit.getLogger().info("All Vault balance change counters have been reset.");
//    }
//}
