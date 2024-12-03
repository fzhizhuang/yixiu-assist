package cn.fuzhizhuang.infrastructure.mq.Impl;

import cn.fuzhizhuang.infrastructure.mq.EventPublisher;
import cn.fuzhizhuang.types.base.BaseEvent;
import com.alibaba.fastjson2.JSON;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.TransactionSendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import static org.apache.rocketmq.client.producer.SendStatus.SEND_OK;

/**
 * 事件发布实现
 *
 * @author Fu.zhizhuang
 */
@Slf4j
@Component
public class EventPublisherImpl implements EventPublisher {

    @Resource
    private RocketMQTemplate rocketMQTemplate;

    @Override
    public <T extends BaseEvent<?>> void publish(String topic, T event) {
        String jsonString = JSON.toJSONString(event);
        publish(topic, jsonString);
    }

    @Override
    public void publish(String topic, String eventJson) {
        try {
            log.info("发送消息 topic:{} message:{}", topic, eventJson);
            rocketMQTemplate.convertAndSend(topic, eventJson);
        } catch (MessagingException e) {
            log.error("发送消息失败,topic:{} message:{}", topic, eventJson, e);
        }
    }


    @Override
    public <T extends BaseEvent<?>> void asyncPublish(String topic, T event) {
        String jsonString = JSON.toJSONString(event);
        asyncPublish(topic, jsonString);
    }


    @Override
    public void asyncPublish(String topic, String eventJson) {
        rocketMQTemplate.asyncSend(topic, eventJson, new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                log.info("异步消息发送成功,topic:{} message:{}", topic, eventJson);
            }

            @Override
            public void onException(Throwable throwable) {
                log.info("异步消息发送失败,topic:{} message:{} error:{}", topic, eventJson, throwable.getMessage());
            }
        });
    }


    @Override
    public <T extends BaseEvent<?>> void delayPublish(String topic, T event, int delayLevel) {
        String jsonString = JSON.toJSONString(event);
        delayPublish(topic, jsonString, delayLevel);
    }

    @Override
    public void delayPublish(String topic, String eventJson, int delayLevel) {
        SendResult sendResult = rocketMQTemplate.syncSend(topic, MessageBuilder.withPayload(eventJson).build(), 3000, delayLevel);
        if (SEND_OK.equals(sendResult.getSendStatus())) {
            log.info("延迟消息发送成功,topic:{} message:{}", topic, eventJson);
        } else {
            log.info("延迟消息发送失败,topic:{} message:{}", topic, eventJson);
        }
    }

    @Override
    public <T extends BaseEvent<?>> void scheduledPublish(String topic, T event, int time) {
        String jsonString = JSON.toJSONString(event);
        scheduledPublish(topic, jsonString, time);
    }


    @Override
    public void scheduledPublish(String topic, String eventJson, int time) {
        // 生成投递时间
        long pushTime = System.currentTimeMillis() + ((long) time * 60 * 1000);
        rocketMQTemplate.syncSendDeliverTimeMills(topic, MessageBuilder.withPayload(eventJson).build(), pushTime);
    }

    @Override
    public <T extends BaseEvent<?>> TransactionSendResult transactionSend(String topic, T event, Object arg) {
        String jsonString = JSON.toJSONString(event);
        return transactionSend(topic, jsonString, arg);
    }


    @Override
    public TransactionSendResult transactionSend(String topic, String eventJson, Object arg) {
        return rocketMQTemplate.sendMessageInTransaction(topic, MessageBuilder.withPayload(eventJson).build(), arg);
    }

    @Override
    public <T extends BaseEvent<?>> void orderSend(String topic, T event, String hashKey) {
        String jsonString = JSON.toJSONString(event);
        orderSend(topic, jsonString, hashKey);
    }


    @Override
    public void orderSend(String topic, String eventJson, String hashKey) {
        Message<String> message = MessageBuilder.withPayload(eventJson).setHeader(RocketMQHeaders.KEYS, hashKey).build();
        SendResult sendResult = rocketMQTemplate.syncSendOrderly(topic, message, hashKey);
        if (SEND_OK.equals(sendResult.getSendStatus())) {
            log.info("顺序消息发送成功,topic:{} message:{}", topic, eventJson);
        } else {
            log.info("顺序消息发送失败,topic:{} message:{}", topic, eventJson);
        }
    }
}

