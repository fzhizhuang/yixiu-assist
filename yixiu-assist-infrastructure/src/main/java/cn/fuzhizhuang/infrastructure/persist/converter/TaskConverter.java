package cn.fuzhizhuang.infrastructure.persist.converter;

import cn.fuzhizhuang.domain.task.model.entity.Task;
import cn.fuzhizhuang.domain.task.model.valobj.TaskStatusVO;
import cn.fuzhizhuang.infrastructure.persist.po.TaskPO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

import java.util.Objects;

/**
 * 任务converter
 *
 * @author Fu.zhizhuang
 */
@Mapper(componentModel = "spring")
public interface TaskConverter {

    @Mappings({
            @Mapping(target = "taskId", source = "taskId"),
            @Mapping(target = "topic", source = "topic"),
            @Mapping(target = "body", source = "body"),
            @Mapping(target = "status", source = "status", qualifiedByName = "getStatus"),
    })
    TaskPO task2TaskPO(Task task);

    @Named("getStatus")
    default Integer getStatus(TaskStatusVO taskStatusVO) {
        if (Objects.isNull(taskStatusVO)) return null;
        return taskStatusVO.getCode();
    }

    @Mappings({
            @Mapping(target = "taskId", source = "taskId"),
            @Mapping(target = "topic", source = "topic"),
            @Mapping(target = "body", source = "body"),
            @Mapping(target = "status", source = "status", qualifiedByName = "getTaskStatusVO"),
    })
    Task taskPO2Task(TaskPO taskPO);

    @Named("getTaskStatusVO")
    default TaskStatusVO getTaskStatusVO(Integer status) {
        return TaskStatusVO.getTaskStatus(status);
    }

}
