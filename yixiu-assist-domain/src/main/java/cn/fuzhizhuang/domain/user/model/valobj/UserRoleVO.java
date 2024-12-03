package cn.fuzhizhuang.domain.user.model.valobj;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 用户角色值对象
 *
 * @author Fu.zhizhuang
 */
@Getter
@AllArgsConstructor
public enum UserRoleVO {

    ADMIN(1, "管理员"),
    NORMAL(2, "普通用户"),
    ;

    private final Integer code;
    private final String desc;

    // 根据code获取用户角色
    public static UserRoleVO getUserRole(Integer code) {
        for (UserRoleVO userRoleVO : UserRoleVO.values()) {
            if (userRoleVO.code.equals(code)) {
                return userRoleVO;
            }
        }
        return null;
    }
}
