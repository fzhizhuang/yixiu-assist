package cn.fuzhizhuang.domain.user.adapter.repository;

import cn.fuzhizhuang.domain.user.model.aggregate.RegisterAggregate;
import cn.fuzhizhuang.domain.user.model.entity.UserEntity;
import cn.fuzhizhuang.domain.user.model.valobj.EmailCaptchaVO;

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
     * 验证邮箱验证码
     *
     * @param email      邮箱
     * @param captcha    验证码
     * @param emailCaptchaVO 验证码类型
     */
    void verifyCaptcha(String email, String captcha, EmailCaptchaVO emailCaptchaVO);

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
     * @param ticket  ticket
     * @param openId openid
     */
    void saveQrCodeTicket(String ticket, String openId);

    /**
     * 移除ticket
     *
     * @param ticket ticket
     */
    void removeQrCodeTicket(String ticket);
}
