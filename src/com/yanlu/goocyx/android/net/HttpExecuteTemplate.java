package com.yanlu.goocyx.android.net;

import com.yanlu.goocyx.android.common.error.BaseException;
import com.yanlu.goocyx.android.net.http.HttpMethod;

import java.util.Map;

/**
 * http请求执行模板
 *
 * @author tianxiang
 * @date 2012-10-17 下午2:05:25
 */
public class HttpExecuteTemplate {
    /**
     * 执行http请求
     *
     * @param:url
     * @param:param
     * @param:httpMethod
     * @return
     */
    public static String execute(String url) throws BaseException {
        return new ApacheHttpClient().executeNormalTask(HttpMethod.GET, url, null);
    }

    public static String execute(String url, Map<String, String> param) throws BaseException {
        return new ApacheHttpClient().executeNormalTask(HttpMethod.GET, url, param);
    }

    public static String execute(HttpMethod httpMethod, String url, Map<String, String> param) throws BaseException {
        return new ApacheHttpClient().executeNormalTask(httpMethod, url, param);

    }

}
