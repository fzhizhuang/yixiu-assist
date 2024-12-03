package cn.fuzhizhuang.types.base;

import lombok.Data;

import java.util.Date;

/**
 * 基础事件
 *
 * @author Fu.zhizhuang
 */
@Data
public class BaseEvent<T> {

    /**
     * 事件id
     */
    private String id;
    /**
     * 业务ID
     */
    private String biz;
    /**
     * 数据
     */
    private T data;
    /**
     * 时间
     */
    private Date time = new Date();
}
