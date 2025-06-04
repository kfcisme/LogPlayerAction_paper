package me.wowkfccc.logplayeraction.logplayeraction.event.plugin.API;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Method;

public class EssentialsHook {
    private final Object essentialsInstance;
    private final Method    getEconomyMethod;

    /**
     * 嘗試 hook Essentials；若 Essentials 或其 API class 不存在，就跳出例外
     */
    public EssentialsHook(JavaPlugin plugin) throws Exception {
        // 1. 確認 class 檔存在（否則 ClassNotFoundException）
        Class<?> essentialsClass = Class.forName("com.earth2me.essentials.Essentials");
        // 2. 取得已載入的 Plugin instance
        Plugin p = plugin.getServer().getPluginManager().getPlugin("Essentials");
        essentialsClass.cast(p);
        this.essentialsInstance = p;
        // 3. 拿到 getEconomy() 方法 (回傳 com.earth2me.essentials.api.Economy)
        this.getEconomyMethod = essentialsClass.getMethod("getEconomy");
    }

    /** 取得 Economy 物件，呼叫前先確保 hook 成功 */
    public Object getEconomy() throws Exception {
        return getEconomyMethod.invoke(essentialsInstance);
    }
}
