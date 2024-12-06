package cn.fuzhizhuang.cases.dto;

import cn.fuzhizhuang.domain.user.model.valobj.UserRoleVO;
import cn.fuzhizhuang.domain.user.model.valobj.UserStatusVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 用户信息DTO
 *
 * @author Fu.zhizhuang
 */
@Data
@Schema(description = "用户信息DTO")
public class UserInfoDTO {

    @Schema(description = "用户ID")
    private String userId;
    @Schema(description = "用户昵称")
    private String username;
    @Schema(description = "头像")
    private String avatar;
    @Schema(description = "邮箱")
    private String email;
    @Schema(description = "微信openid")
    private String openid;
    @Schema(description = "角色")
    private UserRoleVO role;
    @Schema(description = "状态")
    private UserStatusVO status;

    @Schema(description = "总额度")
    private BigDecimal total;
    @Schema(description = "已使用额度")
    private BigDecimal used;
    @Schema(description = "剩余额度")
    private BigDecimal surplus;
}
