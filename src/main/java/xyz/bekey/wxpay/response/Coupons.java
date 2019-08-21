package xyz.bekey.wxpay.response;

import java.util.List;

/**
 * 微信 代金券 特殊处理逻辑
 */
public abstract class Coupons extends ResponseBase{

    /**
     * 以下须经特殊处理 是优惠券相关字段
     */
    // 代金券类型 有序
    private List<String> coupon_types;
    // 代金券ID
    private List<String>  coupon_ids;
    // 单个代金券支付金额
    private List<Integer>  coupon_fees;

    // 代金券退款金额
    private List<Integer> coupon_refund_fees;
    // 代金券退款ID
    private List<String> coupon_refund_ids;

    public List<String> getCoupon_types() {
        return coupon_types;
    }

    public void setCoupon_types(List<String> coupon_types) {
        this.coupon_types = coupon_types;
    }

    public List<String> getCoupon_ids() {
        return coupon_ids;
    }

    public void setCoupon_ids(List<String> coupon_ids) {
        this.coupon_ids = coupon_ids;
    }

    public List<Integer> getCoupon_fees() {
        return coupon_fees;
    }

    public void setCoupon_fees(List<Integer> coupon_fees) {
        this.coupon_fees = coupon_fees;
    }

    public List<Integer> getCoupon_refund_fees() {
        return coupon_refund_fees;
    }

    public void setCoupon_refund_fees(List<Integer> coupon_refund_fees) {
        this.coupon_refund_fees = coupon_refund_fees;
    }

    public List<String> getCoupon_refund_ids() {
        return coupon_refund_ids;
    }

    public void setCoupon_refund_ids(List<String> coupon_refund_ids) {
        this.coupon_refund_ids = coupon_refund_ids;
    }
}
