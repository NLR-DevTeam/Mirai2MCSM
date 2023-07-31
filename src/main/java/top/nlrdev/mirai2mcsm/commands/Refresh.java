package top.nlrdev.mirai2mcsm.commands;

import net.mamoe.mirai.console.command.CommandSender;
import net.mamoe.mirai.console.command.java.JRawCommand;
import net.mamoe.mirai.message.data.MessageChain;
import org.jetbrains.annotations.NotNull;
import top.nlrdev.mirai2mcsm.Mirai2MCSM;

public class Refresh extends JRawCommand {
    public static final Refresh INSTANCE = new Refresh();

    private Refresh() {
        super(Mirai2MCSM.INSTANCE, "refresh", "刷新", "刷新缓存");
        setUsage("/刷新");
        setDescription("刷新缓存中的实例与守护进程信息");
        setPrefixOptional(false);
    }

    @Override
    public void onCommand(@NotNull CommandSender sender, @NotNull MessageChain args) {
        Mirai2MCSM.refreshInfo();
        sender.sendMessage("成功刷新");//ToDo:加判断 6
    }
}
