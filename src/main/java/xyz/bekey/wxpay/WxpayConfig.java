package xyz.bekey.wxpay;

public class WxpayConfig {

    private String host = "https://api.mch.weixin.qq.com/";
    // 微信支付应急地址 该属性暂时没用上
    private String host_backup = "https://api2.mch.weixin.qq.com/";

    private String appid;
    private String mch_id;
    private String key;
    private String certPath;
    private SignType signType;

    /**
     * 自行通过set方法设置
     */
    private int socketTimeout = 10000;
    private int connectTimeout = 10000;

    /**
     * 简易初始化，host地址及sign类型使用默认值
     * @param certPath 在调用需证书接口时必传正确的值，建议传cert证书的绝对路径
     */
    public WxpayConfig(String appid, String mch_id, String key, String certPath) {
        this.appid = appid;
        this.mch_id = mch_id;
        this.key = key;
        this.certPath = certPath;
        this.signType = SignType.MD5;
    }

    public WxpayConfig(String appid, String mch_id, String key, String certPath, String host, SignType signType) {
        this(appid, mch_id, key, certPath);
        this.host = host;
        this.signType = signType;
    }

    public String getAppid() {
        return this.appid;
    }

    public String getMch_id() {
        return this.mch_id;
    }

    public String getKey() {
        return this.key;
    }

    public String getHost() {
        return this.host;
    }

    public void setHost(String host) {
        if (host != null)
            this.host = host;
    }

    public int getSocketTimeout() {
        return socketTimeout;
    }

    public int getConnectTimeout() {
        return connectTimeout;
    }

    public void setSocketTimeout(int socketTimeout) {
        this.socketTimeout = socketTimeout;
    }

    public void setConnectTimeou(int connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    public String getCertPath() {
        return certPath;
    }

    public SignType getSignType() {
        return signType;
    }

    public void setSignType(SignType signType) {
        this.signType = signType;
    }
}
