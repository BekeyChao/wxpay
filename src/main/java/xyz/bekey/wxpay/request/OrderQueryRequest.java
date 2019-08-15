package xyz.bekey.wxpay.request;

import xyz.bekey.wxpay.response.OrderQueryResponse;

public class OrderQueryRequest extends RequestBase<OrderQueryResponse>{
    @Override
    public boolean needCert() {
        return false;
    }

    @Override
    public Class<OrderQueryResponse> responseType() {
        return OrderQueryResponse.class;
    }

    @Override
    public String apiUrl() {
        return "pay/orderquery";
    }

    // 微信订单号
    private String transaction_id;

    // 商户订单号
    private String out_trade_no;

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
}
