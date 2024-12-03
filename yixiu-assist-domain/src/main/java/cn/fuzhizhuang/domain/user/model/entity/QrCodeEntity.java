package cn.fuzhizhuang.domain.user.model.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 二维码实体
 *
 * @author Fu.zhizhuang
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "微信二维码")
public class QrCodeEntity {

    @Schema(description = "ticket")
    private String ticket;
    @Schema(description = "url")
    private String url;
}
