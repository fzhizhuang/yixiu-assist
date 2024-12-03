package cn.fuzhizhuang.types.constant;

/**
 * 缓存常量
 *
 * @author Fu.zhizhuang
 */
public class CacheKey {

    public static final String CAPTCHA_PREFIX = "captcha:";

    // 微信前缀
    public static final String WX_TOKEN_PREFIX = "wx:token:";
    public static final String WX_TICKET_PREFIX = "wx:ticket:";

    /**
     * 构建缓存key
     *
     * @param prefix 前缀
     * @param keys   键值
     * @return 缓存key
     */
    public static String buildKey(Object prefix, String... keys) {
        return prefix + String.join("_", keys);
    }

    /**
     * 构建缓存key，不带前缀
     *
     * @param keys 键值
     * @return 缓存key
     */
    public static String buildKeyWithoutPrefix(String... keys) {
        return String.join("_", keys);
    }
}
