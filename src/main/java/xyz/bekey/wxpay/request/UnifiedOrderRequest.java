package xyz.bekey.wxpay.request;

import com.alibaba.fastjson.JSON;
import xyz.bekey.wxpay.FailToPayException;
import xyz.bekey.wxpay.response.UnifiedOrderResponse;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

/**
 * 统一下单接口请求
 * 该实体类针对接口要求作了大量的修改，不是纯粹的pojo类，可以正常序列化，但不能反序列化
 */
public class UnifiedOrderRequest extends RequestBase<UnifiedOrderResponse> {

    // 不需要证书
    public boolean needCert() {
        return false;
    }

    public Class<UnifiedOrderResponse> responseType() {
        return UnifiedOrderResponse.class;
    }

    public String apiUrl() {
        return "pay/unifiedorder";
    }

    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

    /**
     * 以下为必须参数
     */
    // 订单总金额，单位为分
    private int total_fee;
    // 商户系统内部订单号
    private String out_trade_no;
    // 商品描述
    private String body;
    // 终端IP
    private String spbill_create_ip;

    // 支付类型
    private TradeType trade_type;

    // 通知地址
    private String notify_url;
    /**
     * 以下为指定场景必须参数
     */
    // trade_type=JSAPI 时（即JSAPI支付），此参数必传
    private String openid;

    // trade_type=NATIVE时，此参数必传。此参数为二维码中包含的商品ID
    private String product_id;

    // 标价币种 默认CNY
    private String fee_type;


    // 该字段常用于线下活动时的场景信息上报
    // H5 支付必填
    private Map<String, Object> scene_info;
    /**
     * 以下为非必须参数
     */
    // 商品详情
    private String detail;
    // 附加数据
    private String attach;
    // 设备号
    private String device_info;
    // 交易起始时间 yyyyMMddHHmmss
    private LocalDateTime time_start;
    // 交易结束时间 yyyyMMddHHmmss
    private LocalDateTime time_expire;
    // 订单优惠标记
    private String goods_tag;
    // 上传此参数no_credit--可限制用户不能使用信用卡支付
    private Boolean limit_pay;

    // Y，传入Y时，支付成功消息和支付详情页将出现开票入口
    private Boolean receipt;

    public int getTotal_fee() {
        if (total_fee <= 0) {
            throw new FailToPayException("支付金不能为空");
        }
        return total_fee;
    }

    public void setTotal_fee(int total_fee) {
        if (total_fee <= 0) {
            throw new FailToPayException("支付金额必须大于0");
        }
        this.total_fee = total_fee;
    }

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getSpbill_create_ip() {
        return spbill_create_ip;
    }

    public void setSpbill_create_ip(String spbill_create_ip) {
        this.spbill_create_ip = spbill_create_ip;
    }

    public String getTrade_type() {
        return trade_type.name();
    }

    public void setTrade_type(TradeType trade_type) {
        this.trade_type = trade_type;
    }

    public String getNotify_url() {
        return notify_url;
    }

    public void setNotify_url(String notify_url) {
        this.notify_url = notify_url;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getFee_type() {
        return fee_type;
    }

    public void setFee_type(String fee_type) {
        this.fee_type = fee_type;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getAttach() {
        return attach;
    }

    public void setAttach(String attach) {
        this.attach = attach;
    }

    public String getDevice_info() {
        return device_info;
    }

    public void setDevice_info(String device_info) {
        this.device_info = device_info;
    }

    public String getTime_start() {
//        return time_start;
        if (time_start != null) {
            return formatter.format(time_start);
        }
        return null;
    }

    public void setTime_start(LocalDateTime time_start) {
        this.time_start = time_start;
    }

    public String getTime_expire() {
        if (time_expire != null) {
            return formatter.format(time_expire);
        }
        return null;
    }

    public void setTime_expire(LocalDateTime time_expire) {
        this.time_expire = time_expire;
    }

    public String getGoods_tag() {
        return goods_tag;
    }

    public void setGoods_tag(String goods_tag) {
        this.goods_tag = goods_tag;
    }

    public String getLimit_pay() {
        if (limit_pay != null) {
            return limit_pay ? "no_credit": null;
        }
        return null;
    }

    public void setLimit_pay(boolean limit_pay) {
        this.limit_pay = limit_pay;
    }

    public String getReceipt() {
        if (receipt != null) {
            return receipt ? "Y": null;
        }
        return null;
    }

    public void setReceipt(boolean receipt) {
        this.receipt = receipt;
    }

    public String getScene_info() {
        if (scene_info != null) {
            return JSON.toJSONString(scene_info);
        }
        return null;
    }

    public void setScene_info(Map<String, Object> scene_info) {
        this.scene_info = scene_info;
    }
}
