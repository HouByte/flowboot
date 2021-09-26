package cn.flowboot.common.exception;

/**
 * <h1></h1>
 *
 * @version 1.0
 * @author: Vincent Vic
 * @since: 2021/09/26
 */
public class ParamsException extends RuntimeException {

    public ParamsException(String message) {
        super(message);
    }

    public ParamsException(String message, Throwable cause) {
        super(message, cause);
    }

    public ParamsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
