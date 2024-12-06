package cn.fuzhizhuang.cases.dto;

import cn.fuzhizhuang.types.constant.RegexConstant;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

/**
 * 修改邮箱DTO
 *
 * @author Fu.zhizhuang
 */
@Data
@Schema(description = "修改邮箱")
public class ModifyEmailDTO {

    @Schema(description = "邮箱")
    @NotBlank(message = "邮箱不能为空")
    @Pattern(regexp = RegexConstant.EMAIL_REGEX, message = RegexConstant.EMAIL_REGEX_TIP)
    private String email;

    @Schema(description = "验证码")
    @NotBlank(message = "验证码不能为空")
    @Pattern(regexp = RegexConstant.VERIFY_CODE_REGEX, message = RegexConstant.VERIFY_CODE_REGEX_TIP)
    private String captcha;
}
