package cn.fuzhizhuang.types.utils;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.net.NetUtil;

/**
 * 雪花ID生成工具
 *
 * @author Fu.zhizhuang
 */
public class IdUtil {

    private static final Long dataCenterId = 1L;
    private static Snowflake snowflake;

    static {
        long workerId;
        try {
            workerId = NetUtil.ipv4ToLong(NetUtil.getLocalhostStr()) & 31;
            snowflake = cn.hutool.core.util.IdUtil.getSnowflake(workerId, dataCenterId);
        } catch (Exception e) {
            workerId = NetUtil.getLocalhostStr().hashCode() & 31;
            snowflake = cn.hutool.core.util.IdUtil.getSnowflake(workerId, dataCenterId);
        }
    }

    public static String getIdStr() {
        return snowflake.nextIdStr();
    }

    public static Long getId() {
        return snowflake.nextId();
    }
}
