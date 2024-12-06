package cn.fuzhizhuang.cases.dto;

import cn.fuzhizhuang.types.constant.RegexConstant;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

/**
 * 设置密码DTO
 *
 * @author Fu.zhizhuang
 */
@Data
@Schema(description = "设置密码")
public class SetPasswordDTO {

    @Schema(description = "密码")
    @Pattern(regexp = RegexConstant.PASSWORD_REGEX, message = RegexConstant.PASSWORD_REGEX_TIP)
    private String password;

    @Schema(description = "确认密码")
    @Pattern(regexp = RegexConstant.PASSWORD_REGEX, message = RegexConstant.PASSWORD_REGEX_TIP)
    private String confirmPassword;
}
