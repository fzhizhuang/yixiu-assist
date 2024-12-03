package cn.fuzhizhuang.infrastructure.persist.dao.mapper;

import cn.fuzhizhuang.infrastructure.persist.po.UserPO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户Mapper
 *
 * @author Fu.zhizhuang
 */
@Mapper
public interface UserMapper extends BaseMapper<UserPO> {
}
