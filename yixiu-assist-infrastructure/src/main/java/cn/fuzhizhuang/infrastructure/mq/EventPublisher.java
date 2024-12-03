package cn.fuzhizhuang.infrastructure.mq;

import cn.fuzhizhuang.types.base.BaseEvent;
import org.apache.rocketmq.client.producer.TransactionSendResult;

/**
 * 事件发布接口
 *
 * @author Fu.zhizhuang
 */
public interface EventPublisher {

    /**
     * 发送消息
     *
     * @param topic 主题
     * @param event 事件
     * @param <T>   泛型
     */
    <T extends BaseEvent<?>> void publish(String topic, T event);

    /**
     * 发送消息
     *
     * @param topic     主题
     * @param eventJson 事件json
     */
    void publish(String topic, String eventJson);


    /**
     * 发送异步消息
     *
     * @param topic 主题
     * @param event 事件
     * @param <T>   泛型
     */
    <T extends BaseEvent<?>> void asyncPublish(String topic, T event);


    /**
     * 发送异步消息
     *
     * @param topic     主题
     * @param eventJson 事件json
     */
    void asyncPublish(String topic, String eventJson);

    /**
     * 发送延迟消息
     *
     * @param topic      主题
     * @param event      事件
     * @param delayLevel 延迟等级
     * @param <T>        泛型
     */
    <T extends BaseEvent<?>> void delayPublish(String topic, T event, int delayLevel);

    /**
     * 发送延迟消息
     *
     * @param topic      主题
     * @param eventJson  事件json
     * @param delayLevel 延迟等级
     */
    void delayPublish(String topic, String eventJson, int delayLevel);

    /**
     * 定时投递消息
     *
     * @param topic 主题
     * @param event 事件
     * @param time  消息投递时间,单位分钟
     * @param <T>   泛型
     */
    <T extends BaseEvent<?>> void scheduledPublish(String topic, T event, int time);


    /**
     * 定时投递消息
     *
     * @param topic     主题
     * @param eventJson 事件json
     * @param time      消息投递时间,单位分钟
     */
    void scheduledPublish(String topic, String eventJson, int time);

    /**
     * 发送事务消息
     *
     * @param topic 主题
     * @param event 事件
     * @param arg   参数
     * @param <T>   泛型
     * @return 事务消息结果
     */
    <T extends BaseEvent<?>> TransactionSendResult transactionSend(String topic, T event, Object arg);


    /**
     * 发送事务消息
     *
     * @param topic     主题
     * @param eventJson 事件json
     * @param arg       参数
     */
    TransactionSendResult transactionSend(String topic, String eventJson, Object arg);

    /**
     * 发送顺序消息
     *
     * @param topic   主题
     * @param event   事件
     * @param hashKey hashKey
     * @param <T>     泛型
     */
    <T extends BaseEvent<?>> void orderSend(String topic, T event, String hashKey);


    /**
     * 发送顺序消息
     *
     * @param topic     主题
     * @param eventJson 事件json
     * @param hashKey   hashKey
     */
    void orderSend(String topic, String eventJson, String hashKey);
}
