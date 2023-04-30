package top.nlrdev.mirai2mcsm.utils;

import net.mamoe.mirai.internal.deps.okhttp3.OkHttpClient;
import net.mamoe.mirai.utils.MiraiLogger;
import org.json.JSONObject;
import top.nlrdev.mirai2mcsm.Mirai2MCSM;

public class RespondHandler {
    private MiraiLogger logger = Mirai2MCSM.INSTANCE.getLogger();
    private OkHttpClient httpClient = Mirai2MCSM.globalHttpClient;
    public JSONObject handleRespond(String respond){
        if(respond==null){
            logger.error("请求返回为空");
            return null;
        }
        JSONObject json = new JSONObject(respond);
        switch (json.getString("status")) {
            case"400":
                logger.error("请求参数不正确");
                return null;
            case"403":
                logger.error("无权限");
                return null;
            case"500":
                logger.error("内部服务器错误");
                return null;
        }
        return json;
    }
}
