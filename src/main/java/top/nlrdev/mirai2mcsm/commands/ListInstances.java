package top.nlrdev.mirai2mcsm.commands;

import net.mamoe.mirai.console.command.CommandSender;
import net.mamoe.mirai.console.command.java.JRawCommand;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.message.data.ForwardMessageBuilder;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.MessageChainBuilder;
import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;
import top.nlrdev.mirai2mcsm.Mirai2MCSM;
import top.nlrdev.mirai2mcsm.configs.PluginConfig;
import top.nlrdev.mirai2mcsm.utils.MessageListener;
import top.nlrdev.mirai2mcsm.utils.RequestHandler;

import java.util.Objects;

public class ListInstances extends JRawCommand {
    public static final ListInstances INSTANCE = new ListInstances();
    public static final String URL = "/service/remote_service_instances";

    private ListInstances() {
        super(Mirai2MCSM.INSTANCE, "listInstances", "listinst", "实例列表");
        setUsage("/实例列表");
        setDescription("查询实例列表");
    }

    @Override
    public void onCommand(@NotNull CommandSender sender, @NotNull MessageChain args) {
        String uuid;
        if (args.isEmpty()) {
            if (Mirai2MCSM.remotes.size() != 1) {
                MessageListener.askForString(sender, "您要查询的守护进程的 UUID 是？", id -> execute(sender, args, id));
                return;
            }

            uuid = Mirai2MCSM.remotes.get(0);
        } else {
            uuid = args.get(0).contentToString();
        }

        execute(sender, args, uuid);
    }

    public static void execute(CommandSender sender, MessageChain args, String uuid) {
        String REQUEST = "&page_size=20&instance_name=&remote_uuid=" + uuid;
        if (args.size() != 2) {
            REQUEST += "&page=1";
        } else {
            REQUEST += "&page=" + args.get(1);
        }

        JSONObject json = RequestHandler.handleGetRequest(URL, REQUEST);
        switch (json.getInt("status")) {
            case 400 -> {
                sender.sendMessage("请求参数不正确，请检查 UUID 或页数是否正确");
                return;
            }

            case 403 -> {
                sender.sendMessage("您无权访问该守护进程");
                return;
            }

            case 500 -> {
                sender.sendMessage("服务器内部错误，请重试或联系管理员");
                return;
            }
        }

        System.out.println(json);

        JSONObject data = json.getJSONObject("data");
        JSONArray instData = data.getJSONArray("data");
        MessageChainBuilder messageChainBuilder = new MessageChainBuilder()
                .append("---==实例列表==---\n")
                .append("守护进程uuid：").append(uuid).append("\n");
        for (Object object : instData) {
            JSONObject obj = (JSONObject) object;
            messageChainBuilder.append("实例名称：").append(obj.getJSONObject("config").getString("nickname")).append("")
                    .append("    状态：");
            switch (obj.getInt("status")) {
                case -1 -> messageChainBuilder.append("未知\n");
                case 0 -> messageChainBuilder.append("已停止\n");
                case 1 -> messageChainBuilder.append("正在停止\n");
                case 2 -> messageChainBuilder.append("正在启动\n");
                case 3 -> messageChainBuilder.append("正在运行\n");
            }
            messageChainBuilder.append("    实例UUID：").append(obj.getString("instanceUuid")).append("\n");
        }
        messageChainBuilder.append("实例总数：").append(String.valueOf(instData.length()));

        if (PluginConfig.INSTANCE.useForwardMessage.get() && sender.getSubject() != null && sender.getSubject() instanceof Group) {
            ForwardMessageBuilder forwardMessageBuilder = new ForwardMessageBuilder(sender.getSubject()).add(Objects.requireNonNull(sender.getBot()).getId(), sender.getBot().getNick(), messageChainBuilder.build());
            sender.sendMessage(forwardMessageBuilder.build());
        } else {
            sender.sendMessage(messageChainBuilder.build());
        }
    }
}
