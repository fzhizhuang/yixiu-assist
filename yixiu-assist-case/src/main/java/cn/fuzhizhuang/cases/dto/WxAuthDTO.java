package cn.fuzhizhuang.cases.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 微信认证DTO
 *
 * @author Fu.zhizhuang
 */
@Data
@Schema(description = "微信认证DTO")
public class WxAuthDTO {

    @Schema(description = "ticket")
    @NotBlank(message = "ticket必填")
    private String ticket;
}
