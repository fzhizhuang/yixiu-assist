package cn.fuzhizhuang.infrastructure.persist.dao.impl;

import cn.fuzhizhuang.infrastructure.persist.dao.mapper.UserMapper;
import cn.fuzhizhuang.infrastructure.persist.po.UserPO;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Component;

/**
 * 用户Dao
 *
 * @author Fu.zhizhuang
 */
@Component
public class UserDao extends ServiceImpl<UserMapper, UserPO> {

    /**
     * 根据邮箱或openid查询用户
     *
     * @param account 邮箱或openid
     * @return 用户信息
     */
    public UserPO queryUserByEmailOrOpenid(String account) {
        return lambdaQuery()
                .select(UserPO::getUserId, UserPO::getPassword, UserPO::getStatus)
                .eq(UserPO::getEmail, account)
                .or().eq(UserPO::getOpenid, account)
                .one();
    }
}
