package cn.fuzhizhuang.domain.user.service.auth.strategy;

import cn.dev33.satoken.stp.StpUtil;
import cn.fuzhizhuang.domain.user.adapter.repository.UserRepository;
import cn.fuzhizhuang.domain.user.model.entity.AuthEntity;
import cn.fuzhizhuang.domain.user.model.entity.UserEntity;
import cn.fuzhizhuang.domain.user.model.valobj.UserStatusVO;
import cn.fuzhizhuang.domain.user.service.auth.AuthDomainService;
import cn.fuzhizhuang.types.utils.AssertUtil;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * 抽象认证策略
 *
 * @author Fu.zhizhuang
 */
@Service
public abstract class AbstractAuthStrategy implements AuthDomainService {

    @Resource
    protected UserRepository userRepository;

    @Override
    public String auth(AuthEntity authEntity) {
        AssertUtil.notNull(authEntity, "认证实体信息为空");
        String account = authEntity.getAccount();
        // 查询账号是否注册
        UserEntity userEntity = userRepository.queryUserByEmailOrOpenid(account);
        // 账号不存在
        if (Objects.isNull(userEntity)) {
            userEntity = register(authEntity);
        } else {
            // 账号已存在,校验账号状态
            UserStatusVO userStatus = userEntity.getUserStatus();
            AssertUtil.isTrue(UserStatusVO.ENABLE.equals(userStatus), "账号已禁用");
            userEntity = login(userEntity, authEntity);
        }
        StpUtil.logout(userEntity.getUserId());
        StpUtil.login(userEntity.getUserId());
        // 返回token
        return StpUtil.getTokenValueByLoginId(userEntity.getUserId());
    }

    protected abstract UserEntity register(AuthEntity authEntity);
    protected abstract UserEntity login(UserEntity userEntity, AuthEntity authEntity);
}
