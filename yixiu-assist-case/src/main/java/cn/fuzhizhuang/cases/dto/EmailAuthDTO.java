package cn.fuzhizhuang.cases.dto;

import cn.fuzhizhuang.types.constant.RegexConstant;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

/**
 * 邮箱认证DTO
 *
 * @author Fu.zhizhuang
 */
@Data
@Schema(description = "邮箱认证")
public class EmailAuthDTO {

    @Schema(description = "邮箱")
    @NotBlank(message = "邮箱必填")
    @Pattern(regexp = RegexConstant.EMAIL_REGEX, message = RegexConstant.EMAIL_REGEX_TIP)
    private String email;

    @Schema(description = "验证码")
    @NotBlank(message = "验证码必填")
    @Pattern(regexp = RegexConstant.VERIFY_CODE_REGEX, message = RegexConstant.VERIFY_CODE_REGEX_TIP)
    private String captcha;
}
