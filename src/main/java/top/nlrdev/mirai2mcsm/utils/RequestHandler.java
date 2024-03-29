package top.nlrdev.mirai2mcsm.utils;

import net.mamoe.mirai.internal.deps.okhttp3.OkHttpClient;
import net.mamoe.mirai.internal.deps.okhttp3.Request;
import net.mamoe.mirai.internal.deps.okhttp3.Response;
import net.mamoe.mirai.utils.MiraiLogger;
import org.jetbrains.annotations.Nullable;
import org.json.JSONObject;
import top.nlrdev.mirai2mcsm.Mirai2MCSM;
import top.nlrdev.mirai2mcsm.configs.MCSMConfig;
import top.nlrdev.mirai2mcsm.configs.PluginConfig;

import java.io.IOException;

public class RequestHandler {
    private static final MiraiLogger logger = Mirai2MCSM.INSTANCE.getLogger();
    private static final OkHttpClient httpClient = Mirai2MCSM.globalHttpClient;
    public RequestHandler INSTANCE = this;

    public static JSONObject handleGetRequest(String inURL, @Nullable String query) {
        String url = MCSMConfig.INSTANCE.apiUrl + "/api" + inURL + "?apikey=" + MCSMConfig.INSTANCE.apiKey + query;
       if (PluginConfig.INSTANCE.isDebugMode.get()) System.out.println(url);
        Request request = new Request.Builder().url(url).get().build();
        return callRequest(request);
    }

    /**
     * 请求及返回处理
     *
     * @return JsonObject
     */
    public static JSONObject callRequest(Request request) {
        Response response;
        try {
            response = httpClient.newCall(request).execute();
        } catch (RuntimeException | IOException e) {
            logger.error("请求发起失败");
            throw new RuntimeException(e);
        }

        if (response.body() == null) {
            logger.error("请求返回为空");
            return null;
        }

        JSONObject json = null;
        try {
            json = new JSONObject(response.body().string());
        } catch (IOException e) {
            if (PluginConfig.INSTANCE.isDebugMode.get()) throw new RuntimeException(e);
        }
        if (PluginConfig.INSTANCE.isDebugMode.get()) {
            switch (json.getInt("status")) {
                case 400 -> {
                    logger.error("请求参数不正确");
                    return null;
                }

                case 403 -> {
                    logger.error("没有权限");
                    return null;
                }

                case 500 -> {
                    logger.error("内部服务器错误");
                    return null;
                }
            }
        }
        return json;
    }
}
