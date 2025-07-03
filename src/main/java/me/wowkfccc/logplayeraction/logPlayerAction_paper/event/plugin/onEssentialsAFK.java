package me.wowkfccc.logplayeraction.logPlayerAction_paper.event.plugin;

import me.wowkfccc.logplayeraction.logPlayerAction_paper.event.plugin.API.AFKManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.*;

public class onEssentialsAFK implements Listener {

    // 玩家在方塊層級移動時重置
    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        if (e.getFrom().getBlockX() != e.getTo().getBlockX()
                || e.getFrom().getBlockY() != e.getTo().getBlockY()
                || e.getFrom().getBlockZ() != e.getTo().getBlockZ()) {
            AFKManager.recordActivity(e.getPlayer());
        }
    }

    // 聊天就重置
    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        AFKManager.recordActivity(e.getPlayer());
    }

    // 執行指令
    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent e) {
        AFKManager.recordActivity(e.getPlayer());
    }

    // 點擊/互動也算活動
    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        AFKManager.recordActivity(e.getPlayer());
    }
}