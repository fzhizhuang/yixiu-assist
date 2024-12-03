package cn.fuzhizhuang.domain.user.model.valobj;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 认证类型值对象
 *
 * @author Fu.zhizhuang
 */
@Getter
@AllArgsConstructor
public enum AuthTypeVO {

    PASSWORD("password", "密码"),
    CAPTCHA("captcha", "验证码"),
    WECHAT("wechat", "微信");

    private final String code;
    private final String desc;

}
