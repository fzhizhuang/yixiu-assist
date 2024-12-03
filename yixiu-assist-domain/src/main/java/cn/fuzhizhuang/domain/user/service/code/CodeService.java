package cn.fuzhizhuang.domain.user.service.code;

import cn.fuzhizhuang.domain.user.model.valobj.EmailCaptchaVO;

/**
 * 验证码服务
 *
 * @author Fu.zhizhuang
 */
public interface CodeService {

    /**
     * 发送验证码
     *
     * @param email 邮箱
     * @param template 模板
     */
    void sendCaptcha(String email, String template);

    /**
     * 验证邮箱验证码
     *
     * @param email      邮箱
     * @param captcha    验证码
     * @param emailCaptchaVO 验证码类型
     */
    void verifyCaptcha(String email, String captcha, EmailCaptchaVO emailCaptchaVO);
}
