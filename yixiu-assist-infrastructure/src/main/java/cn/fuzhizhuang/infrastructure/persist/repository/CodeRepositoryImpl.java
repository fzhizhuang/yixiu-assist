package cn.fuzhizhuang.infrastructure.persist.repository;

import cn.fuzhizhuang.domain.user.adapter.event.SendCaptchaEvent;
import cn.fuzhizhuang.domain.user.adapter.repository.CodeRepository;
import cn.fuzhizhuang.domain.user.model.valobj.EmailCaptchaVO;
import cn.fuzhizhuang.infrastructure.mq.EventPublisher;
import cn.fuzhizhuang.starter.redisson.distribute.DistributeCache;
import cn.fuzhizhuang.types.constant.CacheKey;
import cn.fuzhizhuang.types.utils.AssertUtil;
import cn.hutool.core.util.RandomUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.concurrent.TimeUnit;

/**
 * 验证码仓储实现
 *
 * @author Fu.zhizhuang
 */
@Repository
@RequiredArgsConstructor
public class CodeRepositoryImpl implements CodeRepository {

    private final DistributeCache distributeCache;
    private final EventPublisher eventPublisher;


    @Override
    public String genRandomCaptcha(int length) {
        return RandomUtil.randomNumbers(length);
    }

    @Override
    public void saveCaptcha(String email, String captcha, EmailCaptchaVO emailCaptchaVO, long expire) {
        String key = CacheKey.buildKey(CacheKey.CAPTCHA_PREFIX, email, emailCaptchaVO.getCode());
        distributeCache.setValue(key, captcha, expire, TimeUnit.MINUTES);
    }

    @Override
    public void publishCaptchaEvent(SendCaptchaEvent sendCaptchaEvent) {
        eventPublisher.publish(SendCaptchaEvent.TOPIC, sendCaptchaEvent);
    }

    @Override
    public void verifyCaptcha(String email, String captcha, EmailCaptchaVO emailCaptchaVO) {
        // 构建缓存key
        String key = CacheKey.buildKey(CacheKey.CAPTCHA_PREFIX, email, emailCaptchaVO.getCode());
        String cacheCaptcha = distributeCache.getValue(key);
        AssertUtil.isNotBlank(cacheCaptcha, "验证码不存在或已过期,请重新发送验证码");
        AssertUtil.isTrue(cacheCaptcha.equals(captcha), "验证码错误,请输入正确的验证码");
        // 删除验证码
        distributeCache.removeValue(key);
    }

}
