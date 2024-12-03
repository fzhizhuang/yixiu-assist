package cn.fuzhizhuang.types.exception;

import lombok.AllArgsConstructor;

/**
 * 基础响应枚举
 *
 * @author Fu.zhizhuang
 */
@AllArgsConstructor
public enum BaseResultCode implements ErrorCode {

    SUCCESS("0000", "成功"),
    // 参数校验
    PARAM_ERROR("1000", "参数错误"),
    VALIDATE_ERROR("1001", "参数校验错误"),
    // 业务异常
    BUSINESS_ERROR("2000", "业务异常"),
    // 系统异常
    SYSTEM_ERROR("9999", "系统繁忙,请稍后再试~"),
    ;
    private final String code;
    private final String msg;

    @Override
    public String code() {
        return code;
    }

    @Override
    public String msg() {
        return msg;
    }
}
