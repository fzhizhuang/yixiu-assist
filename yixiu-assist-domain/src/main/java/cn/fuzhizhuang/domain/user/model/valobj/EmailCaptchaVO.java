package cn.fuzhizhuang.domain.user.model.valobj;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 邮箱验证码值对象
 *
 * @author Fu.zhizhuang
 */
@Getter
@AllArgsConstructor
public enum EmailCaptchaVO {

    AUTH("AUTH", "您正在进行认证操作,验证码为:%s,%s分钟内有效,请勿将验证码告知他人.", "认证验证码"),
    MODIFY_PASSWORD("MODIFY_PASSWORD", "您正在进行修改密码操作,验证码为:%s,%s分钟内有效,请勿将验证码告知他人.", "修改密码验证码"),
    MODIFY_EMAIL("MODIFY_EMAIL", "您正在进行修改邮箱操作,验证码为:%s,%s分钟内有效,请勿将验证码告知他人.", "修改邮箱验证码");

    private final String code;
    private final String content;
    private final String subject;

    public static EmailCaptchaVO getEmailCaptcha(String code) {
        for (EmailCaptchaVO emailCaptchaVO : EmailCaptchaVO.values()) {
            if (emailCaptchaVO.getCode().equals(code)) {
                return emailCaptchaVO;
            }
        }
        return null;
    }
}
