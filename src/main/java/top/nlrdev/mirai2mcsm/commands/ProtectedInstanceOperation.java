package top.nlrdev.mirai2mcsm.commands;

import net.mamoe.mirai.console.command.CommandSender;
import net.mamoe.mirai.console.command.java.JRawCommand;
import net.mamoe.mirai.message.data.MessageChain;
import org.jetbrains.annotations.NotNull;
import top.nlrdev.mirai2mcsm.Mirai2MCSM;

public class ProtectedInstanceOperation extends JRawCommand {
    public static final ProtectedInstanceOperation INSTANCE = new ProtectedInstanceOperation();
    String OPEN_INSTANCE_URL = "/protected_instance/open";
    String STOP_INSTANCE_URL = "/protected_instance/stop";
    String KILL_INSTANCE_URL = "/protected_instance/kill";
    String RESTART_INSTANCE_URL = "/protected_instance/restart";

    private ProtectedInstanceOperation() {
        super(Mirai2MCSM.INSTANCE, "operateInstance","opinst","实例操作");
        setUsage("/实例操作");
        setDescription("操作一个实例");
        setPrefixOptional(false);
    }
    @Override
    public void onCommand(@NotNull CommandSender sender, @NotNull MessageChain args) {

    }

}
