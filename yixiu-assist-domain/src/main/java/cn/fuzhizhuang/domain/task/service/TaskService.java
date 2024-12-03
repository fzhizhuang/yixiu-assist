package cn.fuzhizhuang.domain.task.service;

import cn.fuzhizhuang.domain.task.model.entity.Task;
import cn.fuzhizhuang.types.base.BaseEvent;

import java.util.List;

/**
 * 任务接口
 *
 * @author Fu.zhizhuang
 */
public interface TaskService {

    /**
     * 查询未发送的消息任务
     *
     * @return 未发送的消息任务，默认10条数据
     */
    List<Task> queryNoSendMessageTasks();

    /**
     * 添加任务
     *
     * @param task 任务
     */
    void addTask(Task task);

    /**
     * 更新任务
     *
     * @param task 任务
     */
    void updateTask(Task task);

    /**
     * 根据任务id查询任务
     *
     * @param TaskId 任务id
     * @return 任务
     */
    Task queryTaskByTaskId(String TaskId);

    /**
     * 重发任务
     *
     * @param task 任务
     */
    void resendTask(Task task);

    /**
     * 执行任务
     *
     * @param event    事件
     * @param runnable 执行逻辑
     */
    void executeTask(BaseEvent<?> event, Runnable runnable);
}
