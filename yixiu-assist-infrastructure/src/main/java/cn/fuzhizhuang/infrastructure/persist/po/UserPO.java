package cn.fuzhizhuang.infrastructure.persist.po;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.util.Date;

/**
 * 用户PO
 *
 * @author Fu.zhizhuang
 */
@Data
@TableName("t_user")
public class UserPO {

    // 自增ID
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    // 用户ID
    @TableField(value = "user_id")
    private String userId;
    // 用户昵称
    @TableField(value = "username")
    private String username;
    // 密码
    @TableField(value = "password")
    private String password;
    // 邮箱
    @TableField(value = "email")
    private String email;
    // 微信ID
    @TableField(value = "openid")
    private String openid;
    // 头像
    @TableField(value = "avatar")
    private String avatar;
    // 用户角色 1-管理员 2-普通用户 3-VIP
    @TableField(value = "role")
    private Integer role;
    // 是否处于激活状态 0-禁用 1-启用
    @TableField(value = "status")
    private Integer status;
    // 创建时间
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;
    // 更新时间
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
}
