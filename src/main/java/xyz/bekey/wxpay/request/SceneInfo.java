package xyz.bekey.wxpay.request;

abstract class SceneInfo {
    /**
     * id : SZTX001
     * name : 腾大餐厅
     * area_code : 440305
     * address : 科技园中一路腾讯大厦
     */
    // 商户自定义
    private String id;
    // 商户自定义
    private String name;
    // 行政区域代码
    private String area_code;
    // 地址
    private String address;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArea_code() {
        return area_code;
    }

    public void setArea_code(String area_code) {
        this.area_code = area_code;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
