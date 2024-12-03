package cn.fuzhizhuang.types.base;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 游标分页请求响应
 *
 * @author Fu.zhizhuang
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "游标翻页响应")
public class CursorPageResp<T> {

    @Schema(description = "游标,下次翻页带上的参数")
    private String cursor;

    @Schema(description = "是否为最后一页")
    private Boolean isLast = Boolean.FALSE;

    @Schema(description = "数据列表")
    private List<T> list;


    /**
     * 判断是否为空
     *
     * @return true/false
     */
    @JsonIgnore
    public Boolean isEmpty() {
        return CollectionUtils.isEmpty(list);
    }

    /**
     * 构建分页响应
     *
     * @param pageResp 分页请求
     * @param list     数据列表
     * @return 分页响应
     */
    public static <T> CursorPageResp<T> build(CursorPageResp<T> pageResp, List<T> list) {
        return CursorPageResp.<T>builder()
                .cursor(pageResp.getCursor())
                .isLast(pageResp.getIsLast())
                .list(list)
                .build();
    }

    /**
     * 构建空分页响应
     *
     * @return 分页响应
     */
    public static <T> CursorPageResp<T> empty() {
        return CursorPageResp.<T>builder()
                .isLast(Boolean.TRUE)
                .list(new ArrayList<>())
                .build();
    }
}
