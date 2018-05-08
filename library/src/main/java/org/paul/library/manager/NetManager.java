package org.paul.library.manager;

import android.util.Log;
import com.google.gson.Gson;
import org.paul.library.base.BaseBean;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

public class NetManager {
    private static final String TAG = NetManager.class.getSimpleName();
    private static final int READ_TIME = 4 * 1000;
    private static final int CONNECT_TIME = 4 * 1000;

    private static class Holder {
        private static NetManager instance = new NetManager();
    }

    private NetManager() {
    }

    public static NetManager getInstance() {
        return Holder.instance;
    }

    <T extends BaseBean> T post(String spec, Map<String, Object> params, Class<T> clz) {
        try {
            HttpURLConnection connection = initConnection(spec, "POST", params);
            Log.d(TAG,String.format("request [%1$s] start",spec));
            T t = handleResult(clz, connection);
            if (t != null) {
                return t;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
    <T extends BaseBean>T get(String spec,Class<T>clz){
        try {
            HttpURLConnection connection = initConnection(spec, "GET", null);
            Log.d(TAG,String.format("request [%1$s] start",spec));
            T t = handleResult(clz, connection);
            if (t != null) {
                return t;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private <T extends BaseBean> T handleResult(Class<T> clz, HttpURLConnection connection) throws IOException {
        int responseCode = connection.getResponseCode();
        if (responseCode == 200) {
            try {
                BufferedInputStream bis = new BufferedInputStream(connection.getInputStream());
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                int len;
                byte[] arr = new byte[1024];
                while ((len = bis.read(arr)) != -1) {
                    bos.write(arr, 0, len);
                    bos.flush();
                }
                bos.close();
                String res = bos.toString("utf-8");
                return new Gson().fromJson(res, clz);
            } catch (com.google.gson.JsonSyntaxException e) {
                e.printStackTrace();
            } catch (IllegalStateException e) {
                e.printStackTrace();
            }
        }
        Log.d(TAG,String.format("bean of [%1$s] response code = [%2$d]",clz.getSimpleName(),responseCode));
        return null;
    }

    private HttpURLConnection initConnection(String spec, String method, Map<String, Object> params) throws IOException {
        HttpURLConnection connection = null;
        URL url = new URL(spec);
        if (isHttps(spec)) {
            connection = (HttpsURLConnection) url.openConnection();
//            trustAllHosts((HttpsURLConnection) connection);
        } else {
            connection = (HttpURLConnection) url.openConnection();
        }
        connection.setRequestMethod(method);
        connection.setConnectTimeout(CONNECT_TIME);
        connection.setReadTimeout(READ_TIME);
        connection.setUseCaches(false);

        initHeader(connection);
        initBody(connection, params);
        return connection;
    }

    private HttpURLConnection initHeader(HttpURLConnection connection) {
        connection.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/62.0.3202.75 Safari/537.36");
        connection.addRequestProperty("Content-Type", "application/json;charset=UTF-8");
        connection.addRequestProperty("Connection", "keep-alive");
        connection.addRequestProperty("Accept-Language", "en-US");
        connection.addRequestProperty("Accept", "application/json, text/plain, */*");
        return connection;
    }

    private HttpURLConnection initBody(HttpURLConnection connection, Map<String, Object> params) throws IOException {
        if (null != params) {
            connection.setDoOutput(true);
            PrintWriter printWriter = new PrintWriter(connection.getOutputStream());
            printWriter.write(createParams(params));
            printWriter.flush();
        }
        return connection;
    }

    private boolean isHttps(String spec) {
        return spec.toLowerCase().startsWith("https");
    }

    private static String createParams(Map<String, Object> params) {
        String ret = new Gson().toJson(params);
        return ret;
    }
}
