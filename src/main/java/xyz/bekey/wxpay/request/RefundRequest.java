package xyz.bekey.wxpay.request;

import xyz.bekey.wxpay.response.RefundResponse;

public class RefundRequest extends RequestBase<RefundResponse>{

    @Override
    public boolean needCert() {
        return true;
    }

    @Override
    public Class<RefundResponse> responseType() {
        return RefundResponse.class;
    }

    @Override
    public String apiUrl() {
        return "secapi/pay/refund";
    }

    /**
     * 二选一
     */
    private String transaction_id;

    private String out_trade_no;

    /**
     * 必须
     */
    // 自定义退款单号
    private String out_refund_no;
    // 订单总金额
    private int total_fee;
    // 退款金额
    private int refund_fee;

    /**
     * 非必须
     */
    // 货币类型
    private String refund_fee_type;
    // 退款原因
    private String refund_desc;
    // 退款账户 仅针对老资金流商户使用
    // REFUND_SOURCE_UNSETTLED_FUNDS 未结算资金退款
    // REFUND_SOURCE_RECHARGE_FUNDS 可用余额退款
    private String refund_account;
    // 退款结果通知
    private String notify_url;

    public String getTransaction_id() {
        assert transaction_id != null || out_trade_no != null
                : "微信订单号与商户订单号不能同时为空";
        return transaction_id;
    }

    public void setTransaction_id(String transaction_id) {
        this.transaction_id = transaction_id;
    }

    public String getOut_trade_no() {
        assert transaction_id != null || out_trade_no != null
                : "微信订单号与商户订单号不能同时为空";
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    public String getOut_refund_no() {
        return out_refund_no;
    }

    public void setOut_refund_no(String out_refund_no) {
        this.out_refund_no = out_refund_no;
    }

    public int getTotal_fee() {
        assert total_fee > 0: "总金额不能为0";
        return total_fee;
    }

    public void setTotal_fee(int total_fee) {
        this.total_fee = total_fee;
    }

    public int getRefund_fee() {
        assert refund_fee <= total_fee: "退款金额不能高于总金额";
        return refund_fee;
    }

    public void setRefund_fee(int refund_fee) {
        this.refund_fee = refund_fee;
    }

    public String getRefund_fee_type() {
        return refund_fee_type;
    }

    public void setRefund_fee_type(String refund_fee_type) {
        this.refund_fee_type = refund_fee_type;
    }

    public String getRefund_desc() {
        return refund_desc;
    }

    public void setRefund_desc(String refund_desc) {
        this.refund_desc = refund_desc;
    }

    public String getRefund_account() {
        return refund_account;
    }

    public void setRefund_account(String refund_account) {
        this.refund_account = refund_account;
    }

    public String getNotify_url() {
        return notify_url;
    }

    public void setNotify_url(String notify_url) {
        this.notify_url = notify_url;
    }
}
