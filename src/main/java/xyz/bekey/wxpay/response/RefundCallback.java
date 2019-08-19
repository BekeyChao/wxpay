package xyz.bekey.wxpay.response;

public class RefundCallback extends Coupons {
    /**
     * 以下一定有
     */
    // 微信单号
    private String transaction_id;
    // 商户单号
    private String out_trade_no;
    // 商户退款单号
    private String out_refund_no;
    // 微信退款单号
    private String refund_id;
    // 退款金额
    private Integer refund_fee;
    // 应结退款金额
    private Integer settlement_refund_fee;
    // 总金额
    private Integer total_fee;

    // SUCCESS-退款成功
    //CHANGE-退款异常
    //REFUNDCLOSE—退款关闭
    private String refund_status;

    // 取当前退款单的退款入账方
    private String refund_recv_accout;

    // 退款资金来源
    // REFUND_SOURCE_RECHARGE_FUNDS 可用余额退款/基本账户
    // REFUND_SOURCE_UNSETTLED_FUNDS 未结算资金退款
    private String refund_account;

    // 退款请求来源
    // API接口
    // VENDOR_PLATFORM商户平台
    private String refund_request_source;

    /**
     * 以下也许有
     */
    // 应结订单金额
    private Integer settlement_total_fee;

    // 退款成功时间
    private String success_time;

    public String getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(String transaction_id) {
        this.transaction_id = transaction_id;
    }

    public String getOut_trade_no() {
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

    public String getRefund_id() {
        return refund_id;
    }

    public void setRefund_id(String refund_id) {
        this.refund_id = refund_id;
    }

    public Integer getRefund_fee() {
        return refund_fee;
    }

    public void setRefund_fee(Integer refund_fee) {
        this.refund_fee = refund_fee;
    }

    public Integer getSettlement_refund_fee() {
        return settlement_refund_fee;
    }

    public void setSettlement_refund_fee(Integer settlement_refund_fee) {
        this.settlement_refund_fee = settlement_refund_fee;
    }

    public Integer getTotal_fee() {
        return total_fee;
    }

    public void setTotal_fee(Integer total_fee) {
        this.total_fee = total_fee;
    }

    public String getRefund_status() {
        return refund_status;
    }

    public void setRefund_status(String refund_status) {
        this.refund_status = refund_status;
    }

    public String getRefund_recv_accout() {
        return refund_recv_accout;
    }

    public void setRefund_recv_accout(String refund_recv_accout) {
        this.refund_recv_accout = refund_recv_accout;
    }

    public String getRefund_account() {
        return refund_account;
    }

    public void setRefund_account(String refund_account) {
        this.refund_account = refund_account;
    }

    public String getRefund_request_source() {
        return refund_request_source;
    }

    public void setRefund_request_source(String refund_request_source) {
        this.refund_request_source = refund_request_source;
    }

    public Integer getSettlement_total_fee() {
        return settlement_total_fee;
    }

    public void setSettlement_total_fee(Integer settlement_total_fee) {
        this.settlement_total_fee = settlement_total_fee;
    }

    public String getSuccess_time() {
        return success_time;
    }

    public void setSuccess_time(String success_time) {
        this.success_time = success_time;
    }
}
