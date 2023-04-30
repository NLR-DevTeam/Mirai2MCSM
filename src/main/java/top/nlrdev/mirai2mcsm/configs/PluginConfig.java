package top.nlrdev.mirai2mcsm.configs;

import net.mamoe.mirai.console.data.Value;
import net.mamoe.mirai.console.data.java.JavaAutoSavePluginConfig;

public class PluginConfig extends JavaAutoSavePluginConfig {
    public final static PluginConfig INSTANCE = new PluginConfig();

    private PluginConfig() {
        super("Config");
    }
}