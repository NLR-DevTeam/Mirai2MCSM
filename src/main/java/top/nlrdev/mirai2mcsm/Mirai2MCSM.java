package top.nlrdev.mirai2mcsm;


import net.mamoe.mirai.console.command.CommandManager;
import net.mamoe.mirai.console.plugin.jvm.JavaPlugin;
import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescriptionBuilder;
import net.mamoe.mirai.internal.deps.okhttp3.OkHttpClient;
import top.nlrdev.mirai2mcsm.commands.QueryPanelStatus;
import top.nlrdev.mirai2mcsm.configs.MCSMConfig;
import top.nlrdev.mirai2mcsm.configs.PluginConfig;

import java.util.concurrent.TimeUnit;

public final class Mirai2MCSM extends JavaPlugin {
    public static final Mirai2MCSM INSTANCE = new Mirai2MCSM();
    public static final OkHttpClient globalHttpClient = new OkHttpClient.Builder()
            .connectTimeout(3, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS).build();

    private Mirai2MCSM() {
        super(new JvmPluginDescriptionBuilder("top.nlrdev.mirai2mcsm", "0.1.0")
                .name("Mirai2MCSM")
                .info("一个提供Mirai对接MCSM管理面板的管理插件")
                .author("NLR DevTeam")
                .build());
    }

    @Override
    public void onEnable() {
        getLogger().info("初始化插件中...");
        initialize();
        getLogger().info("初始化插件完毕");
    }

    public void initialize() {
        refreshRemoteInfo();
        reloadConfigs();
        registerCommands();
        if(MCSMConfig.INSTANCE.getApiKey.get()==""){
            getLogger().error("未填写APIKey！请在/config/top.nlrdev.mirai2mcsm/mcsmconfig");
        }
    }
    public void refreshRemoteInfo(){

    }
    public void reloadConfigs(){
        reloadPluginConfig(MCSMConfig.INSTANCE);
        reloadPluginConfig(PluginConfig.INSTANCE);
    }
    public void registerCommands(){
        CommandManager.INSTANCE.registerCommand(QueryPanelStatus.INSTANCE,true);
    }
}
