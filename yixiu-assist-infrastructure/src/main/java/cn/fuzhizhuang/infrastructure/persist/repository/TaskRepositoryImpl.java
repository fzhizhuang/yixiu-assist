package cn.fuzhizhuang.infrastructure.persist.repository;

import cn.fuzhizhuang.domain.task.model.entity.Task;
import cn.fuzhizhuang.domain.task.repository.TaskRepository;
import cn.fuzhizhuang.infrastructure.mq.EventPublisher;
import cn.fuzhizhuang.infrastructure.persist.converter.TaskConverter;
import cn.fuzhizhuang.infrastructure.persist.dao.impl.TaskDao;
import cn.fuzhizhuang.infrastructure.persist.po.TaskPO;
import cn.fuzhizhuang.types.utils.AssertUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 * 任务仓储实现
 *
 * @author Fu.zhizhuang
 */
@Repository
@RequiredArgsConstructor
public class TaskRepositoryImpl implements TaskRepository {

    private final EventPublisher eventPublisher;
    private final TaskConverter taskConverter;
    private final TaskDao taskDao;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addTask(Task task) {
        AssertUtil.notNull(task, "任务不能为空");
        TaskPO taskPO = taskConverter.task2TaskPO(task);
        taskDao.save(taskPO);
    }

    @Override
    public List<Task> queryNoSendMessageTasks() {
        List<TaskPO> taskPOList = taskDao.queryNoSendMessageTasks();
        if (Objects.nonNull(taskPOList)) return taskPOList.stream().map(taskConverter::taskPO2Task).toList();
        return List.of();
    }

    @Override
    public void updateTask(Task task) {
        AssertUtil.notNull(task, "任务不能为空");
        TaskPO taskPO = taskConverter.task2TaskPO(task);
        taskDao.updateStatus(taskPO);
    }

    @Override
    public Task queryTaskByTaskId(String taskId) {
        TaskPO taskPO = taskDao.queryTaskByTaskId(taskId);
        if (Objects.nonNull(taskPO)) return taskConverter.taskPO2Task(taskPO);
        return null;
    }

    @Override
    public void resendTask(Task task) {
        if (Objects.isNull(task)) return;
        String topic = task.getTopic();
        String body = task.getBody();
        eventPublisher.publish(topic, body);
    }
}
