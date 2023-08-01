package top.nlrdev.mirai2mcsm.configs;

import net.mamoe.mirai.console.data.Value;
import net.mamoe.mirai.console.data.java.JavaAutoSavePluginConfig;

public class MCSMConfig extends JavaAutoSavePluginConfig {
    public final static MCSMConfig INSTANCE = new MCSMConfig();
    public Value<String> apiKey = value("APIKey", "");
    public Value<String> apiUrl = value("APIURL", "http://127.0.0.1:23333");

    private MCSMConfig() {
        super("MCSMConfig");
    }
}
