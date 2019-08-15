package xyz.bekey.wxpay;

import org.apache.commons.codec.digest.DigestUtils;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class WechatSignUtils {
    public static String sign(Map<String, String> param, String key) {
        if (param == null || key == null) {
            throw new IllegalArgumentException("must not be null");
        }
        TreeMap<String, String> treeMap = new TreeMap<>(param);
        StringBuilder sb = new StringBuilder("");
        Set<Map.Entry<String, String>> entries = treeMap.entrySet();
        for (Map.Entry<String, String> entry: entries) {
            if (entry.getValue() == null || "".equals(entry.getValue())) {
                continue;
            }
            if ("sign".equals(entry.getKey())) {
                continue;
            }
            sb.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
        }
        sb.append("key=").append(key);
        String str = sb.toString();
        return DigestUtils.md5Hex(str).toUpperCase();
    }

    public static boolean valid(String sign, Map<String, String> param, String key) {
        String signValid = sign(param, key);
        return signValid.equals(sign);
    }
}
