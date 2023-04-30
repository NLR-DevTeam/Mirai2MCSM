package top.nlrdev.mirai2mcsm.commands;

import net.mamoe.mirai.console.command.CommandSender;
import net.mamoe.mirai.console.command.java.JRawCommand;
import net.mamoe.mirai.message.data.MessageChain;
import org.jetbrains.annotations.NotNull;
import top.nlrdev.mirai2mcsm.Mirai2MCSM;

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

    }
}
