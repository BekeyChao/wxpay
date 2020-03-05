package xyz.bekey.wxpay.response;

/**
 * 微信支付调起信息
 */
public class WechatPrePay {
    private String timestamp;

    private String nonceStr;

//    @JSONField(name = "package")
    private String packag;

    private String signType;

    private String paySign;

    private String error;

    private boolean success;

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getNonceStr() {
        return nonceStr;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }

    public String getPackage() {
        return packag;
    }

    public void setPackage(String packag) {
        this.packag = packag;
    }

    public String getSignType() {
        return signType;
    }

    public void setSignType(String signType) {
        this.signType = signType;
    }

    public String getPaySign() {
        return paySign;
    }

    public void setPaySign(String paySign) {
        this.paySign = paySign;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
