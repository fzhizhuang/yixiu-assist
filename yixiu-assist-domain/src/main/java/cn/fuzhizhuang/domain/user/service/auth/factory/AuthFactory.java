package cn.fuzhizhuang.domain.user.service.auth.factory;

import cn.fuzhizhuang.domain.user.service.auth.AuthDomainService;
import cn.fuzhizhuang.types.utils.AssertUtil;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 认证工厂
 *
 * @author Fu.zhizhuang
 */
@Component
@RequiredArgsConstructor
public class AuthFactory {

    private final List<AuthDomainService> authDomainServices;
    private final Map<String, AuthDomainService> authDomainServiceMap;

    @PostConstruct
    public void init() {
        authDomainServices.forEach(authDomainService -> authDomainServiceMap.put(authDomainService.getAuthType().getCode(), authDomainService));
    }

    public AuthDomainService getAuthDomainService(String code) {
        AuthDomainService authDomainService = authDomainServiceMap.get(code);
        AssertUtil.notNull(authDomainService, "未找到对应的认证方式:" + code);
        return authDomainService;
    }
}
