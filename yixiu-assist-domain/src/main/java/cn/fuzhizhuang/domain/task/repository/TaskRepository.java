package cn.fuzhizhuang.domain.task.repository;

import cn.fuzhizhuang.domain.task.model.entity.Task;

import java.util.List;

/**
 * 任务仓储
 *
 * @author Fu.zhizhuang
 */
public interface TaskRepository {

    /**
     * 添加任务
     *
     * @param task 任务
     */
    void addTask(Task task);

    /**
     * 查询未发送的消息
     *
     * @return 未发送的消息, 10条
     */
    List<Task> queryNoSendMessageTasks();

    /**
     * 更新任务
     *
     * @param task 任务
     */
    void updateTask(Task task);

    /**
     * 根据任务id查询任务
     *
     * @param taskId 任务id
     * @return 任务
     */
    Task queryTaskByTaskId(String taskId);

    /**
     * 重新发送任务
     *
     * @param task 任务
     */
    void resendTask(Task task);
}
