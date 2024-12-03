package cn.fuzhizhuang.cases.dto;

import cn.fuzhizhuang.types.constant.RegexConstant;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

/**
 * @author Fu.zhizhuang
 */
@Data
@Schema(description = "密码认证DTO")
public class PasswordAuthDTO {

    @Schema(description = "邮箱")
    @NotBlank(message = "邮箱必填")
    @Email(message = "邮箱格式不正确")
    private String email;

    @Schema(description = "密码")
    @NotBlank(message = "密码必填")
    @Pattern(regexp = RegexConstant.PASSWORD_REGEX, message = RegexConstant.PASSWORD_REGEX_TIP)
    private String password;
}
