package cn.fuzhizhuang.domain.user.model.entity;

import cn.dev33.satoken.secure.BCrypt;
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

    /**
     * 修改用户头像
     *
     * @param uid       用户ID
     * @param avatarUrl 用户头像
     */
    public void modifyAvatar(String uid, String avatarUrl) {
        AssertUtil.isNotBlank(avatarUrl, "用户头像为空");
        AssertUtil.isNotBlank(uid, "用户ID为空");
        this.userId = uid;
        this.avatar = avatarUrl;
    }

    /**
     * 修改用户密码
     *
     * @param uid      用户ID
     * @param password 密码
     */
    public void modifyPassword(String uid, String password) {
        AssertUtil.isNotBlank(password, "密码为空");
        AssertUtil.isNotBlank(uid, "用户ID为空");
        // 加密密码
        this.password = BCrypt.hashpw(password);
        this.userId = uid;
    }

    /**
     * 修改用户邮箱
     *
     * @param uid      用户ID
     * @param email    邮箱
     */
    public void modifyEmail(String uid, String email) {
        AssertUtil.isNotBlank(email, "邮箱为空");
        AssertUtil.isNotBlank(uid, "用户ID为空");
        this.email = email;
        this.userId = uid;
    }
}
