package top.nlrdev.mirai2mcsm.utils;

import net.mamoe.mirai.internal.deps.okhttp3.OkHttpClient;
import net.mamoe.mirai.internal.deps.okhttp3.Request;
import net.mamoe.mirai.internal.deps.okhttp3.Response;
import net.mamoe.mirai.utils.MiraiLogger;
import org.json.JSONObject;
import top.nlrdev.mirai2mcsm.Mirai2MCSM;

import java.io.IOException;

public class RequestHandler {
    private static MiraiLogger logger = Mirai2MCSM.INSTANCE.getLogger();
    private static OkHttpClient httpClient = Mirai2MCSM.globalHttpClient;
    public RequestHandler INSTANCE = this;


    /**
     * 请求返回处理
     * @return JsonObject
     */
    public static JSONObject handleRequest(Request request){
        Response response = null;
        try{
            response = httpClient.newCall(request).execute();
        }catch (RuntimeException | IOException e){
            logger.error("请求发起失败");
            throw new RuntimeException(e);
        }
        if(response.body() == null){
            logger.error("请求返回为空");
            return null;
        }
        JSONObject json = null;
        try {
            json = new JSONObject(response.body().string());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        switch (json.getInt("status")) {
            case 400:
                logger.error("请求参数不正确");
                return null;
            case 403:
                logger.error("无权限");
                return null;
            case 500:
                logger.error("内部服务器错误");
                return null;
        }
        return json;
    }
}
