package cn.fuzhizhuang.domain.task.model.entity;

import cn.fuzhizhuang.domain.task.model.valobj.TaskStatusVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 任务实体
 *
 * @author Fu.zhizhuang
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Task {

    // 任务实体(业务ID)
    private String taskId;
    // 主题
    private String topic;
    // 内容
    private String body;
    // 状态
    private TaskStatusVO status;

    /**
     * 创建任务, 默认状态为创建中
     *
     * @param taskId 任务ID
     * @param topic  主题
     * @param body   内容
     */
    public void create(String taskId, String topic, String body) {
        this.taskId = taskId;
        this.topic = topic;
        this.body = body;
        this.status = TaskStatusVO.CREATED;
    }

    /**
     * 更新任务状态为已完成
     *
     * @param taskId 任务ID
     */
    public void updateStatusFinished(String taskId) {
        this.taskId = taskId;
        this.status = TaskStatusVO.FINISHED;
    }

    /**
     * 更新任务状态为失败
     *
     * @param taskId 任务ID
     */
    public void updateStatusFailed(String taskId) {
        this.taskId = taskId;
        this.status = TaskStatusVO.FAILED;
    }
}
