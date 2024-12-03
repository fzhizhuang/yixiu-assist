package cn.fuzhizhuang.domain.user.service.code.impl;

import cn.fuzhizhuang.domain.user.adapter.event.SendCaptchaEvent;
import cn.fuzhizhuang.domain.user.adapter.repository.CodeRepository;
import cn.fuzhizhuang.domain.user.model.entity.EmailEntity;
import cn.fuzhizhuang.domain.user.model.valobj.EmailCaptchaVO;
import cn.fuzhizhuang.domain.user.service.code.CodeService;
import cn.fuzhizhuang.types.utils.AssertUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 验证码服务实现
 *
 * @author Fu.zhizhuang
 */
@Service
@RequiredArgsConstructor
public class CodeServiceImpl implements CodeService {

    private final CodeRepository codeRepository;

    public static final Long EXPIRE = 5L;

    @Override
    public void sendCaptcha(String email, String template) {
        // 1. 生成验证码
        String captcha = codeRepository.genRandomCaptcha(6);
        // 2. 发送邮件
        EmailCaptchaVO emailCaptchaVO = EmailCaptchaVO.getEmailCaptcha(template);
        AssertUtil.notNull(emailCaptchaVO, "邮件模板不存在");
        EmailEntity emailEntity = new EmailEntity();
        emailEntity.createEmail(email, emailCaptchaVO, captcha, EXPIRE);
        SendCaptchaEvent sendCaptchaEvent = SendCaptchaEvent.build(emailEntity);
        // 3. 保存验证码
        codeRepository.saveCaptcha(email, captcha, emailCaptchaVO, EXPIRE);
        // 4. 发布事件
        codeRepository.publishCaptchaEvent(sendCaptchaEvent);
    }

    @Override
    public void verifyCaptcha(String email, String captcha, EmailCaptchaVO emailCaptchaVO) {
        codeRepository.verifyCaptcha(email, captcha, emailCaptchaVO);
    }
}
