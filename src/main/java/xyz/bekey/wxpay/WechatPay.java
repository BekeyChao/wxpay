package xyz.bekey.wxpay;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.codec.digest.DigestUtils;
import xyz.bekey.wxpay.request.RequestBase;
import xyz.bekey.wxpay.response.Coupons;
import xyz.bekey.wxpay.response.OrderQueryResponse;
import xyz.bekey.wxpay.response.ResponseBase;
import xyz.bekey.wxpay.response.UnifiedOrderCallback;

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

            if (res instanceof Coupons) {
                Coupons orderQueryResponse = (Coupons) res;
                packageOrderCoupon(orderQueryResponse, r);
                return (T) orderQueryResponse;
            }

            return res;
        }

        return null;
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

    private void packageOrderCoupon(Coupons o, JSONObject result) {
        // $n为下标，从0开始编号
        String coupon_type_$ = "coupon_type_$";
        List<String> coupon_types = new ArrayList<>();
        // 下单时的优惠券字段
        String coupon_id_$ = "coupon_id_$";
        List<String> coupon_ids = new ArrayList<>();
        String coupon_fee_$ = "coupon_fee_$";
        List<Integer> coupon_fees = new ArrayList<>();

        // 退款时的优惠券字段
        String coupon_refund_fee_$ = "coupon_refund_fee_$";
        List<Integer> coupon_refund_fees = new ArrayList<>();
        String coupon_refund_id_$ = "coupon_refund_id_$";
        List<String> coupon_refund_ids = new ArrayList<>();

        // 封装coupon数据
        for (int i = 0; i < 100; i++) {
            if (result.containsKey(coupon_type_$ + i)) {
                coupon_types.add(result.getString(coupon_type_$ + i));
            }
            // 下单时的优惠券字段
            if (result.containsKey(coupon_id_$ + i)) {
                coupon_ids.add(result.getString(coupon_id_$ + i));
                coupon_fees.add(result.getInteger(coupon_fee_$ + i));
                continue;
            }
            // 退款时的优惠券字段
            if (result.containsKey(coupon_refund_id_$ + i)) {
                coupon_refund_fees.add(result.getInteger(coupon_refund_fee_$ + i));
                coupon_refund_ids.add(result.getString(coupon_refund_id_$ + i));
                continue;
            }

            break;
        }

        o.setCoupon_types(coupon_types);
        o.setCoupon_ids(coupon_ids);
        o.setCoupon_fees(coupon_fees);
        o.setCoupon_refund_fees(coupon_refund_fees);
        o.setCoupon_refund_ids(coupon_refund_ids);

    }

    /**
     * a-z：97-122
     * A-Z：65-90
     * 0-9：48-57
     *
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
