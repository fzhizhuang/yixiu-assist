package cn.fuzhizhuang.types.base;

import cn.fuzhizhuang.types.exception.BaseResultCode;
import cn.fuzhizhuang.types.exception.ErrorCode;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 通用响应
 *
 * @author Fu.zhizhuang
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonPropertyOrder({"code", "msg", "data"})
@Schema(description = "通用响应")
public class Result<T> {

    @Schema(description = "是否成功")
    private Boolean success;

    @Schema(description = "响应码")
    private String code;

    @Schema(description = "响应消息")
    private String msg;

    @Schema(description = "响应数据")
    private T data;

    /**
     * 构建成功响应
     *
     * @return Result
     */
    public static <T> Result<T> ok() {
        return ok(null);
    }

    /**
     * 构建成功响应
     *
     * @param data 响应数据
     * @return Result
     */
    public static <T> Result<T> ok(T data) {
        return Result.<T>builder()
                .success(Boolean.TRUE)
                .code(BaseResultCode.SUCCESS.code())
                .msg(BaseResultCode.SUCCESS.msg())
                .data(data)
                .build();
    }

    /**
     * 构建失败响应
     *
     * @param errorCode 错误码
     * @return Result
     */
    public static <T> Result<T> fail(ErrorCode errorCode, String msg) {
        return fail(errorCode.code(), msg);
    }

    /**
     * 构建失败响应
     *
     * @param errorCode 错误码
     * @return Result
     */
    public static <T> Result<T> fail(ErrorCode errorCode) {
        return fail(errorCode.code(), errorCode.msg());
    }

    /**
     * 构建失败响应
     *
     * @param code 错误码
     * @param msg  错误消息
     * @return Result
     */
    public static <T> Result<T> fail(String code, String msg) {
        return Result.<T>builder()
                .success(Boolean.FALSE)
                .code(code)
                .msg(msg)
                .build();
    }
}
