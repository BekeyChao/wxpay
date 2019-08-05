package xyz.bekey.wxpay;

/**
 * 签名校验错误异常
 */
public class InvalidSignException extends Exception{
    public InvalidSignException(String message) {
        super(message);
    }

    public InvalidSignException(Throwable cause) {
        super(cause);
    }
}
