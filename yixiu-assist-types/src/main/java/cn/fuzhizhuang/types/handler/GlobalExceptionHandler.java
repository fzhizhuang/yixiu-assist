package cn.fuzhizhuang.types.handler;

import cn.fuzhizhuang.types.base.Result;
import cn.fuzhizhuang.types.exception.BaseResultCode;
import cn.fuzhizhuang.types.exception.BizException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

/**
 * 全局业务异常
 *
 * @author Fu.zhizhuang
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理业务异常
     *
     * @param e 异常
     * @return Result
     */
    @ExceptionHandler(BizException.class)
    public Result<Void> handleBizException(BizException e) {
        log.error("业务异常:{}", e.getMessage(), e);
        return Result.fail(e.getErrorCode(), e.getMessage());
    }

    /**
     * 处理系统异常,兜底处理
     *
     * @param e 异常
     * @return Result
     */
    @ExceptionHandler(Exception.class)
    public Result<Void> handleException(Exception e) {
        log.error("系统异常:{}", e.getMessage(), e);
        return Result.fail(BaseResultCode.SYSTEM_ERROR, e.getMessage());
    }

    /**
     * 处理参数异常
     *
     * @param e 异常
     * @return Result
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public Result<Void> handleIllegalArgumentException(IllegalArgumentException e) {
        log.error("参数异常:{}", e.getMessage(), e);
        return Result.fail(BaseResultCode.PARAM_ERROR, e.getMessage());
    }

    /**
     * 处理参数校验异常
     *
     * @param e 异常
     * @return Result
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<Void> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        List<String> msgList = bindingResult.getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toList();
        // 组装消息
        String msg = String.join(",", msgList);
        log.error("参数校验异常:{}", msg);
        return Result.fail(BaseResultCode.VALIDATE_ERROR, msg);
    }
}
