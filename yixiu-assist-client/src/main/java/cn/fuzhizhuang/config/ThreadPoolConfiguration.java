package cn.fuzhizhuang.config;

import cn.fuzhizhuang.types.handler.CustomThreadFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * 线程池配置
 *
 * @author Fu.zhizhuang
 */
@EnableAsync
@Configuration
public class ThreadPoolConfiguration implements AsyncConfigurer {

    // 项目公用线程池
    public static final String COMMON_EXECUTOR = "commonExecutor";

    // 配置公用线程池
    @Primary
    @Bean(COMMON_EXECUTOR)
    public ThreadPoolTaskExecutor commonExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10);
        executor.setMaxPoolSize(10);
        executor.setQueueCapacity(200);
        executor.setThreadNamePrefix("common-executor-");
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy()); // 满了调用线程执行，不抛弃也不抛出异常
        executor.setThreadFactory(new CustomThreadFactory(executor)); // 设置线程工厂
        executor.initialize();
        return executor;
    }
}
