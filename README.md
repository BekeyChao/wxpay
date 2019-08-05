## 微信支付集成工具
### 快速开始

```bash
git clone ..

cd yourpath

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
目前最新版本是0.1.0

### 在Spring中使用的例子
微信支付工具都通过实例化WechatPay类来使用，在Spring中建议注册为单例方便使用。
```java

@Configuration
public class WechatPayConfig {

    @Value("${mch_id}")
    private String mch_id; 
    @Value("${key}")
    private String key; 
    @Value("${cert_path}")
    private String cert_path;   // 微信证书路径

    @Bean
    public WechatPay wechatPay() {
        // wxConfig提供了两种构造方法， 默认签名类型为MD5， 默认微信支付服务地址取自官方文档
        WxpayConfig wxConfig = new WxpayConfig(WechatApplication.Yepaofu_Subscribe.getAppid()
                , mch_id, key, cert_path);
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
    
    public void toPay() {
        UnifiedorderRequest request = new UnifiedorderRequest();
        request.setTotal_fee(100);
        request.setSpbill_create_ip("10.10.10.10");
        request.setOut_trade_no("test123");
        request.setBody("测试订单");
        request.setTrade_type(TradeType.MWEB);
        request.setOpenid("openid");
        request.setNotify_url("http://mycallbak.com");
        // some else...
                
        UnifiedorderResponse response = wechatPay.getWechatPayResponse(request);
        boolean success = response.isSuccess();
        if (success) {
            System.out.println(JSON.toJSONString(response)); 
        } else {
            System.out.println(response.getErr_code_des()); 
        }
    }
    
    public void handleCallback(String xml) { 
        try {
            UnifiedorderCallback callback = wechatPay.unifiedorderCallback(xml);
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

### 代码风格
微信接口中会有大量的 javabean  xml  map 之间的转换，我处理的还是比较随意的，在xml -- javabean 之间主要通过fastjson进行转换，因为用起来比较顺手，所以也没在意效率的问题。
整体api风格设计借鉴 阿里云SDK 风格，xxxRequest 对应 xxxResponse，这种api设计很直观，而且通过一个api接口就可以实现了。
在参数校验层面，为了偷懒大量的校验与数据结构转换在Request中进行，所以Request类并不是简单类，在调试中可能会遇到点麻烦，见谅

### 官方文档版本
在写文档时参照最新微信支付官方文档（20190805），接口如有变动请以官方文档为准，相关字段含义也请参照官方文档


