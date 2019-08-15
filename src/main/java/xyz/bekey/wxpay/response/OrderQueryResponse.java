package xyz.bekey.wxpay.response;

public class OrderQueryResponse extends OrderDetail{
    // 交易状态描述
    private String trade_state_desc;

    public String getTrade_state_desc() {
        return trade_state_desc;
    }

    public void setTrade_state_desc(String trade_state_desc) {
        this.trade_state_desc = trade_state_desc;
    }
}
