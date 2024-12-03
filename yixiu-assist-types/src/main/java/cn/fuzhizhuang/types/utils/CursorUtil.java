package cn.fuzhizhuang.types.utils;

import cn.fuzhizhuang.types.base.BaseCursorPage;
import cn.fuzhizhuang.types.base.CursorPageResp;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;
import java.util.Optional;
import java.util.function.Consumer;

/**
 * 游标翻页工具类
 *
 * @author Fu.zhizhuang
 */
public class CursorUtil {

    /**
     * 游标翻页，小于cursor，倒序查询
     *
     * @param mapper       mapper
     * @param req          请求参数
     * @param initWrapper  提供的扩展点，业务方可以在sql中拼接一些查询条件
     * @param cursorColumn 游标字段，用于游标查询，以及后续的游标计算
     * @param clazz        游标字段类型
     * @param ltFlag       小于cursor
     * @param descFlag     降序
     * @param <T>          实体类
     * @return 分页结果
     */
    public static <T> CursorPageResp<T> getCursorPage(IService<T> mapper, BaseCursorPage req, Consumer<LambdaQueryWrapper<T>> initWrapper, SFunction<T, ?> cursorColumn, Class<?> clazz, boolean ltFlag, boolean descFlag) {
        LambdaQueryWrapper<T> wrapper = new LambdaQueryWrapper<>();
        // 游标字段
        if (StringUtils.isNoneBlank(req.getCursor())) {
            if (ltFlag) wrapper.lt(cursorColumn, parseCursor(req.getCursor(), clazz));
            else wrapper.gt(cursorColumn, parseCursor(req.getCursor(), clazz));
        }
        // 游标翻页方向
        if (!descFlag) wrapper.orderByAsc(cursorColumn);
        else wrapper.orderByDesc(cursorColumn);

        if (initWrapper != null) {
            // 额外查询条件
            initWrapper.accept(wrapper);
        }
        Page<T> page = mapper.page(req.plusPage(), wrapper);
        // 计算游标位置
        String cursor = Optional.ofNullable(CollectionUtil.getLast(page.getRecords())).map(cursorColumn).map(CursorUtil::toCursor).orElse(null);
        // 是否是最后一页
        Boolean isLast = page.getRecords().size() != req.getSize();
        return new CursorPageResp<>(cursor, isLast, page.getRecords());
    }

    /**
     * 游标翻页，小于cursor，倒序查询
     * <p>eg: 14,13,12,11,10,9,8,7,6,5</p>
     * <p>eg: 4,3,2,1</p>
     *
     * @param mapper       mapper
     * @param req          请求参数
     * @param initWrapper  提供的扩展点，业务方可以在sql中拼接一些查询条件
     * @param cursorColumn 游标字段，用于游标查询，以及后续的游标计算
     * @param clazz        游标字段类型
     * @return 分页结果
     */
    public static <T> CursorPageResp<T> getCursorPageLtDesc(IService<T> mapper, BaseCursorPage req, Consumer<LambdaQueryWrapper<T>> initWrapper, SFunction<T, ?> cursorColumn, Class<?> clazz) {
        return getCursorPage(mapper, req, initWrapper, cursorColumn, clazz, true, true);
    }

    /**
     * 游标翻页，大于cursor，正序查询
     * <p>eg: 1,2,3,4,5,6,7,8,9,10</p>
     * <p>eg: 11,12,13,14,15,16,17,18,19,20</p>
     *
     * @param mapper       mapper
     * @param req          请求参数
     * @param initWrapper  提供的扩展点，业务方可以在sql中拼接一些查询条件
     * @param cursorColumn 游标字段，用于游标查询，以及后续的游标计算
     * @param clazz        游标字段类型
     * @return 分页结果
     */
    public static <T> CursorPageResp<T> getCursorPageGtAsc(IService<T> mapper, BaseCursorPage req, Consumer<LambdaQueryWrapper<T>> initWrapper, SFunction<T, ?> cursorColumn, Class<?> clazz) {
        return getCursorPage(mapper, req, initWrapper, cursorColumn, clazz, false, false);
    }


    /**
     * 解析游标
     *
     * @param cursor 游标
     * @param clazz  游标字段类型
     * @return 解析后的游标
     */
    private static Object parseCursor(String cursor, Class<?> clazz) {
        if (clazz.isInstance(Date.class)) {
            return new Date(Long.parseLong(cursor));
        } else {
            return cursor;
        }
    }

    /**
     * 将游标转换为字符串
     *
     * @param cursor 游标
     * @return 字符串
     */
    private static String toCursor(Object cursor) {
        if (cursor instanceof Date) {
            return String.valueOf(((Date) cursor).getTime());
        } else {
            return cursor.toString();
        }
    }
}
