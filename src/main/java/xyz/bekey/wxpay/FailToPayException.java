package xyz.bekey.wxpay;

/**
 * 支付失败异常
 */
public class FailToPayException extends RuntimeException {
    public FailToPayException(String message, Throwable cause) {
        super(message, cause);
    }

    public FailToPayException(String message) {
        super(message);
    }
}
