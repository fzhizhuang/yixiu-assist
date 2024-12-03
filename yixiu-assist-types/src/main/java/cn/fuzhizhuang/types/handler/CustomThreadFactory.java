package cn.fuzhizhuang.types.handler;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ThreadFactory;

/**
 * 自定义线程工厂,重写线程工厂
 *
 * @author Fu.zhizhuang
 */
@Slf4j
@AllArgsConstructor
public class CustomThreadFactory implements ThreadFactory {

    private final ThreadFactory factory;

    @Override
    public Thread newThread(Runnable runnable) {
        Thread thread = factory.newThread(runnable);
        // 设置未捕获异常
        thread.setUncaughtExceptionHandler((t, e) -> log.error("线程池执行任务异常,thread:{}", t.getName(), e));
        return thread;
    }
}
