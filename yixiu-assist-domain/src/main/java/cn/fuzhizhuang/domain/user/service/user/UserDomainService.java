package cn.fuzhizhuang.domain.user.service.user;

import cn.fuzhizhuang.domain.user.model.entity.AccountEntity;
import cn.fuzhizhuang.domain.user.model.entity.UserEntity;

/**
 * 用户服务
 *
 * @author Fu.zhizhuang
 */
public interface UserDomainService {

    /**
     * 根据用户ID查询用户信息
     *
     * @param userId 用户ID
     * @return 用户信息
     */
    UserEntity queryUserByUserId(String userId);

    /**
     * 根据用户ID查询用户账户信息
     *
     * @param userId 用户ID
     * @return 用户账户信息
     */
    AccountEntity queryAccountByUserId(String userId);

    /**
     * 修改用户昵称
     *
     * @param uid      用户ID
     * @param username 新昵称
     */
    void modifyUsername(String uid, String username);

    /**
     * 更新用户缓存
     *
     * @param uid 用户ID
     * @return UserEntity
     */
    UserEntity updateUserCache(String uid);

    /**
     * 修改用户头像
     *
     * @param uid      用户ID
     * @param avatarUrl 新头像地址
     */
    void modifyAvatar(String uid, String avatarUrl);

}
