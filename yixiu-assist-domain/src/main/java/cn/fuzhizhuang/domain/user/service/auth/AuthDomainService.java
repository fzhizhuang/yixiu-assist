package cn.fuzhizhuang.domain.user.service.auth;

import cn.fuzhizhuang.domain.user.model.entity.AuthEntity;
import cn.fuzhizhuang.domain.user.model.valobj.AuthTypeVO;

/**
 * 认证领域服务
 *
 * @author Fu.zhizhuang
 */
public interface AuthDomainService {

    /**
     * 认证
     *
     * @param authEntity 认证实体
     * @return 认证token
     */
    String auth(AuthEntity authEntity);

    /**
     * 获取认证类型
     *
     * @return 认证类型
     */
    AuthTypeVO getAuthType();
}
