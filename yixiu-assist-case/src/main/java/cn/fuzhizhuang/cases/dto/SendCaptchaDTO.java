package cn.fuzhizhuang.cases.dto;

import cn.fuzhizhuang.types.constant.RegexConstant;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

/**
 * 发送验证码DTO
 *
 * @author Fu.zhizhuang
 */
@Data
@Schema(description = "发送验证码DTO")
public class SendCaptchaDTO {

    @Schema(description = "邮箱")
    @NotBlank(message = "邮箱必填")
    @Pattern(regexp = RegexConstant.EMAIL_REGEX, message = RegexConstant.EMAIL_REGEX_TIP)
    private String email;

    @Schema(description = "模板")
    @NotBlank(message = "模板必填")
    private String template;
}
