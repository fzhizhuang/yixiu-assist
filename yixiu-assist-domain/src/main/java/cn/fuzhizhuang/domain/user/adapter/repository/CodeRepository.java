package cn.fuzhizhuang.domain.user.adapter.repository;

import cn.fuzhizhuang.domain.user.adapter.event.SendCaptchaEvent;
import cn.fuzhizhuang.domain.user.model.valobj.EmailCaptchaVO;

/**
 * 验证码仓储
 *
 * @author Fu.zhizhuang
 */
public interface CodeRepository {

    /**
     * 生成随机验证码
     *
     * @param length 验证码长度
     * @return 验证码
     */
    String genRandomCaptcha(int length);

    /**
     * 保存验证码
     *
     * @param email      邮箱
     * @param captcha    验证码
     * @param emailCaptchaVO 验证码类型
     * @param expire     过期时间
     */
    void saveCaptcha(String email, String captcha, EmailCaptchaVO emailCaptchaVO, long expire);

    /**
     * 发送验证码事件
     *
     * @param sendCaptchaEvent 发送验证码事件
     */
    void publishCaptchaEvent(SendCaptchaEvent sendCaptchaEvent);

    /**
     * 验证邮箱验证码
     *
     * @param email      邮箱
     * @param captcha    验证码
     * @param emailCaptchaVO 验证码类型
     */
    void verifyCaptcha(String email, String captcha, EmailCaptchaVO emailCaptchaVO);
}
