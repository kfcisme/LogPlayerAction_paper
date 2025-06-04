
package me.wowkfccc.logplayeraction.logPlayerAction_paper.event;

import me.wowkfccc.logplayeraction.logPlayerAction_paper.LogPlayerAction_paper;
//import me.wowkfccc.logplayeraction.logplayeraction.Logplayeraction;
import me.wowkfccc.logplayeraction.logPlayerAction_paper.event.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.*;
import org.bukkit.event.block.*;
import org.bukkit.event.inventory.*;
import org.bukkit.event.player.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerActionListener implements Listener {
    private final LogPlayerAction_paper plugin;
    public int chatCount = 0;
    public int pickupCounts = 0;
    public int blockBreakCounts = 0;
    public int tntPrimeCounts = 0;
    public int multiPlaceCounts = 0;
    public int blockDamageCounts = 0;
    public int blockPlaceCounts = 0;
    public int craftCounts = 0;
    public int dmgByEntityCounts = 0;
    public int deathCounts = 0;
    public int explosionCounts = 0;
    public int furnaceExtractCounts = 0;
    public int invCloseCounts= 0;
    public int invOpenCounts = 0;
    public int bucketEmptyCounts = 0;
    public int bucketFillCounts = 0;
    public int cmdPreCounts = 0;
    public int cmdSendCounts = 0;
    public int playerDeathCounts = 0;
    public int itemDropCounts = 0;
    public int expChangeCounts = 0;
    public int interactCounts = 0;
    public int quitCounts = 0;
    public int levelChangeCounts = 0;
    public int respawnCounts = 0;
    public int teleportCounts = 0;
    public int chunkLoadCounts = 0;

    // 每种事件的计数 Map
//    private final Map<UUID, Integer> pickupCounts          = new HashMap<>();
//    private final Map<UUID, Integer> blockBreakCounts      = new HashMap<>();
//    private final Map<UUID, Integer> tntPrimeCounts        = new HashMap<>();
//    private final Map<UUID, Integer> multiPlaceCounts      = new HashMap<>();
//    private final Map<UUID, Integer> chatCounts            = new HashMap<>();
//    private final Map<UUID, Integer> blockDamageCounts     = new HashMap<>();
//    private final Map<UUID, Integer> blockPlaceCounts      = new HashMap<>();
//    private final Map<UUID, Integer> craftCounts           = new HashMap<>();
//    private final Map<UUID, Integer> dmgByEntityCounts     = new HashMap<>();
//    private final Map<UUID, Integer> deathCounts           = new HashMap<>();
//    private final Map<UUID, Integer> explosionCounts       = new HashMap<>();
//    private final Map<UUID, Integer> furnaceExtractCounts  = new HashMap<>();
//    private final Map<UUID, Integer> invCloseCounts        = new HashMap<>();
//    private final Map<UUID, Integer> invOpenCounts         = new HashMap<>();
//    private final Map<UUID, Integer> bucketEmptyCounts     = new HashMap<>();
//    private final Map<UUID, Integer> bucketFillCounts      = new HashMap<>();
//    private final Map<UUID, Integer> cmdPreCounts          = new HashMap<>();
//    private final Map<UUID, Integer> cmdSendCounts         = new HashMap<>();
//    private final Map<UUID, Integer> playerDeathCounts     = new HashMap<>();
//    private final Map<UUID, Integer> itemDropCounts        = new HashMap<>();
//    private final Map<UUID, Integer> expChangeCounts       = new HashMap<>();
//    private final Map<UUID, Integer> interactCounts        = new HashMap<>();
//    private final Map<UUID, Integer> levelChangeCounts     = new HashMap<>();
//    private final Map<UUID, Integer> quitCounts            = new HashMap<>();
//    private final Map<UUID, Integer> respawnCounts         = new HashMap<>();
//    private final Map<UUID, Integer> teleportCounts        = new HashMap<>();
//
    public PlayerActionListener(LogPlayerAction_paper plugin) {
        this.plugin = plugin;
    }
//
//    @EventHandler public void onEntityPickup(EntityPickupItemEvent e) {
//
//        UUID id = e.getEntity().getUniqueId();
//        pickupCounts.put(id, pickupCounts.getOrDefault(id, 0) + 1);
//    }
//    @EventHandler public void onBlockBreak(BlockBreakEvent e) {
//        UUID id = e.getPlayer().getUniqueId();
//        blockBreakCounts.put(id, blockBreakCounts.getOrDefault(id, 0) + 1);
//    }
//    @EventHandler public void onTNTPrime(ExplosionPrimeEvent e) {
//        plugin.getServer().getOnlinePlayers().stream().findFirst().ifPresent(p -> {
//            UUID id = p.getUniqueId();
//            tntPrimeCounts.put(id, tntPrimeCounts.getOrDefault(id, 0) + 1);
//        });
//    }
//    @EventHandler public void onBlockMultiPlace(BlockMultiPlaceEvent e) {
//        UUID id = e.getPlayer().getUniqueId();
//        multiPlaceCounts.put(id, multiPlaceCounts.getOrDefault(id, 0) + 1);
//    }
//    @EventHandler public void onAsyncChat(AsyncPlayerChatEvent e) {
//        UUID id = e.getPlayer().getUniqueId();
//        chatCounts.put(id, chatCounts.getOrDefault(id, 0) + 1);
//    }
//    @EventHandler public void onBlockDamage(BlockDamageEvent e) {
//        UUID id = e.getPlayer().getUniqueId();
//        blockDamageCounts.put(id, blockDamageCounts.getOrDefault(id, 0) + 1);
//    }
//    @EventHandler public void onBlockPlace(BlockPlaceEvent e) {
//        UUID id = e.getPlayer().getUniqueId();
//        blockPlaceCounts.put(id, blockPlaceCounts.getOrDefault(id, 0) + 1);
//    }
//    @EventHandler public void onCraftItem(CraftItemEvent e) {
//        UUID id = e.getWhoClicked().getUniqueId();
//        craftCounts.put(id, craftCounts.getOrDefault(id, 0) + 1);
//    }
//    @EventHandler public void onEntityDamageByEntity(EntityDamageByEntityEvent e) {
//        if (!(e.getDamager() instanceof org.bukkit.entity.Player)) return;
//        UUID id = ((org.bukkit.entity.Player)e.getDamager()).getUniqueId();
//        dmgByEntityCounts.put(id, dmgByEntityCounts.getOrDefault(id, 0) + 1);
//    }
//    @EventHandler public void onEntityDeath(EntityDeathEvent e) {
//        if (e.getEntity().getKiller() != null) {
//            UUID id = e.getEntity().getKiller().getUniqueId();
//            deathCounts.put(id, deathCounts.getOrDefault(id, 0) + 1);
//        }
//    }
//    @EventHandler public void onExplosionPrime(ExplosionPrimeEvent e) {
//        // 同 TNT Prime，可合并或分开
//        plugin.getServer().getOnlinePlayers().stream().findFirst().ifPresent(p -> {
//            UUID id = p.getUniqueId();
//            explosionCounts.put(id, explosionCounts.getOrDefault(id, 0) + 1);
//        });
//    }
//    @EventHandler public void onFurnaceExtract(FurnaceExtractEvent e) {
//        UUID id = e.getPlayer().getUniqueId();
//        furnaceExtractCounts.put(id, furnaceExtractCounts.getOrDefault(id, 0) + 1);
//    }
//    @EventHandler public void onInventoryClose(InventoryCloseEvent e) {
//        if (e.getPlayer() instanceof org.bukkit.entity.Player) {
//            UUID id = ((org.bukkit.entity.Player)e.getPlayer()).getUniqueId();
//            invCloseCounts.put(id, invCloseCounts.getOrDefault(id, 0) + 1);
//        }
//    }
//    @EventHandler public void onInventoryOpen(InventoryOpenEvent e) {
//        if (e.getPlayer() instanceof org.bukkit.entity.Player) {
//            UUID id = ((org.bukkit.entity.Player)e.getPlayer()).getUniqueId();
//            invOpenCounts.put(id, invOpenCounts.getOrDefault(id, 0) + 1);
//        }
//    }
//    @EventHandler public void onBucketEmpty(PlayerBucketEmptyEvent e) {
//        UUID id = e.getPlayer().getUniqueId();
//        bucketEmptyCounts.put(id, bucketEmptyCounts.getOrDefault(id, 0) + 1);
//    }
//    @EventHandler public void onBucketFill(PlayerBucketFillEvent e) {
//        UUID id = e.getPlayer().getUniqueId();
//        bucketFillCounts.put(id, bucketFillCounts.getOrDefault(id, 0) + 1);
//    }
//    @EventHandler public void onCommandPre(PlayerCommandPreprocessEvent e) {
//        UUID id = e.getPlayer().getUniqueId();
//        cmdPreCounts.put(id, cmdPreCounts.getOrDefault(id, 0) + 1);
//    }
//    @EventHandler public void onCommandSend(PlayerCommandSendEvent e) {
//        UUID id = e.getPlayer().getUniqueId();
//        cmdSendCounts.put(id, cmdSendCounts.getOrDefault(id, 0) + 1);
//    }
//    @EventHandler public void onPlayerDeath(PlayerDeathEvent e) {
//        UUID id = e.getEntity().getUniqueId();
//        playerDeathCounts.put(id, playerDeathCounts.getOrDefault(id, 0) + 1);
//    }
//    @EventHandler public void onPlayerDrop(PlayerDropItemEvent e) {
//        UUID id = e.getPlayer().getUniqueId();
//        itemDropCounts.put(id, itemDropCounts.getOrDefault(id, 0) + 1);
//    }
//    @EventHandler public void onExpChange(PlayerExpChangeEvent e) {
//        UUID id = e.getPlayer().getUniqueId();
//        expChangeCounts.put(id, expChangeCounts.getOrDefault(id, 0) + 1);
//    }
//    @EventHandler public void onInteract(PlayerInteractEvent e) {
//        UUID id = e.getPlayer().getUniqueId();
//        interactCounts.put(id, interactCounts.getOrDefault(id, 0) + 1);
//    }
//    @EventHandler public void onLevelChange(PlayerLevelChangeEvent e) {
//        UUID id = e.getPlayer().getUniqueId();
//        levelChangeCounts.put(id, levelChangeCounts.getOrDefault(id, 0) + 1);
//    }
//    @EventHandler public void onQuit(PlayerQuitEvent e) {
//        UUID id = e.getPlayer().getUniqueId();
//        quitCounts.put(id, quitCounts.getOrDefault(id, 0) + 1);
//    }
//    @EventHandler public void onRespawn(PlayerRespawnEvent e) {
//        UUID id = e.getPlayer().getUniqueId();
//        respawnCounts.put(id, respawnCounts.getOrDefault(id, 0) + 1);
//    }
//    @EventHandler public void onTeleport(PlayerTeleportEvent e) {
//        UUID id = e.getPlayer().getUniqueId();
//        teleportCounts.put(id, teleportCounts.getOrDefault(id, 0) + 1);
//    }

    /** 返回所有事件计数并重置为 0 */
    public EventCounts getAndResetCounts(UUID playerId) {

        EventCounts c = new EventCounts(
                pickupCounts = onEntityPickupItem.SendInsertData(playerId),
                blockBreakCounts = onBlockBreak.SendInsertData(playerId),
                tntPrimeCounts = onTNTPrime.SendInsertData(playerId),
                multiPlaceCounts = onBlockMultiPlace.SendInsertData(playerId),
                chatCount = onAsyncPlayerChat.SendInsertData(playerId),
                blockDamageCounts = onBlockDamage.SendInsertData(playerId),
                blockPlaceCounts = onBlockPlace.SendInsertData(playerId),
                craftCounts = onCraftItem.SendInsertData(playerId),
                dmgByEntityCounts = onEntityDamageByEntity.SendInsertData(playerId),
                deathCounts = onEntityDeath.SendInsertData(playerId),
                explosionCounts = onExplosionPrime.SendInsertData(playerId),
                furnaceExtractCounts = onFurnaceExtract.SendInsertData(playerId),
                invCloseCounts = onInventoryClose.SendInsertData(playerId),
                invOpenCounts = onInventoryOpen.SendInsertData(playerId),
                bucketEmptyCounts = onPlayerBucketEmpty.SendInsertData(playerId),
                bucketFillCounts = onPlayerBucketFill.SendInsertData(playerId),
                cmdPreCounts = onPlayerCommandPreprocess.SendInsertData(playerId),
                cmdSendCounts = onPlayerCommandSend.SendInsertData(playerId),
                playerDeathCounts = onPlayerDeath.SendInsertData(playerId),
                itemDropCounts = onPlayerDropItem.SendInsertData(playerId),
                expChangeCounts = onPlayerExpChange.SendInsertData(playerId),
                interactCounts = onPlayerInteract.SendInsertData(playerId),
                levelChangeCounts = onPlayerLevelChange.SendInsertData(playerId),
                quitCounts = onPlayerQuit.SendInsertData(playerId),
                respawnCounts = onPlayerRespawn.SendInsertData(playerId),
                teleportCounts = onPlayerTeleport.SendInsertData(playerId),
                chunkLoadCounts = PlayerChunkLoadListener.SendInsertData(playerId)
//                pickupCounts.getOrDefault(playerId, 0),
//                blockBreakCounts.getOrDefault(playerId, 0),
//                tntPrimeCounts.getOrDefault(playerId, 0),
//                multiPlaceCounts.getOrDefault(playerId, 0),
//                chatCounts.getOrDefault(playerId, 0),
//                blockDamageCounts.getOrDefault(playerId, 0),
//                blockPlaceCounts.getOrDefault(playerId, 0),
//                craftCounts.getOrDefault(playerId, 0),
//                dmgByEntityCounts.getOrDefault(playerId, 0),
//                deathCounts.getOrDefault(playerId, 0),
//                explosionCounts.getOrDefault(playerId, 0),
//                furnaceExtractCounts.getOrDefault(playerId, 0),
//                invCloseCounts.getOrDefault(playerId, 0),
//                invOpenCounts.getOrDefault(playerId, 0),
//                bucketEmptyCounts.getOrDefault(playerId, 0),
//                bucketFillCounts.getOrDefault(playerId, 0),
//                cmdPreCounts.getOrDefault(playerId, 0),
//                cmdSendCounts.getOrDefault(playerId, 0),
//                playerDeathCounts.getOrDefault(playerId, 0),
//                itemDropCounts.getOrDefault(playerId, 0),
//                expChangeCounts.getOrDefault(playerId, 0),
//                interactCounts.getOrDefault(playerId, 0),
//                levelChangeCounts.getOrDefault(playerId, 0),
//                quitCounts.getOrDefault(playerId, 0),
//                respawnCounts.getOrDefault(playerId, 0),
//                teleportCounts.getOrDefault(playerId, 0)
        );

        // 重置
//        pickupCounts.put(playerId, 0);
//        blockBreakCounts.put(playerId, 0);
//        tntPrimeCounts.put(playerId, 0);
//        multiPlaceCounts.put(playerId, 0);
//        chatCounts.put(playerId, 0);
//        blockDamageCounts.put(playerId, 0);
//        blockPlaceCounts.put(playerId, 0);
//        craftCounts.put(playerId, 0);
//        dmgByEntityCounts.put(playerId, 0);
//        deathCounts.put(playerId, 0);
//        explosionCounts.put(playerId, 0);
//        furnaceExtractCounts.put(playerId, 0);
//        invCloseCounts.put(playerId, 0);
//        invOpenCounts.put(playerId, 0);
//        bucketEmptyCounts.put(playerId, 0);
//        bucketFillCounts.put(playerId, 0);
//        cmdPreCounts.put(playerId, 0);
//        cmdSendCounts.put(playerId, 0);
//        playerDeathCounts.put(playerId, 0);
//        itemDropCounts.put(playerId, 0);
//        expChangeCounts.put(playerId, 0);
//        interactCounts.put(playerId, 0);
//        levelChangeCounts.put(playerId, 0);
//        quitCounts.put(playerId, 0);
//        respawnCounts.put(playerId, 0);
//        teleportCounts.put(playerId, 0);

        return c;
    }

    public static void ResetCounters(UUID playerId) {
        LogPlayerAction_paper.getInstance().getLogger().info("Resetting counters for player: " + playerId);
        onAsyncPlayerChat.resetCounters(playerId);
        onBlockPlace.resetCounters(playerId);
        onBlockDamage.resetCounters(playerId);
        onBlockMultiPlace.resetCounters(playerId);
        onCraftItem.resetCounters(playerId);
        onEntityDamageByEntity.resetCounters(playerId);
        onEntityDeath.resetCounters(playerId);
        onEntityPickupItem.resetCounters(playerId);
        onExplosionPrime.resetCounters(playerId);
        onFurnaceExtract.resetCounters(playerId);
        onInventoryClose.resetCounters(playerId);
        onInventoryOpen.resetCounters(playerId);
        onPlayerBucketEmpty.resetCounters(playerId);
        onPlayerBucketFill.resetCounters(playerId);
        onPlayerCommandPreprocess.resetCounters(playerId);
        onPlayerCommandSend.resetCounters(playerId);
        onPlayerDeath.resetCounters(playerId);
        onPlayerDropItem.resetCounters(playerId);
        onPlayerExpChange.resetCounters(playerId);
        onPlayerInteract.resetCounters(playerId);
        onPlayerLevelChange.resetCounters(playerId);
        onPlayerQuit.resetCounters(playerId);
        onPlayerRespawn.resetCounters(playerId);
        onPlayerTeleport.resetCounters(playerId);
        onTNTPrime.resetCounters(playerId);
        PlayerChunkLoadListener.resetChunkLoadCounts(playerId);

    }

    public static class EventCounts {
        public final int pickup;
        public final int blockBreak;
        public final int tntPrime;
        public final int multiPlace;
        public final int chat;
        public final int blockDamage;
        public final int blockPlace;
        public final int craft;
        public final int dmgByEntity;
        public final int death;
        public final int explosion;
        public final int furnaceExtract;
        public final int invClose;
        public final int invOpen;
        public final int bucketEmpty;
        public final int bucketFill;
        public final int cmdPre;
        public final int cmdSend;
        public final int playerDeath;
        public final int itemDrop;
        public final int expChange;
        public final int interact;
        public final int levelChange;
        public final int quit;
        public final int respawn;
        public final int teleport;
        public final int chunkLoadCounts;

        public EventCounts(
                int pickup,
                int blockBreak,
                int tntPrime,
                int multiPlace,
                int chat,
                int blockDamage,
                int blockPlace,
                int craft,
                int dmgByEntity,
                int death,
                int explosion,
                int furnaceExtract,
                int invClose,
                int invOpen,
                int bucketEmpty,
                int bucketFill,
                int cmdPre,
                int cmdSend,
                int playerDeath,
                int itemDrop,
                int expChange,
                int interact,
                int levelChange,
                int quit,
                int respawn,
                int teleport,
                int chunkLoadCounts
        ) {
            this.pickup = pickup;
            this.blockBreak = blockBreak;
            this.tntPrime = tntPrime;
            this.multiPlace = multiPlace;
            this.chat = chat;
            this.blockDamage = blockDamage;
            this.blockPlace = blockPlace;
            this.craft = craft;
            this.dmgByEntity = dmgByEntity;
            this.death = death;
            this.explosion = explosion;
            this.furnaceExtract = furnaceExtract;
            this.invClose = invClose;
            this.invOpen = invOpen;
            this.bucketEmpty = bucketEmpty;
            this.bucketFill = bucketFill;
            this.cmdPre = cmdPre;
            this.cmdSend = cmdSend;
            this.playerDeath = playerDeath;
            this.itemDrop = itemDrop;
            this.expChange = expChange;
            this.interact = interact;
            this.levelChange = levelChange;
            this.quit = quit;
            this.respawn = respawn;
            this.teleport = teleport;
            this.chunkLoadCounts = chunkLoadCounts;
        }
    }
}
