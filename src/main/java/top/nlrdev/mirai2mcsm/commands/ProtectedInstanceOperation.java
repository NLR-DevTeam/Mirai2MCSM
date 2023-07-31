package top.nlrdev.mirai2mcsm.commands;

import net.mamoe.mirai.console.command.CommandSender;
import net.mamoe.mirai.console.command.java.JRawCommand;
import net.mamoe.mirai.message.data.MessageChain;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;
import top.nlrdev.mirai2mcsm.Mirai2MCSM;
import top.nlrdev.mirai2mcsm.utils.RequestHandler;

public class ProtectedInstanceOperation extends JRawCommand {
    public static final ProtectedInstanceOperation INSTANCE = new ProtectedInstanceOperation();
    public static final String OPEN_INSTANCE_URL = "/protected_instance/open";
    public static final String STOP_INSTANCE_URL = "/protected_instance/stop";
    public static final String KILL_INSTANCE_URL = "/protected_instance/kill";
    public static final String RESTART_INSTANCE_URL = "/protected_instance/restart";

    private ProtectedInstanceOperation() {
        super(Mirai2MCSM.INSTANCE, "operateInstance", "opinst", "实例操作");
        setUsage("/实例操作");
        setDescription("操作一个实例");
    }

    @Override
    public void onCommand(@NotNull CommandSender sender, @NotNull MessageChain args) {
        switch (args.size()) {
            case 0 -> {
                sender.sendMessage("操作类型不正确");
                return;
            }

            case 1 -> {
                sender.sendMessage("需要 uuid 参数");
                return;
            }

            case 2 -> {
                if (Mirai2MCSM.remotes.size() != 1) {
                    sender.sendMessage("守护进程不止一个，请同时输入守护进程 UUID 与实例 UUID");
                    return;
                }
            }
        }

        String operation = args.get(0).contentToString();
        String remoteUUID = null;
        String UUID = null;

        if (Mirai2MCSM.remotes.size() == 1) {
            if (args.size() == 2 || args.size() == 3) {
                UUID = args.get(1).contentToString();
                remoteUUID = Mirai2MCSM.getRemotesUUID("");
            }
        } else {
            UUID = args.get(1).contentToString();
            remoteUUID = Mirai2MCSM.getRemotesUUID(args.get(2).contentToString());
        }

        String REQUEST = "&uuid=" + UUID + "&remote_uuid=" + remoteUUID;
        JSONObject response = switch (operation) {
            case "open" -> RequestHandler.handleGetRequest(OPEN_INSTANCE_URL, REQUEST);
            case "stop" -> RequestHandler.handleGetRequest(STOP_INSTANCE_URL, REQUEST);
            case "kill" -> RequestHandler.handleGetRequest(KILL_INSTANCE_URL, REQUEST);
            case "restart" -> RequestHandler.handleGetRequest(RESTART_INSTANCE_URL, REQUEST);
            default -> {
                sender.sendMessage("操作类型不正确！");
                yield null;
            }
        };

        if (response == null) {
            return;
        }

        switch (response.getInt("status")) {
            case 200 -> sender.sendMessage("操作成功");
            case 400 -> sender.sendMessage("操作失败，请求参数不正确");
            case 403 -> sender.sendMessage("操作失败，无权限");
            case 500 -> sender.sendMessage("操作失败，服务器内部错误");
        }
    }
}
