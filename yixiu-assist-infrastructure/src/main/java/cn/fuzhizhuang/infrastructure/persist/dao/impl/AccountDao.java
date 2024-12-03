package cn.fuzhizhuang.infrastructure.persist.dao.impl;

import cn.fuzhizhuang.infrastructure.persist.dao.mapper.AccountMapper;
import cn.fuzhizhuang.infrastructure.persist.po.AccountPO;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Component;

/**
 * 账户Dao
 *
 * @author Fu.zhizhuang
 */
@Component
public class AccountDao extends ServiceImpl<AccountMapper, AccountPO> {
}
