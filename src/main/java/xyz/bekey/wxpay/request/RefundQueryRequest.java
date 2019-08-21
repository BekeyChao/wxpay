package xyz.bekey.wxpay.request;

import xyz.bekey.wxpay.response.RefundQueryResponse;

/**
 * 退款查询接口
 */
public class RefundQueryRequest extends RequestBase<RefundQueryResponse>{
    @Override
    public boolean needCert() {
        return false;
    }

    @Override
    public Class<RefundQueryResponse> responseType() {
        return RefundQueryResponse.class;
    }

    @Override
    public String apiUrl() {
        return "pay/refundquery";
    }

    /**
     * 四选一
     */
    private String transaction_id;
    private String out_trade_no;
    private String out_refund_no;
    private String refund_id;

    // 偏移量，当部分退款次数超过10次时可使用
    private Integer offset;

    public String getTransaction_id() {
        assert transaction_id != null || out_trade_no != null
                || out_refund_no != null || refund_id != null
                : "退款单号四选一";
        return transaction_id;
    }

    public void setTransaction_id(String transaction_id) {
        this.transaction_id = transaction_id;
    }

    public String getOut_trade_no() {
        assert transaction_id != null || out_trade_no != null
                || out_refund_no != null || refund_id != null
                : "退款单号四选一";
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    public String getOut_refund_no() {
        assert transaction_id != null || out_trade_no != null
                || out_refund_no != null || refund_id != null
                : "退款单号四选一";
        return out_refund_no;
    }

    public void setOut_refund_no(String out_refund_no) {
        this.out_refund_no = out_refund_no;
    }

    public String getRefund_id() {
        assert transaction_id != null || out_trade_no != null
                || out_refund_no != null || refund_id != null
                : "退款单号四选一";
        return refund_id;
    }

    public void setRefund_id(String refund_id) {
        this.refund_id = refund_id;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }
}
