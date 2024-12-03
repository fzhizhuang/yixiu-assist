package cn.fuzhizhuang.cases.service;

import cn.fuzhizhuang.cases.dto.EmailAuthDTO;
import cn.fuzhizhuang.cases.dto.PasswordAuthDTO;
import cn.fuzhizhuang.cases.dto.WxAuthDTO;
import cn.fuzhizhuang.domain.user.model.entity.QrCodeEntity;
import jakarta.validation.Valid;

/**
 * 认证服务
 *
 * @author Fu.zhizhuang
 */
public interface AuthService {

    /**
     * 密码验证
     *
     * @param dto 密码验证参数
     * @return 验证结果
     */
    String passwordAuth(@Valid PasswordAuthDTO dto);

    /**
     * 邮箱验证
     *
     * @param dto 邮箱验证参数
     * @return 验证结果
     */
    String emailAuth(@Valid EmailAuthDTO dto);

    /**
     * 获取微信二维码
     * @return 二维码
     */
    QrCodeEntity getQrCode();

    /**
     * 微信登录
     * @param dto 微信登录参数
     * @return 登录结果
     */
    String wxAuth(@Valid WxAuthDTO dto);
}
