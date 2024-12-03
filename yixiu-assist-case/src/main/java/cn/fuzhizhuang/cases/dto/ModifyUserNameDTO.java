package cn.fuzhizhuang.cases.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 修改用户昵称DTO
 *
 * @author Fu.zhizhuang
 */
@Data
@Schema(description = "修改用户昵称DTO")
public class ModifyUserNameDTO {

    @Schema(description = "用户昵称")
    @NotBlank(message = "用户昵称为空")
    private String username;
}
