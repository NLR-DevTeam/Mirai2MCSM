package top.nlrdev.mirai2mcsm;


import net.mamoe.mirai.console.command.CommandManager;
import net.mamoe.mirai.console.plugin.PluginManager;
import net.mamoe.mirai.console.plugin.jvm.JavaPlugin;
import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescriptionBuilder;
import net.mamoe.mirai.internal.deps.okhttp3.OkHttpClient;
import net.mamoe.mirai.utils.MiraiLogger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import top.nlrdev.mirai2mcsm.commands.*;
import top.nlrdev.mirai2mcsm.configs.MCSMConfig;
import top.nlrdev.mirai2mcsm.configs.PluginConfig;
import top.nlrdev.mirai2mcsm.utils.RequestHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public final class Mirai2MCSM extends JavaPlugin {

    public static final Mirai2MCSM INSTANCE = new Mirai2MCSM();
    public static final MiraiLogger logger = INSTANCE.getLogger();
    public static final OkHttpClient globalHttpClient = new OkHttpClient.Builder()
            .connectTimeout(3, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS).build();
    /**
     * 守护进程的缓存，
     * 存储值为< remark , uuid >
     **/
    public static final HashMap<String, String> remotesRemark = new HashMap<>();
    /**
     * 守护进程的缓存，
     * 存储值为< uuid >
     **/
    public static final ArrayList<String> remotes = new ArrayList<>();
    /**
     * 实例的缓存，
     * 存储值为< uuid , < nickName , uuid > >
     **/
    public static final ArrayList<String> instances = new ArrayList<>();
    public static final HashMap<String, String> instancesRemark = new HashMap<>();

    private Mirai2MCSM() {
        super(new JvmPluginDescriptionBuilder("top.nlrdev.mirai2mcsm", "0.1.0")
                .name("Mirai2MCSM")
                .info("一个提供Mirai对接MCSM管理面板的管理插件")
                .author("NLR DevTeam")
                .build());
    }


    @Override
    public void onEnable() {
        logger.info("初始化插件中...");
        initialize();
        logger.info("初始化插件完毕");
    }
    @Override
    public void onDisable(){
        logger.info("正在关闭插件");
    }
    public void initialize() {
        reloadConfigs();
        if (Objects.equals(MCSMConfig.INSTANCE.apiKey.get(), "")) {
            logger.error("未填写 APIKey！请在 /config/top.nlrdev.mirai2mcsm/MCSMConfig.yml 中填写！");
        }
        refreshInfo();
        registerCommands();
    }

    public void reloadConfigs() {
        reloadPluginConfig(MCSMConfig.INSTANCE);
        reloadPluginConfig(PluginConfig.INSTANCE);
    }

    public void registerCommands() {
        CommandManager.INSTANCE.registerCommand(QueryPanelStatus.INSTANCE, true);
        CommandManager.INSTANCE.registerCommand(ListInstances.INSTANCE, true);
        CommandManager.INSTANCE.registerCommand(ListRemotes.INSTANCE, true);
        CommandManager.INSTANCE.registerCommand(ProtectedInstanceOperation.INSTANCE, true);
        CommandManager.INSTANCE.registerCommand(Refresh.INSTANCE, true);
    }

    public static void refreshInfo() {
        try {
            remotes.clear();
            remotesRemark.clear();
            instances.clear();
            remotesRemark.clear();
            refreshRemotesInfo();
            refreshInstancesInfo();
        }catch (Exception e){
            logger.error("网络连接出错，请检查APIKey与APIURL是否正确填写");
        }
    }

    public static void refreshRemotesInfo() {
        JSONObject obj = RequestHandler.handleGetRequest("/service/remote_services_list", "");
        JSONArray data = obj.getJSONArray("data");
        for (Object object : data) {
            JSONObject json = (JSONObject) object;
            remotesRemark.put(json.getString("remarks"), json.getString("uuid"));
            remotes.add(json.getString("uuid"));
        }
    }

    public static void refreshInstancesInfo() {
         JSONObject obj = RequestHandler.handleGetRequest("/service/remote_services", "");
         JSONArray data = obj.getJSONArray("data");
         for (Object object : data) {
             JSONObject jsonObject = (JSONObject) object;
             JSONArray instancesArray = jsonObject.getJSONArray("instances");
             for (Object instance : instancesArray) {
                 JSONObject inst = (JSONObject) instance;
                 instancesRemark.put(inst.getJSONObject("config").getString("nickname"), inst.getString("instanceUuid"));
                 instances.add(inst.getString("instanceUuid"));
             }
         }
    }

    public static String getRemotesUUID(String arg) {
        if (remotes.size() == 1) return remotes.get(0);
        if (arg.matches("[0-9]+")) return remotes.get(Integer.getInteger(arg));
        if (remotes.contains(arg)) return arg;
        return remotesRemark.getOrDefault(arg, null);
    }

    public static String getInstancesUUID(String arg) {
        if (instances.size() == 1) return instances.get(0);
        if (instances.contains(arg)) return arg;
        return instancesRemark.getOrDefault(arg,null);
    }
}
