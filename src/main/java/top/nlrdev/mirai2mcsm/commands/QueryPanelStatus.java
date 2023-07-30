package top.nlrdev.mirai2mcsm.commands;

import net.mamoe.mirai.console.command.CommandSender;
import net.mamoe.mirai.console.command.java.JRawCommand;
import net.mamoe.mirai.internal.deps.okhttp3.Request;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.MessageChainBuilder;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;
import top.nlrdev.mirai2mcsm.Mirai2MCSM;
import top.nlrdev.mirai2mcsm.configs.MCSMConfig;
import top.nlrdev.mirai2mcsm.utils.RequestHandler;

public class QueryPanelStatus extends JRawCommand {
    public static final QueryPanelStatus INSTANCE = new QueryPanelStatus();

    private QueryPanelStatus() {
        super(Mirai2MCSM.INSTANCE, "queryStatus","面板状态","查询面板状态");
        setUsage("(/)面板状态");
        setDescription("查询面板状态");
        setPrefixOptional(true);
    }

    @Override
    public void onCommand(@NotNull CommandSender sender, @NotNull MessageChain args) {
        String url = MCSMConfig.INSTANCE.getApiUrl.get()+"/api/overview?apikey="+MCSMConfig.INSTANCE.getApiKey.get();
        Request request = new Request.Builder().url(url).get().build();
        JSONObject json = RequestHandler.callRequest(request);
        JSONObject data = null;
        if (json == null||json.getInt("status")!=200) {
            sender.sendMessage("状态：运行异常\n返回值："+json.getInt("status"));
            return;
        }
        data = json.getJSONObject("data");
        MessageChainBuilder messageChainBuilder = new MessageChainBuilder()
                .append("---==面板状态==---")
                .append("状态：正常运行\n")
                .append("面板版本：").append(data.getString("version")).append("\n")
                .append("面板系统CPU负载：").append(String.valueOf(data.getJSONObject("process").getFloat("cpu"))).append("\n")
                .append("");
        sender.sendMessage(messageChainBuilder.build());
    }
}
