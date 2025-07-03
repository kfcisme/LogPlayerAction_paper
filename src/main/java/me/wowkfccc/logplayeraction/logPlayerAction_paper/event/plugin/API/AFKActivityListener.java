package me.wowkfccc.logplayeraction.logPlayerAction_paper.event.plugin.API;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.*;
import me.wowkfccc.logplayeraction.logPlayerAction_paper.event.plugin.onEssentialsAFK;

import java.util.UUID;

public class AFKActivityListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        AFKManager.recordActivity(event.getPlayer());

    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        AFKManager.recordActivity(event.getPlayer());

    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        AFKManager.recordActivity(event.getPlayer());

    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        AFKManager.recordActivity(event.getPlayer());

    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        AFKManager.recordActivity(event.getPlayer());

    }

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent event) {
        AFKManager.recordActivity(event.getPlayer());

    }

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        if (!event.getFrom().toVector().equals(event.getTo().toVector())) {
            AFKManager.recordActivity(event.getPlayer());

        }
    }
}

