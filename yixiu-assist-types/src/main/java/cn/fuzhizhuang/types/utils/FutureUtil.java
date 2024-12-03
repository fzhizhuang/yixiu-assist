package cn.fuzhizhuang.types.utils;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.function.BinaryOperator;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * 美团的CompleteFuture封装工具类
 * <p><a href="https://mp.weixin.qq.com/s/GQGidprakfticYnbVYVYGQ">参考文章</a></p>
 *
 * @author Fu.zhizhuang
 */
public class FutureUtil {

    /**
     * 设置CF状态为失败
     *
     * @param ex 异常
     * @return CompletableFuture
     */
    public static <T> CompletableFuture<T> failed(Throwable ex) {
        CompletableFuture<T> future = new CompletableFuture<>();
        future.completeExceptionally(ex);
        return future;
    }

    /**
     * 设置CF状态为成功
     *
     * @param result 结果
     * @return CompletableFuture
     */
    public static <T> CompletableFuture<T> success(T result) {
        CompletableFuture<T> future = new CompletableFuture<>();
        future.complete(result);
        return future;
    }

    /**
     * 将List<CompletableFuture>转换成CompletableFuture<List<T>>
     *
     * @param futures CF列表
     * @return CF列表中每个CF的返回值组成的List
     */
    public static <T> CompletableFuture<List<T>> sequence(List<CompletableFuture<T>> futures) {
        CompletableFuture<Void> allDoneFuture = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
        return allDoneFuture.thenApply(v -> futures.stream().map(CompletableFuture::join).collect(Collectors.toList()));
    }

    /**
     * 将List<CompletableFuture<List<T>>>转换成CompletableFuture<List<T>>
     * 多用于分页查询的场景
     *
     * @param futures CF列表
     * @return CF列表中每个CF的返回值组成的List
     */
    public static <T> CompletableFuture<List<T>> sequenceList(List<CompletableFuture<List<T>>> futures) {
        CompletableFuture<Void> allDoneFuture = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
        return allDoneFuture.thenApply(v -> futures.stream().map(CompletableFuture::join)
                .flatMap(List::stream).collect(Collectors.toList()));
    }

    /**
     * 将List<CompletableFuture<Map<K,V>>>转换成CompletableFuture<Map<K,V>>
     *
     * @param futures       CF列表
     * @param mergeFunction 自定义key冲突时的合并策略
     * @param <K>           键
     * @param <V>           值
     * @return CF列表中每个CF的返回值组成的Map
     */
    public static <K, V> CompletableFuture<Map<K, V>> sequenceMap(List<CompletableFuture<Map<K, V>>> futures, BinaryOperator<V> mergeFunction) {
        CompletableFuture<Void> allDoneFuture = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
        return allDoneFuture.thenApply(v -> futures.stream().map(CompletableFuture::join)
                .flatMap(map -> map.entrySet().stream()).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, mergeFunction)));
    }

    /**
     * 将List<CompletableFuture<T>>转换成CompletableFuture<List<T>>
     * 忽略CF返回值为null的CF
     *
     * @param futures CF列表
     * @return CF列表中每个CF的返回值组成的List
     */
    public static <T> CompletableFuture<List<T>> sequenceNonNull(List<CompletableFuture<T>> futures) {
        CompletableFuture<Void> allDoneFuture = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
        return allDoneFuture.thenApply(v -> futures.stream().map(CompletableFuture::join).filter(Objects::nonNull).collect(Collectors.toList()));
    }

    /**
     * 将List<CompletableFuture<List<T>>>转换成CompletableFuture<List<T>>
     * 忽略CF返回值为null的CF
     * 多用于分页查询的场景
     *
     * @param futures CF列表
     * @return CF列表中每个CF的返回值组成的List
     */
    public static <T> CompletableFuture<List<T>> sequenceListNonNull(List<CompletableFuture<List<T>>> futures) {
        CompletableFuture<Void> allDoneFuture = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
        return allDoneFuture.thenApply(v -> futures.stream().map(CompletableFuture::join)
                .flatMap(List::stream).filter(Objects::nonNull).collect(Collectors.toList()));
    }


    /**
     * 将List<CompletableFuture<T>>转换成CompletableFuture<List<T>>
     * 自定义过滤条件
     *
     * @param futures        CF列表
     * @param filterFunction 自定义过滤条件
     * @return CF列表中每个CF的返回值组成的List
     */
    public static <T> CompletableFuture<List<T>> sequence(List<CompletableFuture<T>> futures, Predicate<? super T> filterFunction) {
        CompletableFuture<Void> allDoneFuture = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
        return allDoneFuture.thenApply(v -> futures.stream().map(CompletableFuture::join).filter(filterFunction).collect(Collectors.toList()));
    }

    /**
     * 将List<CompletableFuture<List<T>>>转换成CompletableFuture<List<T>>
     * 自定义过滤条件
     * 多用于分页查询的场景
     *
     * @param futures        CF列表
     * @param filterFunction 自定义过滤条件
     * @return CF列表中每个CF的返回值组成的List
     */
    public static <T> CompletableFuture<List<T>> sequenceList(List<CompletableFuture<List<T>>> futures, Predicate<? super T> filterFunction) {
        CompletableFuture<Void> allDoneFuture = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
        return allDoneFuture.thenApply(v -> futures.stream().map(CompletableFuture::join)
                .flatMap(List::stream).filter(filterFunction).collect(Collectors.toList()));
    }

    /**
     * 将List<CompletableFuture<Map<K,V>>>转换成CompletableFuture<Map<K,V>>
     * 多个map合并为一个map, key冲突时,采用新的value覆盖
     *
     * @param futures CF列表
     * @param <K>     键
     * @param <V>     值
     * @return CF列表中每个CF的返回值组成的Map
     */
    public static <K, V> CompletableFuture<Map<K, V>> sequenceMap(List<CompletableFuture<Map<K, V>>> futures) {
        CompletableFuture<Void> allDoneFuture = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
        return allDoneFuture.thenApply(v -> futures.stream().map(CompletableFuture::join)
                .flatMap(map -> map.entrySet().stream())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (v1, v2) -> v2)));
    }
}
