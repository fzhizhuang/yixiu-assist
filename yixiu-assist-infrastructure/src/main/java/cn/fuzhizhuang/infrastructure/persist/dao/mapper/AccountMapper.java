package cn.fuzhizhuang.infrastructure.persist.dao.mapper;

import cn.fuzhizhuang.infrastructure.persist.po.AccountPO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 账户mapper
 *
 * @author Fu.zhizhuang
 */
@Mapper
public interface AccountMapper extends BaseMapper<AccountPO> {
}
