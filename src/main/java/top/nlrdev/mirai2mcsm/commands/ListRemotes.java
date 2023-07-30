package top.nlrdev.mirai2mcsm.commands;

import net.mamoe.mirai.console.command.CommandSender;
import net.mamoe.mirai.console.command.java.JRawCommand;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.MessageChainBuilder;
import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;
import top.nlrdev.mirai2mcsm.Mirai2MCSM;
import top.nlrdev.mirai2mcsm.utils.RequestHandler;

public class ListRemotes extends JRawCommand {
    public static final ListRemotes INSTANCE = new ListRemotes();
    String URL = "/service/remote_services_list";
    String QUERY = "";

    private ListRemotes() {
        super(Mirai2MCSM.INSTANCE, "listRemotes","listre","守护进程列表");
        setUsage("/守护进程列表");
        setDescription("查询守护进程列表");
        setPrefixOptional(false);
    }
    @Override
    public void onCommand(@NotNull CommandSender sender, @NotNull MessageChain args) {
        JSONObject obj =RequestHandler.handlePostRequest(URL,QUERY);
        System.out.println(obj);
        JSONArray data = obj.getJSONArray("data");
        MessageChainBuilder messageChainBuilder = new MessageChainBuilder();
        messageChainBuilder.append("---==守护进程列表==---\n")
                .append("守护进程总数：").append(String.valueOf(data.length())).append("\n");
        for (int i = 0; i < data.length(); i++) {
            JSONObject object = data.getJSONObject(i);
            messageChainBuilder.append("守护进程uuid：").append(object.getString("uuid"))
                    .append("\n    IP：").append(object.getString("ip")).append(":").append(String.valueOf(object.getInt("port")))
                    .append("\n    是否在线：");
            if(object.getBoolean("available")){
                messageChainBuilder.append("在线");
            }else{
                messageChainBuilder.append("离线");
            }
            if(i != data.length()-1){
                messageChainBuilder.append("\n");
            }
        }
        sender.sendMessage(messageChainBuilder.build());
    }
}

