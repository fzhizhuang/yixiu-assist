package cn.fuzhizhuang.types.base;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 分页响应
 *
 * @author Fu.zhizhuang
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "分页响应")
public class PageResp<T> {

    @Schema(description = "页码")
    private Integer pageNum;
    @Schema(description = "每页大小")
    private Integer pageSize;
    @Schema(description = "数据总数")
    private Long total;
    @Schema(description = "数据列表")
    private List<T> list;
}
