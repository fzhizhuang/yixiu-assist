package cn.fuzhizhuang.infrastructure.persist.po;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.util.Date;

/**
 * 任务本地消息表PO
 *
 * @author Fu.zhizhuang
 */
@Data
@TableName("t_task")
public class TaskPO {

    // 主键
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    // 任务ID
    @TableField("task_id")
    private String taskId;

    // 消息topic
    @TableField("topic")
    private String topic;

    // 消息内容
    @TableField("body")
    private String body;

    // 任务状态 0-创建 1-完成 2-失败
    @TableField("status")
    private Integer status;

    // 创建时间
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;

    // 更新时间
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
}
