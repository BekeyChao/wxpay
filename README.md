## 微信支付集成工具
### 快速开始

```bash

cd yourpath

git clone gitAddress

cd wxpay

mvn install

```

克隆源码到本地，通过mvn安装到本地，在pom文件中引入
```xml
<dependency>
    <groupId>xyz.bekey</groupId>
    <artifactId>wxpay</artifactId>
    <version>version</version>
</dependency>
```
目前最新版本是0.0.5， jdk最低要求1.8，代码中并没有大量应用java8新内容，如果希望兼容，只需修改统一下单接口中的LocalDateTime类型

### 在Spring中使用的例子
微信支付工具都通过实例化WechatPay类来使用，在Spring中建议注册为单例方便使用。
```java

@Configuration
public class WechatPayConfig {

    @Value("${appid}")
    private String appid;
    @Value("${mch_id}")
    private String mch_id; 
    @Value("${key}")
    private String key; 
    @Value("${cert_path}")
    private String cert_path;   // 微信证书路径

    @Bean
    public WechatPay wechatPay() {
        // wxConfig提供了两种构造方法， 默认签名类型为MD5， 默认微信支付服务地址取自官方文档
        WxpayConfig wxConfig = new WxpayConfig(appid,
                mch_id, key, cert_path);
        // 自定义 可以忽略不设
        wxConfig.setConnectTimeou(10000);
        wxConfig.setSocketTimeout(10000);
        return new WechatPay(wxConfig);
    }
}
```
以统一下单接口为例，调用方法
```java
@Service
public class PayService {
    @Autowired
    private WechatPay wechatPay;
    // 统一下单接口
    public void toPay() {
        UnifiedOrderRequest request = new UnifiedOrderRequest();
        request.setTotal_fee(100);
        request.setSpbill_create_ip("10.10.10.10");
        request.setOut_trade_no("test123");
        request.setBody("测试订单");
        request.setTrade_type(TradeType.MWEB);
        request.setOpenid("openid");
        request.setNotify_url("http://mycallbak.com");
        // some else...
                
        UnifiedOrderResponse response = wechatPay.getWechatPayResponse(request);
        boolean success = response.isSuccess();
        if (success) {
            System.out.println(JSON.toJSONString(response)); 
        } else {
            System.out.println(response.getErr_code_des()); 
        }
    }
    // 支付结果回调，请从response中获取xml字符串
    public void handleCallback(String xml) { 
        try {
            UnifiedOrderCallback callback = wechatPay.unifiedorderCallback(xml);
            if (callback.isSuccess()) {
                System.out.println(JSON.toJSONString(callback));
            } else {
                System.out.println(callback.getErr_code_des());
            }
        } catch (InvalidSignException e) {
            // 处理签名异常
            e.printStackTrace();
        }        
    }
    
}
```
### 接口完成列表
已完成接口
* 统一下单接口 UnifiedOrderRequest
* 查询订单接口 OrderQueryRequest
* 申请退款接口（需要证书） RefundRequest
* 退款查询接口 RefundQueryRequest
* 支付结果通知 UnifiedOrderCallback
  

未完成接口
* 关闭订单
* 海关申报
* 下载对账单
* 下载资金账单
* 交易保障
* 拉取订单评价数据
* 退款结果通知（因为需要AES-256-ECB解密，jdk默认不支持，暂时不实现了）

### 代码风格
微信接口中会有大量的 javabean  xml  map 之间的转换，我处理的还是比较随意的，在xml -- javabean 之间主要通过fastjson进行转换，因为用起来比较顺手，所以也没在意效率的问题。
整体api风格设计借鉴 阿里云SDK 风格，xxxRequest 对应 xxxResponse，这种api设计很直观，而且通过一个api接口就可以实现了。
在参数校验层面，为了偷懒大量的校验与数据结构转换在Request中进行，所以Request类并不是简单类，在调试中可能会遇到点麻烦，见谅

### 官方文档版本
在写文档时参照最新微信支付官方文档（20190805），接口如有变动请以官方文档为准，相关字段含义也请参照官方文档


