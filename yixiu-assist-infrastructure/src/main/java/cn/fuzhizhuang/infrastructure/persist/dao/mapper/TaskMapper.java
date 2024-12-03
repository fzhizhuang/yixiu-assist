package cn.fuzhizhuang.infrastructure.persist.dao.mapper;

import cn.fuzhizhuang.infrastructure.persist.po.TaskPO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 任务Mapper
 *
 * @author Fu.zhizhuang
 */
@Mapper
public interface TaskMapper extends BaseMapper<TaskPO> {
}
