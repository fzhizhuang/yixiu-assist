package cn.fuzhizhuang.domain.user.model.entity;

import cn.fuzhizhuang.domain.user.model.valobj.UserRoleVO;
import cn.fuzhizhuang.domain.user.model.valobj.UserStatusVO;
import cn.fuzhizhuang.types.utils.IdUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;

/**
 * 用户实体
 *
 * @author Fu.zhizhuang
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserEntity {

    private String userId;
    private String username;
    private String password;
    private String email;
    private String openid;
    private String avatar;
    private UserRoleVO userRole;
    private UserStatusVO userStatus;

    /**
     * 注册
     *
     * @param email  邮箱
     * @param openid 微信openid
     */
    public void register(String email, String openid) {
        this.userId = IdUtil.getIdStr();
        this.username = RandomStringUtils.random(8, true, true);
        this.email = email;
        this.openid = openid;
        this.userRole = UserRoleVO.NORMAL;
        this.userStatus = UserStatusVO.ENABLE;
    }
}
