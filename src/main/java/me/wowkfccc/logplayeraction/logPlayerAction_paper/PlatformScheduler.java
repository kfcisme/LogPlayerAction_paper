package me.wowkfccc.logplayeraction.logPlayerAction_paper;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Method;
import java.time.Duration;

public class PlatformScheduler {

    public static final boolean IS_FOLIA ;
    //    private static final boolean IS_FOLIA;
    private static Class<?> foliaClass;
    private static Method getSchedulerServiceMethod;
    private static Method runTaskTimerMethod;
    private static Method runTaskLaterMethod;
    private static Method runTaskMethod;

    static {
        boolean foliaDetected = false;
        try {
            foliaClass = Class.forName("dev.folia.Folia");
            getSchedulerServiceMethod = foliaClass.getMethod("getSchedulerService");
            Class<?> schedulerServiceClass = Class.forName("dev.folia.api.scheduler.FoliaSchedulerService");
            runTaskTimerMethod = schedulerServiceClass.getMethod(
                    "runTaskTimer",
                    JavaPlugin.class,
                    Runnable.class,
                    Duration.class,
                    Duration.class
            );
            runTaskLaterMethod = schedulerServiceClass.getMethod(
                    "runTaskLater",
                    JavaPlugin.class,
                    Runnable.class,
                    Duration.class
            );
            runTaskMethod = schedulerServiceClass.getMethod(
                    "runTask",
                    JavaPlugin.class,
                    Runnable.class
            );
            foliaDetected = true;
        } catch (ClassNotFoundException | NoSuchMethodException ex) {
            foliaDetected = false;
        }
        IS_FOLIA = foliaDetected;
    }

    public static void runTaskTimer(JavaPlugin plugin, Runnable task, long delayTicks, long periodTicks) {
//        if (IS_FOLIA) {
            try {
                Object schedulerService = getSchedulerServiceMethod.invoke(null);
                Duration delay = Duration.ofMillis(delayTicks * 50);
                Duration period = Duration.ofMillis(periodTicks * 50);
                runTaskTimerMethod.invoke(schedulerService, plugin, task, delay, period);
                return;
            } catch (Exception e) {
                Bukkit.getLogger().warning("[PlatformScheduler] Folia runTaskTimer 失敗，改用 Bukkit: " + e.getMessage());
            }
        //}
//        Bukkit.getScheduler().runTaskTimer(plugin, task, delayTicks, periodTicks);
    }

    public static void runTaskLater(JavaPlugin plugin, Runnable task, long delayTicks) {
//        if (IS_FOLIA) {
            try {
                Object schedulerService = getSchedulerServiceMethod.invoke(null);
                Duration delay = Duration.ofMillis(delayTicks * 50);
                runTaskLaterMethod.invoke(schedulerService, plugin, task, delay);
                return;
            } catch (Exception e) {
                Bukkit.getLogger().warning("[PlatformScheduler] Folia runTaskLater 失敗，改用 Bukkit: " + e.getMessage());
            }
        //}
        //Bukkit.getScheduler().runTaskLater(plugin, task, delayTicks);
    }

    public static void runTask(JavaPlugin plugin, Runnable task) {
        if (IS_FOLIA) {
            try {
                Object schedulerService = getSchedulerServiceMethod.invoke(null);
                runTaskMethod.invoke(schedulerService, plugin, task);
                return;
            } catch (Exception e) {
                Bukkit.getLogger().warning("[PlatformScheduler] Folia runTask 失敗，改用 Bukkit: " + e.getMessage());
            }
        }
        Bukkit.getScheduler().runTask(plugin, task);
    }

    public static void cancelAll(JavaPlugin plugin) {
        Bukkit.getScheduler().cancelTasks(plugin);
    }
}