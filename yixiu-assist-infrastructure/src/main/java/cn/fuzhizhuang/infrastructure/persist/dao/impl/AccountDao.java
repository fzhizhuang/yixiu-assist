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

    /**
     * 根据用户id查询账户信息
     *
     * @param userId 用户id
     * @return 账户信息
     */
    public AccountPO queryAccountByUserId(String userId) {
        return lambdaQuery().select(AccountPO::getTotalBalance, AccountPO::getUsedBalance, AccountPO::getAvailableBalance).eq(AccountPO::getUserId, userId).one();
    }
}
