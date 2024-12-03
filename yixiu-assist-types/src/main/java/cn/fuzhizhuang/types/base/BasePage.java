package cn.fuzhizhuang.types.base;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 基础分页请求
 *
 * @author Fu.zhizhuang
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "基础分页请求")
public class BasePage {

    @Schema(description = "页码")
    @Min(value = 1, message = "页码最低为1")
    @Builder.Default
    private Integer pageNum = 1;

    @Schema(description = "每页大小")
    @Min(value = 1, message = "每页大小最低为1")
    @Max(value = 100, message = "每页大小最高为100")
    @Builder.Default
    private Integer pageSize = 10;

    public <T> Page<T> plusPage() {
        return new Page<>(pageNum, pageSize);
    }
}
