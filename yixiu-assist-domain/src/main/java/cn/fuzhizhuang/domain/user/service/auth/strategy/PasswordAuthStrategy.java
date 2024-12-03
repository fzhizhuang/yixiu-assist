package cn.fuzhizhuang.domain.user.service.auth.strategy;

import cn.dev33.satoken.secure.BCrypt;
import cn.fuzhizhuang.domain.user.model.entity.AuthEntity;
import cn.fuzhizhuang.domain.user.model.entity.UserEntity;
import cn.fuzhizhuang.domain.user.model.valobj.AuthTypeVO;
import cn.fuzhizhuang.types.exception.BaseResultCode;
import cn.fuzhizhuang.types.exception.BizException;
import cn.fuzhizhuang.types.utils.AssertUtil;
import org.springframework.stereotype.Service;

/**
 * 密码认证策略
 *
 * @author Fu.zhizhuang
 */
@Service
public class PasswordAuthStrategy extends AbstractAuthStrategy {

    @Override
    protected UserEntity register(AuthEntity authEntity) {
        throw new BizException(BaseResultCode.BUSINESS_ERROR, "暂不支持密码注册");
    }

    @Override
    protected UserEntity login(UserEntity userEntity, AuthEntity authEntity) {
        String encryptedPassword = userEntity.getPassword();
        String password = authEntity.getPassword();
        // 校验密码
        boolean checked = BCrypt.checkpw(password, encryptedPassword);
        AssertUtil.isTrue(checked, "密码错误");
        return userEntity;
    }

    @Override
    public AuthTypeVO getAuthType() {
        return AuthTypeVO.PASSWORD;
    }
}
