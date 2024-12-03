package cn.fuzhizhuang.domain.task.model.valobj;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 消息状态值对象
 *
 * @author Fu.zhizhuang
 */
@Getter
@AllArgsConstructor
public enum TaskStatusVO {

    // 0-创建 1-完成 2-失败
    CREATED(0, "创建"),
    FINISHED(1, "完成"),
    FAILED(2, "失败");

    private final Integer code;
    private final String desc;

    /**
     * 根据code获取枚举
     *
     * @param code code
     * @return TaskStatusVO
     */
    public static TaskStatusVO getTaskStatus(Integer code) {
        for (TaskStatusVO taskStatus : values()) {
            if (taskStatus.code.equals(code)) {
                return taskStatus;
            }
        }
        return null;
    }
}
