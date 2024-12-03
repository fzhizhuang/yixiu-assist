package cn.fuzhizhuang.infrastructure.persist.dao.impl;

import cn.fuzhizhuang.infrastructure.persist.dao.mapper.UserMapper;
import cn.fuzhizhuang.infrastructure.persist.po.UserPO;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Objects;

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
        return lambdaQuery().select(UserPO::getUserId, UserPO::getPassword, UserPO::getStatus).eq(UserPO::getEmail, account).or().eq(UserPO::getOpenid, account).one();
    }

    /**
     * 根据用户id查询用户
     *
     * @param userId 用户id
     * @return 用户信息
     */
    public UserPO queryUserByUserId(String userId) {
        return lambdaQuery().select(UserPO::getUserId, UserPO::getPassword, UserPO::getUsername, UserPO::getEmail, UserPO::getOpenid, UserPO::getAvatar).eq(UserPO::getUserId, userId).one();
    }

    /**
     * 更新用户信息
     *
     * @param userPO 用户信息
     */
    public void updateUser(UserPO userPO) {
        lambdaUpdate()
                .set(StringUtils.isNotBlank(userPO.getUsername()), UserPO::getUsername, userPO.getUsername())
                .set(StringUtils.isNotBlank(userPO.getAvatar()), UserPO::getAvatar, userPO.getAvatar())
                .set(StringUtils.isNotBlank(userPO.getPassword()), UserPO::getPassword, userPO.getPassword())
                .set(StringUtils.isNotBlank(userPO.getEmail()), UserPO::getEmail, userPO.getEmail())
                .set(Objects.nonNull(userPO.getRole()), UserPO::getRole, userPO.getRole())
                .set(Objects.nonNull(userPO.getStatus()), UserPO::getStatus, userPO.getStatus())
                .eq(UserPO::getUserId, userPO.getUserId())
                .update();
    }
}
