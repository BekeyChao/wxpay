package xyz.bekey.wxpay;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.codec.digest.DigestUtils;
import xyz.bekey.wxpay.request.RequestBase;
import xyz.bekey.wxpay.response.*;

import java.nio.charset.Charset;
import java.util.*;

public class WechatPay {

    /**
     * 支付回调成功响应
     */
    public static final String SUCCESS = "<xml><return_code><![CDATA[SUCCESS]]></return_code>" +
            "<return_msg><![CDATA[OK]]></return_msg></xml>";

    private WxpayConfig wxpayConfig;

    private HttpUtils httpUtils;

    public WechatPay(WxpayConfig wxpayConfig) {
        this.wxpayConfig = wxpayConfig;
        this.httpUtils = new HttpUtils(wxpayConfig);
    }

    public <T extends ResponseBase> T getWechatPayResponse(RequestBase<T> request) {
        String jsonStr = JSON.toJSONString(request);
        JSONObject params = JSON.parseObject(jsonStr);
        params.put("appid", wxpayConfig.getAppid());
        params.put("mch_id", wxpayConfig.getMch_id());
        params.put("nonce_str", genNonceStr());
        if (!SignType.MD5.equals(wxpayConfig.getSignType())) {
            params.put("sign_type", wxpayConfig.getSignType().getType());
        }
        SortedSet<String> keySet = new TreeSet<>(params.keySet());
        String sign = sign(params, keySet);
        params.put("sign", sign);
        keySet.add("sign");

        String xmlBody = XmlUtils.toXmlBody(params, keySet);

        String response = httpUtils.doPost(wxpayConfig.getHost() + request.apiUrl(), xmlBody, request.needCert());

        if (response != null) {
            JSONObject r = XmlUtils.parseXml(response);
            T res = r.toJavaObject(request.responseType());

            /**
             * 针对有优惠券类型的响应特殊处理
             */
            if (res instanceof Coupons) {
                Coupons orderQueryResponse = (Coupons) res;
                packageOrderCoupon(orderQueryResponse, r);
                return (T) orderQueryResponse;
            }

            /**
             * 针对退款查询的特殊处理
             */
            if (res instanceof RefundQueryResponse) {
                RefundQueryResponse refundQueryResponse = (RefundQueryResponse)res;
                packageRefundQueryResponse(refundQueryResponse, r);
                return (T)refundQueryResponse;
            }

            return res;
        }

        return null;
    }

    private void packageRefundQueryResponse(RefundQueryResponse refundQueryResponse, JSONObject r) {
        // 凌乱的特殊处理
        List<RefundQueryResponse.RefundOrder> refundOrders = new ArrayList<>();
        int total = refundQueryResponse.getRefund_count();
        for (int i = 0; i < total; i++) {
            RefundQueryResponse.RefundOrder refundOrder = new RefundQueryResponse.RefundOrder();
            refundOrder.setOut_refund_no(r.getString("out_refund_no_" + i));
            refundOrder.setRefund_id(r.getString("refund_id_" + i));
            refundOrder.setRefund_channel(r.getString("refund_channel_" + i));
            refundOrder.setRefund_fee(r.getInteger("refund_fee_" + i));
            refundOrder.setSettlement_refund_fee(r.getInteger("settlement_refund_fee_" + i));
            refundOrder.setRefund_status(r.getString("refund_status_" + i));
            refundOrder.setRefund_account(r.getString("refund_account_" + i));
            refundOrder.setRefund_recv_accout(r.getString("refund_recv_accout_" + i));
            refundOrder.setRefund_success_time(r.getString("refund_success_time_" + i));
            refundOrder.setCoupon_refund_count(r.getInteger("coupon_refund_count_" + i));
            refundOrder.setCoupon_refund_fee(r.getInteger("coupon_refund_fee_" + i));
            // 优惠券
            List<String> coupon_types = subscriptStringFiled("coupon_type_" + i + "_", r);
            List<Integer> coupon_refund_fees = subscriptIntFiled("coupon_refund_fee_" + i + "_" + i, r);
            List<String> coupon_refund_ids = subscriptStringFiled("coupon_refund_id_" + i + "_" + i, r);
            refundOrder.setCoupon_types(coupon_types);
            refundOrder.setCoupon_refund_fees(coupon_refund_fees);
            refundOrder.setCoupon_refund_ids(coupon_refund_ids);

            refundOrders.add(refundOrder);
        }
        refundQueryResponse.setRefundOrders(refundOrders);
    }

    /**
     * 统一下单接口 回调内容
     *
     * @param callbackXml
     * @return
     * @throws InvalidSignException 签名校验错误，按业务逻辑自行处理
     */
    public UnifiedOrderCallback unifiedorderCallback(String callbackXml) throws InvalidSignException {
        JSONObject result = XmlUtils.parseXml(callbackXml);

        SortedSet<String> keySet = new TreeSet<>(result.keySet());

        if (!result.containsKey("sign")) {
            throw new InvalidSignException("回调Sign不存在");
        }

        if (!valid(result, keySet, result.getString("sign"))) {
            throw new InvalidSignException("Sign校验错误");
        }

        UnifiedOrderCallback unifiedorderCallback = result.toJavaObject(UnifiedOrderCallback.class);
        packageOrderCoupon(unifiedorderCallback, result);

        return unifiedorderCallback;
    }

