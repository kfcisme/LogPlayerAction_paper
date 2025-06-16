
package me.wowkfccc.logplayeraction.logPlayerAction_paper.event;

import me.wowkfccc.logplayeraction.logPlayerAction_paper.LogPlayerAction_paper;
import me.wowkfccc.logplayeraction.logPlayerAction_paper.event.plugin.onEssentialsAFK;
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
    public int redstoneCounts = 0;
    public int afktime = 0;


    public PlayerActionListener(LogPlayerAction_paper plugin) {
        this.plugin = plugin;
    }


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
                chunkLoadCounts = PlayerChunkLoadListener.SendInsertData(playerId),
                redstoneCounts = onRedstoneTracker.SendInsertData(playerId),
                afktime = onEssentialsAFK.SendInsertData(playerId)
        );


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
        onRedstoneTracker.resetCounters(playerId);
        onEssentialsAFK.resetCounters(playerId);
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
        public final int redstoneCounts;
        public final int afktime;

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
                int chunkLoadCounts,
                int redstoneCounts,
                int afktime
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
            this.redstoneCounts = redstoneCounts;
            this.afktime = afktime;
        }
    }
}
