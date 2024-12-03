package cn.fuzhizhuang.types.exception;

/**
 * 异常枚举接口
 *
 * @author Fu.zhizhuang
 */
public interface ErrorCode {

    /**
     * 错误码
     *
     * @return code
     */
    String code();

    /**
     * 错误信息
     *
     * @return msg
     */
    String msg();
}
