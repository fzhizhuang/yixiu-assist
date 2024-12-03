package cn.fuzhizhuang.domain.user.model.entity;

import cn.fuzhizhuang.domain.user.model.valobj.UserRoleVO;
import cn.fuzhizhuang.domain.user.model.valobj.UserStatusVO;
import cn.fuzhizhuang.types.utils.AssertUtil;
import cn.fuzhizhuang.types.utils.IdUtil;
import cn.hutool.core.util.RandomUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
        this.username = RandomUtil.randomString(12);
        this.email = email;
        this.openid = openid;
        this.userRole = UserRoleVO.NORMAL;
        this.userStatus = UserStatusVO.ENABLE;
    }

    /**
     * 修改用户昵称
     *
     * @param uid      用户ID
     * @param username 用户昵称
     */
    public void modifyUsername(String uid, String username) {
        AssertUtil.isNotBlank(username, "用户昵称为空");
        AssertUtil.isNotBlank(uid, "用户ID为空");
        this.userId = uid;
        this.username = username;
    }
}
