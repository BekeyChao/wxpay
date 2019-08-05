package xyz.bekey.wxpay.response;

/**
 * 统一下单接口响应
 */
public class UnifiedorderResponse extends ResponseBase{

    /**
     * 以下信息必然有
     */
    // JSAPI -JSAPI支付
    // NATIVE -Native支付
    // APP -APP支付
    private String trade_type;

    // 预支付交易会话标识
    private String prepay_id;

    /**
     * 以下不一定有
     */
    // 终端设备号(门店号或收银设备ID)，注意：PC网页或公众号内支付请传"WEB"
    private String device_info;

    // trade_type=NATIVE时有返回
    private String code_url;

    public String getTrade_type() {
        return trade_type;
    }

    public void setTrade_type(String trade_type) {
        this.trade_type = trade_type;
    }

    public String getPrepay_id() {
        return prepay_id;
    }

    public void setPrepay_id(String prepay_id) {
        this.prepay_id = prepay_id;
    }

    public String getDevice_info() {
        return device_info;
    }

    public void setDevice_info(String device_info) {
        this.device_info = device_info;
    }

    public String getCode_url() {
        return code_url;
    }

    public void setCode_url(String code_url) {
        this.code_url = code_url;
    }
}