    public WechatPrePay signAndPrePay(UnifiedOrderResponse response) {
        WechatPrePay prePay = new WechatPrePay();
        if (response.isSuccess()) {
            //
            prePay.setSuccess(true);
            prePay.setTimestamp("" + System.currentTimeMillis() / 1000);
            prePay.setNonceStr(response.getNonce_str());
            prePay.setPackage("prepay_id=" + response.getPrepay_id());
            prePay.setSignType(SignType.MD5.getType());

            Map<String, String> map = new HashMap<>();
            map.put("appId", response.getAppid());
            map.put("timeStamp", prePay.getTimestamp());
            map.put("nonceStr", prePay.getNonceStr());
            map.put("package", prePay.getPackage());
            map.put("signType", prePay.getSignType());

            String sign = WechatSignUtils.sign(map, wxpayConfig.getKey());
            prePay.setPaySign(sign);
            return prePay;
        }

        prePay.setSuccess(false);
        prePay.setError(response.getErr_code_des());
        return prePay;
    }
//    /**
//     * 退款结果回调 因为需要替换jdk jar包等操作，比较麻烦，而且需求不大
    //              可以通过退款查询等方式代替，暂不实现
//     * @param o
//     * @param result
//     */
//    public RefundCallback refundCallback(String str) {
        //解密步骤如下：
        //（1）对加密串A做base64解码，得到加密串B
        //（2）对商户key做md5，得到32位小写key* ( key设置路径：微信商户平台(pay.weixin.qq.com)-->账户设置-->API安全-->密钥设置 )
        //
        //（3）用key*对加密串B做AES-256-ECB解密（PKCS7Padding）
//        Base64 base64 = new Base64();
//        byte[] bBytes = base64.decode(str.getBytes(Charset.forName("UTF-8")));
//        String b = new String(bBytes, Charset.forName("UTF-8"));
//
//        String md5 = DigestUtils.md5Hex(wxpayConfig.getKey()).toLowerCase();
////        new AESCipher.AES256_ECB_NoPadding();
//        try {
//            CipherSpi cipherSpi = Cipherspr
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        } catch (NoSuchPaddingException e) {
//            e.printStackTrace();
//        }


//    }
    private static List<String> subscriptStringFiled(String prix, JSONObject result) {
        List<String> strings = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            if (result.containsKey(prix + i)) {
                strings.add(result.getString(prix + i));
                continue;
            }
            break;
        }
        if (strings.size() > 0)
            return strings;
        return null;
    }

    private static List<Integer> subscriptIntFiled(String prix, JSONObject result) {
        List<Integer> ints = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            if (result.containsKey(prix + i)) {
                ints.add(result.getInteger(prix + i));
                continue;
            }
            break;
        }
        if (ints.size() > 0)
            return ints;
        return null;
    }

    /**
     * 包装优惠券相关字段字段  -- 没有验证过
     * @param o
     * @param result
     */
    private void packageOrderCoupon(Coupons o, JSONObject result) {
        // $n为下标，从0开始编号
        String coupon_type_$ = "coupon_type_";
        List<String> coupon_types = subscriptStringFiled(coupon_type_$, result);
        o.setCoupon_types(coupon_types);

        // 下单时的优惠券字段
        String coupon_id_$ = "coupon_id_";
        List<String> coupon_ids = subscriptStringFiled(coupon_id_$, result);
        o.setCoupon_ids(coupon_ids);

        String coupon_fee_$ = "coupon_fee_";
        List<Integer> coupon_fees = subscriptIntFiled(coupon_fee_$, result);
        o.setCoupon_fees(coupon_fees);

        // 退款时的优惠券字段
        String coupon_refund_fee_$ = "coupon_refund_fee_";
        List<Integer> coupon_refund_fees = subscriptIntFiled(coupon_refund_fee_$, result);
        o.setCoupon_refund_fees(coupon_refund_fees);

        String coupon_refund_id_$ = "coupon_refund_id_";
        List<String> coupon_refund_ids = subscriptStringFiled(coupon_refund_id_$, result);
        o.setCoupon_refund_ids(coupon_refund_ids);
    }

    /**
     * a-z：97-122
     * A-Z：65-90
     * 0-9：48-57
     * 简易随机字符串生成法，由32位小写字母组成
     * @return
     */
    private String genNonceStr() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder("");
        for (int i = 0; i < 32; i++) {
            int r = 97 + random.nextInt(26);
            sb.append((char) r);
        }
        return sb.toString();
    }

    private String sign(JSONObject params, SortedSet<String> keySet) {
        StringBuilder sb = new StringBuilder("");
        for (String key : keySet) {
            String value = params.getString(key);
            if (value != null && value.length() > 0) {
                sb.append(key).append("=")
                        .append(value).append("&");
            }
        }
        sb.append("key=").append(wxpayConfig.getKey());
        if (params.containsKey("sign_type")) {
            return DigestUtils.sha256Hex(sb.toString().getBytes(Charset.forName("UTF-8")))
                    .toUpperCase();
        } else {
            return DigestUtils.md5Hex(sb.toString()).toUpperCase();
        }
    }

    private boolean valid(JSONObject params, SortedSet<String> keySet, String sign) {
        keySet.remove("sign");
        String mySign = sign(params, keySet);
        return sign.equals(mySign);
    }
}
