package cn.fuzhizhuang.cases.service.impl;

import cn.fuzhizhuang.cases.dto.EmailAuthDTO;
import cn.fuzhizhuang.cases.dto.PasswordAuthDTO;
import cn.fuzhizhuang.cases.dto.SendCaptchaDTO;
import cn.fuzhizhuang.cases.dto.WxAuthDTO;
import cn.fuzhizhuang.cases.service.AuthService;
import cn.fuzhizhuang.domain.user.model.entity.AuthEntity;
import cn.fuzhizhuang.domain.user.model.entity.QrCodeEntity;
import cn.fuzhizhuang.domain.user.model.valobj.AuthTypeVO;
import cn.fuzhizhuang.domain.user.model.valobj.EmailCaptchaVO;
import cn.fuzhizhuang.domain.user.service.auth.AuthDomainService;
import cn.fuzhizhuang.domain.user.service.auth.WxDomainService;
import cn.fuzhizhuang.domain.user.service.auth.factory.AuthFactory;
import cn.fuzhizhuang.domain.user.service.code.CodeService;
import cn.fuzhizhuang.types.utils.AssertUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 认证服务实现
 *
 * @author Fu.zhizhuang
 */
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthFactory factory;
    private final WxDomainService wxDomainService;
    private final CodeService codeService;

    @Override
    public String passwordAuth(PasswordAuthDTO dto) {
        // 构建认证实体
        AuthEntity authEntity = new AuthEntity();
        authEntity.setAccount(dto.getEmail());
        authEntity.setPassword(dto.getPassword());
        // 获取认证策略
        AuthDomainService authDomainService = factory.getAuthDomainService(AuthTypeVO.PASSWORD.getCode());
        return authDomainService.auth(authEntity);
    }

    @Override
    public String emailAuth(EmailAuthDTO dto) {
        // 构建认证实体
        AuthEntity authEntity = new AuthEntity();
        authEntity.setAccount(dto.getEmail());
        authEntity.setCaptcha(dto.getCaptcha());
        // 获取认证策略
        AuthDomainService authDomainService = factory.getAuthDomainService(AuthTypeVO.CAPTCHA.getCode());
        return authDomainService.auth(authEntity);
    }

    @Override
    public QrCodeEntity getQrCode() {
        // 1. 生成ticket
        String ticket = wxDomainService.createQrCodeTicket();
        // 2. 生成二维码URL
        String qrCodeUrl = wxDomainService.showQrCode(ticket);
        // 3. 返回信息
        QrCodeEntity qrCodeEntity = new QrCodeEntity();
        qrCodeEntity.setTicket(ticket);
        qrCodeEntity.setUrl(qrCodeUrl);
        return qrCodeEntity;
    }

    @Override
    public String wxAuth(WxAuthDTO dto) {
        String ticket = dto.getTicket();
        // 获取openid
        String openid = wxDomainService.checkQrCodeTicket(ticket);
        AssertUtil.notNull(openid, "扫码中");
        // 构建认证实体
        AuthEntity authEntity = new AuthEntity();
        authEntity.setAccount(openid);
        // 获取认证策略
        AuthDomainService authDomainService = factory.getAuthDomainService(AuthTypeVO.WECHAT.getCode());
        return authDomainService.auth(authEntity);
    }

    @Override
    public void sendCaptcha(SendCaptchaDTO dto) {
        String email = dto.getEmail();
        String template = dto.getTemplate();
        EmailCaptchaVO emailCaptcha = EmailCaptchaVO.getEmailCaptcha(template);
        AssertUtil.notNull(emailCaptcha, "邮件模板不存在");
        codeService.sendCaptcha(email, template);
    }
}
