package cn.fuzhizhuang.infrastructure.persist.dao.impl;

import cn.fuzhizhuang.domain.task.model.valobj.TaskStatusVO;
import cn.fuzhizhuang.infrastructure.persist.dao.mapper.TaskMapper;
import cn.fuzhizhuang.infrastructure.persist.po.TaskPO;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 任务Dao
 *
 * @author Fu.zhizhuang
 */
@Component
public class TaskDao extends ServiceImpl<TaskMapper, TaskPO> {

    /**
     * 查询未发送的消息任务
     *
     * @return 未发送任务消息
     */
    public List<TaskPO> queryNoSendMessageTasks() {
        return lambdaQuery().select(TaskPO::getTaskId, TaskPO::getTopic, TaskPO::getStatus, TaskPO::getBody)
                .eq(TaskPO::getStatus, TaskStatusVO.CREATED)
                .lt(TaskPO::getUpdateTime, System.currentTimeMillis() - 60 * 1000)
                .last("limit 10")
                .list();
    }

    /**
     * 更新任务状态
     *
     * @param taskPO 任务
     */
    public void updateStatus(TaskPO taskPO) {
        lambdaUpdate().set(TaskPO::getStatus, taskPO.getStatus())
                .eq(TaskPO::getTaskId, taskPO.getTaskId())
                .update();
    }

    /**
     * 根据任务id查询任务
     *
     * @param taskId 任务id
     * @return 任务
     */
    public TaskPO queryTaskByTaskId(String taskId) {
        return lambdaQuery().select(TaskPO::getTaskId, TaskPO::getTopic, TaskPO::getBody, TaskPO::getStatus)
                .eq(TaskPO::getTaskId, taskId)
                .one();
    }
}
