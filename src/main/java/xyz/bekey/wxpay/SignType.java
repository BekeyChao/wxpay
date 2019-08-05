package xyz.bekey.wxpay;

public enum SignType {
    MD5("MD5"), SHA256("HMAC-SHA256");

    private String type;

    SignType(String type ) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
