package cn.fuzhizhuang.types.base;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

/**
 * 基础游标分页请求
 *
 * @author Fu.zhizhuang
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "游标翻页请求")
public class BaseCursorPage {

    @Schema(description = "游标(初始为null，后续附带上次翻页的游标)")
    private String cursor;

    @Schema(description = "每页大小")
    @Min(value = 1, message = "每页大小最低为1")
    @Max(value = 100, message = "每页大小最高为100")
    @Builder.Default
    private Integer size = 10;

    /**
     * 构建分页对象
     *
     * @return 分页对象
     */
    public <T> Page<T> plusPage() {
        return new Page<>(1, size, false);
    }

    /**
     * 是否是第一页
     *
     * @return 是否是第一页
     */
    @JsonIgnore
    public Boolean isFirstPage() {
        return StringUtils.isEmpty(cursor);
    }
}
