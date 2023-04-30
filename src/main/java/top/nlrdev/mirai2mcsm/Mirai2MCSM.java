package top.nlrdev.mirai2mcsm;


import net.mamoe.mirai.console.plugin.jvm.JavaPlugin;
import net.mamoe.mirai.internal.deps.okhttp3.*;
import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescriptionBuilder;
import net.mamoe.mirai.internal.deps.okhttp3.OkHttpClient;
import top.nlrdev.mirai2mcsm.configs.MCSMConfig;

import java.util.concurrent.TimeUnit;

public final class Mirai2MCSM extends JavaPlugin {
    public static final Mirai2MCSM INSTANCE = new Mirai2MCSM();
    public static final OkHttpClient globalHttpClient = new OkHttpClient.Builder().connectTimeout(3, TimeUnit.SECONDS).readTimeout(10, TimeUnit.SECONDS).build();

    private Mirai2MCSM() {
        super(new JvmPluginDescriptionBuilder("top.nlrdev.mirai2mcsm", "0.1.0")
                .info("EG")
                .build());
    }

    @Override
    public void onEnable() {
        getLogger().info("初始化插件中...");
        initialize();
    }

    public void initialize() {
        refreshRemoteInfo();
        reloadConfigs();
        registerCommands();
    }
    public void refreshRemoteInfo(){

    }
    public void reloadConfigs(){

    }
    public void registerCommands(){

    }
}
