package me.wowkfccc.logplayeraction.logPlayerAction_paper.event;

import me.wowkfccc.logplayeraction.logPlayerAction_paper.LogPlayerAction_paper;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * 用於追蹤玩家在一段時間內放置紅石方塊以及間接觸發紅石熄滅的次數
 */
public class onRedstoneTracker implements Listener {
    //private final JavaPlugin plugin;
    private final LogPlayerAction_paper plugin;
    private static final Map<UUID, Integer> PlayerRedstoneTargged = new HashMap<>();
    // 定義所有紅石相關方塊類型
    private final Set<Material> redstoneMaterials = EnumSet.of(
            Material.REDSTONE_WIRE,
            Material.REDSTONE_TORCH,
//            Material.UNLIT_REDSTONE_TORCH,
            Material.REPEATER,
            Material.COMPARATOR,
            Material.REDSTONE_BLOCK,
            Material.RAIL,
            Material.DETECTOR_RAIL,
            Material.ACTIVATOR_RAIL,
            Material.POWERED_RAIL,
            Material.REDSTONE,
            Material.HOPPER,
            Material.DROPPER,
            Material.DISPENSER,
            Material.PISTON,
            Material.STICKY_PISTON,
            Material.TARGET,
            Material.LEVER,
            Material.OAK_PRESSURE_PLATE,
            Material.SPRUCE_PRESSURE_PLATE,
            Material.BIRCH_PRESSURE_PLATE,
            Material.JUNGLE_PRESSURE_PLATE,
            Material.ACACIA_PRESSURE_PLATE,
            Material.DARK_OAK_PRESSURE_PLATE,
            Material.CRIMSON_PRESSURE_PLATE,
            Material.WARPED_PRESSURE_PLATE,
            Material.STONE_PRESSURE_PLATE,
            Material.LIGHT_WEIGHTED_PRESSURE_PLATE,
            Material.HEAVY_WEIGHTED_PRESSURE_PLATE,
            Material.REDSTONE_LAMP,
            Material.OAK_DOOR,
            Material.IRON_DOOR,
            Material.OBSERVER,
            Material.DAYLIGHT_DETECTOR,
            Material.BELL,
            Material.LECTERN
    );

    public onRedstoneTracker(LogPlayerAction_paper plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onRedtoneBlockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        UUID playerid = player.getUniqueId();

//        Session session = sessions.get(player);
//        if (session != null) {
        Material type = event.getBlockPlaced().getType();
        if (redstoneMaterials.contains(type)) {
            // 如果是紅石相關方塊，增加計數
            PlayerRedstoneTargged.put(playerid, PlayerRedstoneTargged.getOrDefault(playerid, 0) + 1);
            Bukkit.getLogger().info("Player " + player.getName() + " placed redstone block count: " + PlayerRedstoneTargged.get(playerid));

            // 在這裡可以開始一個追蹤 Session
            // startSession(playerid, 20 * 60); // 假設追蹤 1 分鐘
        }
    }


    public static int SendInsertData(UUID playerId) {
        return PlayerRedstoneTargged.getOrDefault(playerId, 0);
    }

    public static void resetCounters(UUID playerId) {
        // Log the counters before resetting
        Player player = Bukkit.getPlayer(playerId);
        for (Map.Entry<UUID, Integer> entry : PlayerRedstoneTargged.entrySet()) {
            //Player player = Bukkit.getPlayer(entry.getKey());
            if (player != null) {
                Bukkit.getLogger().info("Player " + player.getName() + " total PlayerExpChange count: " + entry.getValue());
            }
        }

        // Clear the counters
        PlayerRedstoneTargged.remove(playerId);
        Bukkit.getLogger().info("All player PlayerExpChange counters have been reset.");
    }
}



    /**
     * 內部使用的玩家追蹤 Session
     */
//    private static class Session {
//        int placedCount = 0;
//        int deactivationCount = 0;
//        Set<Location> trackedLocations = new HashSet<>();
//    }



