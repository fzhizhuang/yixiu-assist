package cn.fuzhizhuang.trigger.job;

import cn.fuzhizhuang.domain.task.model.entity.Task;
import cn.fuzhizhuang.domain.task.service.TaskService;
import io.micrometer.core.annotation.Timed;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 任务Job
 *
 * @author Fu.zhizhuang
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class TaskJob {

    private final TaskService taskService;

    @Timed(value = "taskJob", description = "重发任务消息定时任务")
    @Scheduled(cron = "0/5 * * * * ?")
    public void execute() {
        try {
            // 查询未发送的消息
            List<Task> tasks = taskService.queryNoSendMessageTasks();
            for (Task task : tasks) {
                // 重发消息
                log.info("重发消息，taskId:{}", task.getTaskId());
                taskService.resendTask(task);
            }
        } catch (Exception e) {
            log.error("重发消息异常", e);
        }
    }
}
