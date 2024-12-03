package cn.fuzhizhuang.domain.user.model.valobj;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 用户状态值对象
 *
 * @author Fu.zhizhuang
 */
@Getter
@AllArgsConstructor
public enum UserStatusVO {

    // 0-禁用 1-启用
    DISABLE(0, "禁用"),
    ENABLE(1, "启用");

    private final Integer code;
    private final String desc;

    // 根据code获取用户状态
    public static UserStatusVO getUserStatus(Integer code) {
        for (UserStatusVO userStatus : UserStatusVO.values()) {
            if (userStatus.getCode().equals(code)) {
                return userStatus;
            }
        }
        return null;
    }
}
