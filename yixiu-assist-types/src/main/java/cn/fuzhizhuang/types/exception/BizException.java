package cn.fuzhizhuang.types.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 业务异常
 *
 * @author Fu.zhizhuang
 */
@Getter
@AllArgsConstructor
public class BizException extends RuntimeException {

    private final ErrorCode errorCode;
    private final String msg;

    public BizException(ErrorCode errorCode) {
        this(errorCode, errorCode.msg());
    }
}
