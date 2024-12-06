package cn.fuzhizhuang.domain.user.adapter.repository;

import cn.fuzhizhuang.domain.user.model.aggregate.RegisterAggregate;
import cn.fuzhizhuang.domain.user.model.entity.AccountEntity;
import cn.fuzhizhuang.domain.user.model.entity.UserEntity;

/**
 * 用户仓储
 *
 * @author Fu.zhizhuang
 */
public interface UserRepository {

    /**
     * 根据邮箱或者openid查询用户
     *
     * @param account 邮箱或者openid
     * @return 用户实体
     */
    UserEntity queryUserByEmailOrOpenid(String account);


    /**
     * 注册用户
     *
     * @param aggregate 注册聚合
     * @return 用户实体
     */
    UserEntity register(RegisterAggregate aggregate);

    /**
     * 根据ticket查询openid
     *
     * @param ticket ticket
     * @return openid
     */
    String queryOpenidByTicket(String ticket);

    /**
     * 保存ticket
     *
     * @param ticket ticket
     * @param openId openid
     */
    void saveQrCodeTicket(String ticket, String openId);

    /**
     * 移除ticket
     *
     * @param ticket ticket
     */
    void removeQrCodeTicket(String ticket);

    /**
     * 根据用户id查询用户
     *
     * @param userId 用户id
     * @return 用户实体
     */
    UserEntity queryUserByUserId(String userId);

    /**
     * 根据用户id查询账户
     *
     * @param userId 用户id
     * @return 账户实体
     */
    AccountEntity queryAccountByUserId(String userId);

    /**
     * 修改用户昵称
     *
     * @param userEntity 用户实体
     */
    void modifyUsername(UserEntity userEntity);

    /**
     * 修改用户头像
     *
     * @param userEntity 用户实体
     */
    void modifyAvatar(UserEntity userEntity);

    /**
     * 修改用户密码
     * @param userEntity 用户实体
     */
    void modifyPassword(UserEntity userEntity);

    /**
     * 修改用户邮箱
     * @param userEntity 用户实体
     */
    void modifyEmail(UserEntity userEntity);
}
