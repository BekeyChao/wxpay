package xyz.bekey.wxpay.response;

public abstract class ResponseBase {
    // 通信标识 SUCCESS/FAIL
    private String return_code;

    // 当return_code为FAIL时返回信息为错误原因
    private String return_msg;

    // appid
    private String appid;

    // 商户号
    private String mch_id;

    // 随机字符串
    private String nonce_str;

    // 签名
    private String sign;

    // SUCCESS/FAIL
    private String result_code;

    /**
     *  result_code 为 fail 时有
     */
    private String err_code;

    private String err_code_des;

    public boolean isSuccess() {
        // result_code 一定有 err_code不一定有
        return "SUCCESS".equals(result_code)
                && !"fail".equals(err_code);
    }

    public String getReturn_code() {
        return return_code;
    }

    public void setReturn_code(String return_code) {
        this.return_code = return_code;
    }

    public String getReturn_msg() {
        return return_msg;
    }

    public void setReturn_msg(String return_msg) {
        this.return_msg = return_msg;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getMch_id() {
        return mch_id;
    }

    public void setMch_id(String mch_id) {
        this.mch_id = mch_id;
    }

    public String getNonce_str() {
        return nonce_str;
    }

    public void setNonce_str(String nonce_str) {
        this.nonce_str = nonce_str;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getResult_code() {
        return result_code;
    }

    public void setResult_code(String result_code) {
        this.result_code = result_code;
    }

    public String getErr_code() {
        return err_code;
    }

    public void setErr_code(String err_code) {
        this.err_code = err_code;
    }

    public String getErr_code_des() {
        return err_code_des;
    }

    public void setErr_code_des(String err_code_des) {
        this.err_code_des = err_code_des;
    }
}
