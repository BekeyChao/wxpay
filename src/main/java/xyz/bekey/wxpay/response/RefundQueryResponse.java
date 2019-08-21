package xyz.bekey.wxpay.response;

import java.util.List;

public class RefundQueryResponse extends ResponseBase{
    // 退款总次数
    private Integer total_refund_count;

    // 单号
    private String transaction_id;
    private String out_trade_no;

    private Integer total_fee;
    private Integer settlement_total_fee;
    private String fee_type;
    private Integer cash_fee;
    // 当前返回退款笔数
    private Integer refund_count;

    private List<RefundOrder> refundOrders;

    public static class RefundOrder {
        // 退款单号
        private String out_refund_no;
        private String refund_id;
        // 渠道
        private String refund_channel;
        // 退款金额
        private Integer refund_fee;
        // 应结退款金额
        private Integer settlement_refund_fee;
        // 退款状态
        private String refund_status;
        // 退款账户
        private String refund_account;
        // 退款到账账户
        private String refund_recv_accout;
        // 退款完成实际时间
        private String refund_success_time;
        // 退款代金券使用数量
        private Integer coupon_refund_count;
        // 总代金券退款金额
        private Integer coupon_refund_fee;

        /**
         * 代金券字段
         * @return
         */
        // 代金券类型 有序
        private List<String> coupon_types;
        // 代金券退款金额
        private List<Integer> coupon_refund_fees;
        // 代金券退款ID
        private List<String> coupon_refund_ids;

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

        public String getRefund_channel() {
            return refund_channel;
        }

        public void setRefund_channel(String refund_channel) {
            this.refund_channel = refund_channel;
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

        public String getRefund_status() {
            return refund_status;
        }

        public void setRefund_status(String refund_status) {
            this.refund_status = refund_status;
        }

        public String getRefund_account() {
            return refund_account;
        }

        public void setRefund_account(String refund_account) {
            this.refund_account = refund_account;
        }

        public String getRefund_recv_accout() {
            return refund_recv_accout;
        }

        public void setRefund_recv_accout(String refund_recv_accout) {
            this.refund_recv_accout = refund_recv_accout;
        }

        public String getRefund_success_time() {
            return refund_success_time;
        }

        public void setRefund_success_time(String refund_success_time) {
            this.refund_success_time = refund_success_time;
        }

        public Integer getCoupon_refund_count() {
            return coupon_refund_count;
        }

        public void setCoupon_refund_count(Integer coupon_refund_count) {
            this.coupon_refund_count = coupon_refund_count;
        }

        public Integer getCoupon_refund_fee() {
            return coupon_refund_fee;
        }

        public void setCoupon_refund_fee(Integer coupon_refund_fee) {
            this.coupon_refund_fee = coupon_refund_fee;
        }

        public List<String> getCoupon_types() {
            return coupon_types;
        }

        public void setCoupon_types(List<String> coupon_types) {
            this.coupon_types = coupon_types;
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

    public Integer getTotal_refund_count() {
        return total_refund_count;
    }

    public void setTotal_refund_count(Integer total_refund_count) {
        this.total_refund_count = total_refund_count;
    }

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

    public Integer getTotal_fee() {
        return total_fee;
    }

    public void setTotal_fee(Integer total_fee) {
        this.total_fee = total_fee;
    }

    public Integer getSettlement_total_fee() {
        return settlement_total_fee;
    }

    public void setSettlement_total_fee(Integer settlement_total_fee) {
        this.settlement_total_fee = settlement_total_fee;
    }

    public String getFee_type() {
        return fee_type;
    }

    public void setFee_type(String fee_type) {
        this.fee_type = fee_type;
    }

    public Integer getCash_fee() {
        return cash_fee;
    }

    public void setCash_fee(Integer cash_fee) {
        this.cash_fee = cash_fee;
    }

    public Integer getRefund_count() {
        return refund_count;
    }

    public void setRefund_count(Integer refund_count) {
        this.refund_count = refund_count;
    }

    public List<RefundOrder> getRefundOrders() {
        return refundOrders;
    }

    public void setRefundOrders(List<RefundOrder> refundOrders) {
        this.refundOrders = refundOrders;
    }
}
