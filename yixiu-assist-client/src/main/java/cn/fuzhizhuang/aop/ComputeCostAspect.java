package cn.fuzhizhuang.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * 计算耗时切面
 *
 * @author Fu.zhizhuang
 */
@Slf4j
@Aspect
@Component
public class ComputeCostAspect {

    @Around("@annotation(cn.fuzhizhuang.types.annotation.ComputeCost)")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        // 执行耗时
        log.info("方法: {}.{} 耗时:{} ms", joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName(), System.currentTimeMillis() - startTime);
        return result;
    }
}
