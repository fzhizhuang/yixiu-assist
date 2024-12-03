package cn.fuzhizhuang.types.exception;

import lombok.Getter;

/**
 * 业务异常
 *
 * @author Fu.zhizhuang
 */
@Getter
public class BizException extends RuntimeException {

    private final ErrorCode errorCode;
    private final String msg;

    public BizException(ErrorCode errorCode) {
        this(errorCode, errorCode.msg());
    }

    public BizException(ErrorCode errorCode, String msg) {
        super(msg);
        this.errorCode = errorCode;
        this.msg = msg;
    }
}
