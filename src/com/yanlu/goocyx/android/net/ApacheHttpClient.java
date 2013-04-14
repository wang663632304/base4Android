/**
 * 
 */
package com.yanlu.goocyx.android.net;

import android.text.TextUtils;
import com.yanlu.goocyx.android.common.error.BaseException;
import com.yanlu.goocyx.android.common.error.ErrorInfo;
import com.yanlu.goocyx.android.common.util.Log2;
import com.yanlu.goocyx.android.common.util.Utility;
import com.yanlu.goocyx.android.net.http.HttpMethod;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.util.Map;
import java.util.zip.GZIPInputStream;

/**
 * User: captain_miao
 * Date: 13-4-6
 */
public class ApacheHttpClient {
	private static final String CHARSET = HTTP.UTF_8;
    private static final int CONNECT_TIMEOUT = 10 * 1000;
    private static final int READ_TIMEOUT = 10 * 1000;




    public String executeNormalTask(HttpMethod httpMethod, String url, Map<String, String> param) throws BaseException {
        switch (httpMethod) {
            case POST:
                return doPost(url, param);
            case GET:
                return doGet(url, param);
        }
        return "";
    }

    private static Proxy getProxy() {
        String proxyHost = System.getProperty("http.proxyHost");
        String proxyPort = System.getProperty("http.proxyPort");
        if (!TextUtils.isEmpty(proxyHost) && !TextUtils.isEmpty(proxyPort))
            return new Proxy(java.net.Proxy.Type.HTTP, new InetSocketAddress(proxyHost, Integer.valueOf(proxyPort)));
        else
            return null;
    }

    public String doPost(String urlAddress, Map<String, String> param) throws BaseException {
         try {
             URL url = new URL(urlAddress);
             Proxy proxy = getProxy();
             HttpsURLConnection uRLConnection;
             if (proxy != null){
                 uRLConnection = (HttpsURLConnection) url.openConnection(proxy);
             } else {
                 uRLConnection = (HttpsURLConnection) url.openConnection();
             }

             uRLConnection.setDoInput(true);
             uRLConnection.setDoOutput(true);
             uRLConnection.setRequestMethod("POST");
             uRLConnection.setUseCaches(false);
             uRLConnection.setConnectTimeout(CONNECT_TIMEOUT);
             uRLConnection.setReadTimeout(READ_TIMEOUT);
             uRLConnection.setInstanceFollowRedirects(false);
             uRLConnection.setRequestProperty("Connection", "Keep-Alive");
             uRLConnection.setRequestProperty("Charset", "UTF-8");
             uRLConnection.setRequestProperty("Accept-Encoding", "gzip, deflate");
             uRLConnection.connect();

             DataOutputStream out = new DataOutputStream(uRLConnection.getOutputStream());
             out.write(Utility.encodeUrl(param).getBytes());
             out.flush();
             out.close();
             return handleResponse(uRLConnection);
         } catch (IOException e) {
             e.printStackTrace();
             throw new BaseException(ErrorInfo.NETWORK_TIME_OUT, e);
         }
     }


    public String doGet(String urlStr, Map<String, String> param) throws BaseException {
         InputStream is = null;
         try {

             StringBuilder urlBuilder = new StringBuilder(urlStr);
             urlBuilder.append("?").append(Utility.encodeUrl(param));
             URL url = new URL(urlBuilder.toString());
             Log2.d("","get request:" + url);
             Proxy proxy = getProxy();
             HttpURLConnection urlConnection;
             if (proxy != null)
                 urlConnection = (HttpURLConnection) url.openConnection(proxy);
             else
                 urlConnection = (HttpURLConnection) url.openConnection();

             urlConnection.setRequestMethod("GET");
             urlConnection.setDoOutput(false);
             urlConnection.setConnectTimeout(CONNECT_TIMEOUT);
             urlConnection.setReadTimeout(READ_TIMEOUT);
             urlConnection.setRequestProperty("Connection", "Keep-Alive");
             urlConnection.setRequestProperty("Charset", "UTF-8");
             urlConnection.setRequestProperty("Accept-Encoding", "gzip, deflate");

             urlConnection.connect();

             return handleResponse(urlConnection);
         } catch (IOException e) {
             e.printStackTrace();
             throw new BaseException(ErrorInfo.NETWORK_TIME_OUT, e);
         }
    }
    private String handleResponse(HttpURLConnection httpURLConnection) throws BaseException {
        int status = 0;
        try {
            status = httpURLConnection.getResponseCode();
        } catch (IOException e) {
            e.printStackTrace();
            httpURLConnection.disconnect();
            throw new BaseException(ErrorInfo.NETWORK_TIME_OUT, e);
        }

        if (status != HttpURLConnection.HTTP_OK) {
            return handleError(httpURLConnection);
        }

        return readResult(httpURLConnection);
    }

    private String readResult(HttpURLConnection urlConnection) throws BaseException {
        InputStream is = null;
        BufferedReader buffer = null;
        try {
            is = urlConnection.getInputStream();

            String content_encode = urlConnection.getContentEncoding();

            if (null != content_encode && !"".equals(content_encode) && content_encode.equals("gzip")) {
                is = new GZIPInputStream(is);
            }

            buffer = new BufferedReader(new InputStreamReader(is));
            StringBuilder strBuilder = new StringBuilder();
            String line;
            while ((line = buffer.readLine()) != null) {
                strBuilder.append(line);
            }
            Log2.d("", "result=" + strBuilder.toString());
            return strBuilder.toString();
        } catch (IOException e) {
            e.printStackTrace();
            throw new BaseException(ErrorInfo.NETWORK_TIME_OUT, e);
        } finally {
            Utility.closeSilently(is);
            Utility.closeSilently(buffer);
            urlConnection.disconnect();
        }

    }

    private String handleError(HttpURLConnection urlConnection) throws BaseException {

        String result = readError(urlConnection);
        String err = null;
        int errCode = 0;
        try {
            Log2.e("", "error=" + result);
            JSONObject json = new JSONObject(result);
            err = json.optString("error_description", "");
            if (TextUtils.isEmpty(err))
                err = json.getString("error");
            errCode = json.getInt("error_code");
            BaseException exception = new BaseException();
            exception.setError_code(errCode);
            exception.setOriError(err);
            throw exception;

        } catch (JSONException e) {
            e.printStackTrace();
        }


        return result;
    }

    private String readError(HttpURLConnection urlConnection) throws BaseException {
        InputStream is = null;
        BufferedReader buffer = null;

        try {
            is = urlConnection.getErrorStream();

            if (is == null) {

                throw new BaseException(ErrorInfo.UNKNOWN_NETWORK_ERROR);
            }

            String content_encode = urlConnection.getContentEncoding();

            if (null != content_encode && !"".equals(content_encode) && content_encode.equals("gzip")) {
                is = new GZIPInputStream(is);
            }

            buffer = new BufferedReader(new InputStreamReader(is));
            StringBuilder strBuilder = new StringBuilder();
            String line;
            while ((line = buffer.readLine()) != null) {
                strBuilder.append(line);
            }
            Log2.d("", "error result=" + strBuilder.toString());
            return strBuilder.toString();
        } catch (IOException e) {
            e.printStackTrace();
            throw new BaseException(ErrorInfo.NETWORK_TIME_OUT, e);
        } finally {
            Utility.closeSilently(is);
            Utility.closeSilently(buffer);
            urlConnection.disconnect();

        }

    }
}
