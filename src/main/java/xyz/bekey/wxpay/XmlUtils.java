package xyz.bekey.wxpay;

import com.alibaba.fastjson.JSONObject;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.util.Iterator;
import java.util.SortedSet;

class XmlUtils {

    /**
     * 微信支付xml响应
     * @param strXML XML字符串
     * @return XML数据转换后的json数据
     * @throws FailToPayException parse异常
     */
    static JSONObject parseXml(String strXML) {
        JSONObject data = new JSONObject();
        Document document;
        try {
            document = DocumentHelper.parseText(strXML);
        } catch (DocumentException e ) {
            throw new FailToPayException("响应xml解析异常 -> " + strXML, e );
        }

        Element root = document.getRootElement();
        Iterator<Element> children = root.elementIterator();
        while (children.hasNext()) {
            Element element = children.next();
            data.put(element.getName(), element.getText());
        }
        return data;
    }

    /**
     * 将
     * @param params
     * @param keySet
     * @return
     */
    static String toXmlBody(JSONObject params, SortedSet<String> keySet) {
        Document document = DocumentHelper.createDocument();
        Element root = document.addElement("xml");
        for (String key: keySet) {
            String value = params.getString(key);
            if (value != null && value.length() > 0) {
                root.addElement(key).addText(value);
            }
        }
        return document.asXML();
    }


}
