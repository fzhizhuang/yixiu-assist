package cn.fuzhizhuang.domain.user.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 认证实体
 *
 * @author Fu.zhizhuang
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthEntity {

    // 邮箱或openid
    private String account;
    // 密码
    private String password;
    // 验证码
    private String captcha;
}
