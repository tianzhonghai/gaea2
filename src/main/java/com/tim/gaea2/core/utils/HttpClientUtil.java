package com.tim.gaea2.core.utils;

import com.alibaba.fastjson.JSON;
import org.apache.http.Consts;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;

import java.net.URI;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by tianzhonghai on 2017/6/8.
 */
public class HttpClientUtil {
    // 超时时间豪秒
    private static final Integer CONN_TIME_OUT = 2000;
    private static final Integer SOCKET_TIME_OUT = 10000;
    private static final Integer DEFAULT_MAX_PER_ROUTE = 300;
    private static final Integer MAX_TOTAL = 900;
    private static RequestConfig requestConfig;
    private static HttpClient httpClient;
    private static Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);

    static {
        requestConfig = RequestConfig.custom().setConnectionRequestTimeout(CONN_TIME_OUT)
                .setSocketTimeout(SOCKET_TIME_OUT).build();

        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
        cm.setDefaultMaxPerRoute(DEFAULT_MAX_PER_ROUTE);
        cm.setMaxTotal(MAX_TOTAL);

        httpClient = HttpClients.custom().setConnectionManager(cm).setDefaultRequestConfig(requestConfig).build();
    }


    public static String requestGet(String url, Map<String, String> paramsMap, int timeout) throws Exception {
//        logger.info("GET request  url:{} params:{}", url, paramsMap);

        Long start = System.currentTimeMillis();

        List<NameValuePair> params = initParams(url, paramsMap);
        // Get请求
        HttpGet httpGet = new HttpGet(url);

        timeout = timeout <= 0 ? CONN_TIME_OUT : timeout;

        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(timeout).build();

        httpGet.setConfig(requestConfig);

        try {
            // 设置参数
            String str = EntityUtils.toString(new UrlEncodedFormEntity(params, Consts.UTF_8));
            httpGet.setURI(new URI(httpGet.getURI().toString() + "?" + str));
            // 发送请求
            HttpResponse response = httpClient.execute(httpGet);
//            logger.info("GET request  url:{} response:{} time:{}", url, response, System.currentTimeMillis() - start);
            // 获取返回数据
            String retStr = getSuccessRetFromResp(response, url, JSON.toJSONString(paramsMap));

            return retStr;
        } finally {
            httpGet.releaseConnection();
        }
    }

    public static String requestPost(String url, Map<String, String> paramsMap, int timeout) throws Exception {
//        logger.info("POST request  url:{} params:{}", url, paramsMap);
        Long start = System.currentTimeMillis();

        List<NameValuePair> params = initParams(url, paramsMap);

        HttpPost httpPost = new HttpPost(url);

        timeout = timeout <= 0 ? CONN_TIME_OUT : timeout;

        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(timeout).build();

        httpPost.setConfig(requestConfig);

        try {

            httpPost.setEntity(new UrlEncodedFormEntity(params, Charset.forName("UTF-8")));

            HttpResponse response = httpClient.execute(httpPost);

//            logger.info("POST request  url:{} response:{}  time:{}", url, response, System.currentTimeMillis() - start);

            String retStr = getSuccessRetFromResp(response, url, JSON.toJSONString(paramsMap));


            return retStr;
        } finally {
            httpPost.releaseConnection();
        }
    }

    /**
     * post json 格式数据
     *
     * @param url
     * @param params
     * @return
     * @throws Exception
     */
    public static String requestPostJsonStr(String url, String params, int timeout) throws Exception {

//        logger.info("POST request  url:{} params:{}", url, params);

        Long start = System.currentTimeMillis();

        timeout = timeout <= 0 ? CONN_TIME_OUT : timeout;

        HttpPost httpPost = new HttpPost(url);

        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(timeout).build();

        httpPost.setConfig(requestConfig);

        try {
            StringEntity entity = new StringEntity(params, Consts.UTF_8);
            entity.setContentType("application/json");

            httpPost.setEntity(entity);

            HttpResponse response = httpClient.execute(httpPost);

//            logger.info("POST request  url:{} response:{}  time:{}", url, response, System.currentTimeMillis() - start);

            String retStr = getSuccessRetFromResp(response, url, params);

            return retStr;
        } finally {
            httpPost.releaseConnection();
        }

    }

    /**
     * post json 格式数据
     *
     * @param url
     * @param obj
     * @return
     * @throws Exception
     */
    public static String requestPostJson(String url, Object obj, int timeout) throws Exception {
        String params = JSON.toJSONString(obj);
        return requestPostJsonStr(url, params, timeout);
    }

    private static String getSuccessRetFromResp(HttpResponse response, String url, String params) throws Exception {
        String retStr = "";
        // 检验状态码，如果成功接收数据
        int code = response.getStatusLine().getStatusCode();

        if (code == 200) {
            retStr = EntityUtils.toString(response.getEntity(), Consts.UTF_8);
        } else {
            throw new RuntimeException(String.format("Http request error:%s, url:%s, params:%s", response, url, params));
        }

//        logger.info("Http request retStr:{}. url:{}", retStr, url);
        return retStr;
    }

    private static List<NameValuePair> initParams(String url, Map<String, String> paramsMap) {

        List<NameValuePair> params = new ArrayList<NameValuePair>();
        if (paramsMap == null)
            return params;
        Iterator<String> iterator = paramsMap.keySet().iterator();


        while (iterator.hasNext()) {
            String key = iterator.next();
            params.add(new BasicNameValuePair(key, paramsMap.get(key)));
        }
        return params;
    }
}
