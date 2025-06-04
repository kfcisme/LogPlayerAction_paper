package me.wowkfccc.logplayeraction.logPlayerAction_paper;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import me.wowkfccc.logplayeraction.logPlayerAction_paper.event.PlayerSessionListener;
import org.bukkit.entity.Player;

public class commandmanager implements CommandExecutor {

    private final LogPlayerAction_paper plugin;
    private final PlayerSessionListener sessionListener;

    public commandmanager(LogPlayerAction_paper plugin,
                          PlayerSessionListener sessionListener) {
        this.plugin = plugin;
        this.sessionListener = sessionListener;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd,
                             String label, String[] args) {

        if (!cmd.getName().equalsIgnoreCase("logplayeraction"))
            return false;

        if (args.length == 0) {
            sender.sendMessage("§aLogPlayerAction v"
                    + plugin.getDescription().getVersion() + " by Wowkfccc");
            return true;
        }

        switch (args[0].toLowerCase()) {

            /* -------- /logplayeraction reload -------- */
            case "reload" -> {
                if (!sender.hasPermission("logplayeraction.reload")) {
                    sender.sendMessage(ChatColor.RED + "You do not have permission.");
                    return true;
                }
                plugin.reloadConfig();
                sender.sendMessage(ChatColor.GREEN + "Config reloaded.");
            }

            /* -------- /logplayeraction time_reset [player] -------- */
            case "time_reset" -> {
                if (!sender.hasPermission("logplayeraction.time_reset")) {
                    sender.sendMessage(ChatColor.RED + "You do not have permission.");
                    return true;
                }

                // default = 重置自己
                if (args.length == 1) {
                    if (!(sender instanceof Player p)) {
                        sender.sendMessage(ChatColor.RED + "Console 必須指定玩家或使用 all！");
                        return true;
                    }
                    sessionListener.resetTimer(p.getUniqueId());
                    sender.sendMessage(ChatColor.GREEN + "已重置自己 的計時器！");
                    return true;
                }

                String targetArg = args[1];

                // all
                if (targetArg.equalsIgnoreCase("all")) {
                    for (Player p : plugin.getServer().getOnlinePlayers()) {
                        sessionListener.resetTimer(p.getUniqueId());
                    }
                    sender.sendMessage(ChatColor.GREEN + "已重置 所有在線玩家 的計時器！");
                    return true;
                }

                // 單一玩家
                Player target = plugin.getServer().getPlayerExact(targetArg);
                if (target == null) {
                    sender.sendMessage(ChatColor.RED + "找不到玩家 " + targetArg);
                    return true;
                }
                sessionListener.resetTimer(target.getUniqueId());
                sender.sendMessage(ChatColor.GREEN + "已重置 " + target.getName() + " 的計時器！");
                return true;
            }

            default -> sender.sendMessage(ChatColor.RED + "未知子命令。");
        }
        return true;
    }
}
