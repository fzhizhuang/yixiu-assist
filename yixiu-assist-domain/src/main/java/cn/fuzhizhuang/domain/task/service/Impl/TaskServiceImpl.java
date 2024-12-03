package cn.fuzhizhuang.domain.task.service.Impl;

import cn.fuzhizhuang.domain.task.model.entity.Task;
import cn.fuzhizhuang.domain.task.model.valobj.TaskStatusVO;
import cn.fuzhizhuang.domain.task.repository.TaskRepository;
import cn.fuzhizhuang.domain.task.service.TaskService;
import cn.fuzhizhuang.types.base.BaseEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * 任务实现
 *
 * @author Fu.zhizhuang
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    @Override
    public List<Task> queryNoSendMessageTasks() {
        return taskRepository.queryNoSendMessageTasks();
    }

    @Override
    public void addTask(Task task) {
        taskRepository.addTask(task);
    }

    @Override
    public void updateTask(Task task) {
        taskRepository.updateTask(task);
    }

    @Override
    public Task queryTaskByTaskId(String TaskId) {
        return taskRepository.queryTaskByTaskId(TaskId);
    }

    @Override
    public void resendTask(Task task) {
        taskRepository.resendTask(task);
    }

    @Override
    public void executeTask(BaseEvent<?> event, Runnable runnable) {
        // 获取业务ID
        String biz = event.getBiz();
        // 查询任务状态
        Task task = this.queryTaskByTaskId(biz);
        if (Objects.isNull(task)) return;
        // 判断任务是否为已完成，完成不再处理
        if (TaskStatusVO.FINISHED.equals(task.getStatus())) return;
        // 执行任务
        log.info("开始执行任务: biz:{}", biz);
        runnable.run();
        // 更新任务状态为已完成
        log.info("任务执行完成,更新任务状态为完成 biz:{}", biz);
        task.updateStatusFinished(biz);
        this.updateTask(task);
    }
}
