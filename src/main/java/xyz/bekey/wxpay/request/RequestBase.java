package xyz.bekey.wxpay.request;

import xyz.bekey.wxpay.response.ResponseBase;

public abstract class RequestBase<T extends ResponseBase> {
//    // 公众账号ID 通过配置文件设置
//    private String appid;
//    // 商户号  通过配置文件设置
//    private String mch_id;
//    // 随机字符串 通过框架自动配置
//    private String nonce_str;
//    // 签名 通过框架自动设置
//    private String sign;
//    // 签名类型 默认MD5 支持HMAC-SHA256
//    private String sign_type;

    /**
     * 标记接口是否需要证书
     * @return
     */
    public abstract boolean needCert();

    /**
     * 提供响应类型，方便反序列化
     * @return
     */
    public abstract Class<T> responseType();

    /**
     * 提供接口请求地址 排除host前缀
     * @return
     */
    public abstract String apiUrl();

}
