package cn.fuzhizhuang.types.utils;

import cn.fuzhizhuang.starter.redisson.distribute.DistributeCache;
import cn.fuzhizhuang.types.constant.CacheKey;
import cn.hutool.core.util.StrUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * 幂等工具
 *
 * @author Fu.zhizhuang
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class IdempotentUtil {

    private final DistributeCache distributeCache;

    public void execute(String biz, Runnable runnable) {
        // 幂等判断消息事件是否已消费
        String key = CacheKey.buildKey(CacheKey.IDEMPOTENT, biz);
        String idempotentBiz = distributeCache.getValue(key);
        log.info("执行幂等判断,key:{}, idempotentBiz:{}", key, idempotentBiz);
        if (StrUtil.isNotBlank(idempotentBiz)) {
            log.info("幂等判断,消息已消费,跳过处理.");
            return;
        }
        log.info("消息未消费,执行后续操作.");
        runnable.run();
        // 执行完成，缓存幂等消息，解决消息重复消费
        distributeCache.setValue(key, biz, 2, TimeUnit.HOURS);
    }
}
