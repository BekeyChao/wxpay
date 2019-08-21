package xyz.bekey.wxpay;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.DefaultHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.security.GeneralSecurityException;
import java.security.KeyStore;

/**
 * http 工具
 * 基于 org.apache.httpcomponents
 */
class HttpUtils {

    private WxpayConfig wxpayConfig;

    HttpUtils(WxpayConfig config) {
        this.wxpayConfig = config;
    }

    String doPost(String url, String xmlBody ,boolean needCert) {
        CloseableHttpClient httpClient ;
        if (needCert) {
            httpClient = initClient();
        } else {
            httpClient = HttpClients.custom().build();
        }

        HttpPost httpPost = new HttpPost(url);
        // 得指明使用UTF-8编码，否则到API服务器XML的中文不能被成功识别
        StringEntity postEntity = new StringEntity(xmlBody, Charset.forName("UTF-8"));
        httpPost.addHeader("Content-Type", "text/xml");
        httpPost.setEntity(postEntity);
        // 根据默认超时限制初始化requestConfig
        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(wxpayConfig.getSocketTimeout())
                .setConnectTimeout(wxpayConfig.getConnectTimeout())
                .build();
        // 设置请求器的配置
        httpPost.setConfig(requestConfig);

        try {
            HttpResponse response = null;
            try {
                // httpClient = httpClientThreadLocal.get();
                if (httpClient != null)
                    response = httpClient.execute(httpPost);
            }  catch (IOException e) {
                throw new FailToPayException("请求链接异常", e);
            }
            if (response == null)
                throw new FailToPayException("请求响应异常");
            HttpEntity entity = response.getEntity();
            try {
                return EntityUtils.toString(entity, "UTF-8");
            }  catch (IOException e) {
                throw new FailToPayException("响应读取异常", e);
            }
        } finally {
            httpPost.abort();
        }

    }

    private CloseableHttpClient initClient()  {
        SSLContext sslcontext;
        try {
            sslcontext = SSLContextBuilder.create()
//                    .custom()
                    .loadKeyMaterial(initKeyStore(), wxpayConfig.getMch_id().toCharArray())
                    .build();
        } catch (GeneralSecurityException e) {
            throw new FailToPayException("加载证书失败", e);
        }

        // 指定TLS版本
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                sslcontext, new String[] { "TLSv1" }, null,
                new DefaultHostnameVerifier());
        // 设置httpclient的SSLSocketFactory
        CloseableHttpClient httpClient = HttpClients
                .custom()
                .setSSLSocketFactory(sslsf)
                .build();
        return httpClient;
    }

    private KeyStore initKeyStore() {
        FileInputStream instream = null;
        try {
            // 指定读取证书格式为PKCS12
            KeyStore keyStore = KeyStore.getInstance("PKCS12");
            String path = wxpayConfig.getCertPath();
            if (path == null || "".equals(path)) {
                throw new FailToPayException("证书路径错误");
            }
            instream = new FileInputStream(new File(path));
            keyStore.load(instream, wxpayConfig.getMch_id().toCharArray());
            return keyStore;
        } catch (Exception e) {
            throw new FailToPayException("加载证书错误", e);
        } finally {
            if (instream != null) {
                try {
                    instream.close();
                } catch (IOException e) {
                    // ignore it
                }
            }
        }
    }
}
