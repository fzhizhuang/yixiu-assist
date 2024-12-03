package cn.fuzhizhuang.domain.user.service.auth.strategy;

import cn.fuzhizhuang.domain.user.model.aggregate.RegisterAggregate;
import cn.fuzhizhuang.domain.user.model.entity.AccountEntity;
import cn.fuzhizhuang.domain.user.model.entity.AuthEntity;
import cn.fuzhizhuang.domain.user.model.entity.UserEntity;
import cn.fuzhizhuang.domain.user.model.valobj.AuthTypeVO;
import cn.fuzhizhuang.domain.user.model.valobj.EmailCaptchaVO;
import cn.fuzhizhuang.domain.user.service.code.CodeService;
import cn.fuzhizhuang.types.utils.AssertUtil;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * 邮箱验证码策略
 *
 * @author Fu.zhizhuang
 */
@Service
public class EmailCaptchaAuthStrategy extends AbstractAuthStrategy {

    @Resource
    private CodeService codeService;

    @Override
    protected UserEntity register(AuthEntity authEntity) {
        String email = authEntity.getAccount();
        String captcha = authEntity.getCaptcha();
        AssertUtil.notNull(email, "邮箱不能为空");
        AssertUtil.notNull(captcha, "验证码不能为空");
        codeService.verifyCaptcha(email, captcha, EmailCaptchaVO.AUTH);
        // 构建注册聚合对象
        UserEntity userEntity = new UserEntity();
        userEntity.register(email, null);
        AccountEntity accountEntity = new AccountEntity();
        accountEntity.register(userEntity.getUserId());
        RegisterAggregate aggregate = RegisterAggregate.builder().userEntity(userEntity).accountEntity(accountEntity).build();
        return userRepository.register(aggregate);
    }

    @Override
    protected UserEntity login(UserEntity userEntity, AuthEntity authEntity) {
        String email = authEntity.getAccount();
        String captcha = authEntity.getCaptcha();
        // 校验验证码
        codeService.verifyCaptcha(email, captcha, EmailCaptchaVO.AUTH);
        return userEntity;
    }

    @Override
    public AuthTypeVO getAuthType() {
        return AuthTypeVO.CAPTCHA;
    }
}
