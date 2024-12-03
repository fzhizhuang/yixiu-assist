package cn.fuzhizhuang.domain.user.model.entity;

import cn.fuzhizhuang.domain.user.model.valobj.EmailCaptchaVO;
import cn.fuzhizhuang.types.utils.AssertUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 邮箱事件
 *
 * @author Fu.zhizhuang
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmailEntity {

    private String email;
    private String subject;
    private String content;

    /**
     * 创建邮件
     *
     * @param email          邮箱
     * @param emailCaptchaVO 邮箱验证码值对象
     * @param captcha        验证码
     * @param expire         过期时间，单位：分钟
     */
    public void createEmail(String email, EmailCaptchaVO emailCaptchaVO, String captcha, Long expire) {
        AssertUtil.isNotBlank(email, "邮箱必填");
        AssertUtil.notNull(emailCaptchaVO, "邮箱验证码类型必填");
        AssertUtil.isNotBlank(captcha, "邮箱验证码必填");
        AssertUtil.isTrue(expire >= 0, "过期时间必须大于等于0");
        String content = emailCaptchaVO.getContent();
        String subject = emailCaptchaVO.getSubject();
        this.email = email;
        this.content = String.format(content, captcha, expire);
        this.subject = subject;
    }
}
