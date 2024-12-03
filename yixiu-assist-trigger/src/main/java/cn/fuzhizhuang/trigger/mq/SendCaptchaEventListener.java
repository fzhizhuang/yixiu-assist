package cn.fuzhizhuang.trigger.mq;

import cn.fuzhizhuang.domain.user.adapter.event.SendCaptchaEvent;
import cn.fuzhizhuang.domain.user.model.entity.EmailEntity;
import cn.fuzhizhuang.types.utils.IdempotentUtil;
import cn.fuzhizhuang.types.utils.MailUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.TypeReference;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * 发送验证码事件监听
 *
 * @author Fu.zhizhuang
 */
@Slf4j
@Component
@RequiredArgsConstructor
@RocketMQMessageListener(topic = SendCaptchaEvent.TOPIC, consumerGroup = SendCaptchaEvent.GROUP)
public class SendCaptchaEventListener implements RocketMQListener<String> {

    private final IdempotentUtil idempotentUtil;

    @Override
    public void onMessage(String s) {
        log.info("接收到发送验证码事件:{}", s);
        SendCaptchaEvent event = JSON.parseObject(s, new TypeReference<SendCaptchaEvent>() {
        }.getType());
        // 执行幂等判断
        idempotentUtil.execute(event.getBiz(), () -> {
            // 执行任务
            EmailEntity emailEntity = event.getData();
            String email = emailEntity.getEmail();
            String subject = emailEntity.getSubject();
            String content = emailEntity.getContent();
            MailUtil.sendEmail(email, subject, content);
        });
    }

}
