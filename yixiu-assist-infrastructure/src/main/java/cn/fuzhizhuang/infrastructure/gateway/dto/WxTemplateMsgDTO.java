package cn.fuzhizhuang.infrastructure.gateway.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 微信模板消息DTO
 *
 * @author Fu.zhizhuang
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WxTemplateMsgDTO {

    /**
     * 接收者openid
     */
    @JsonProperty("touser")
    private String toUser;
    /**
     * 模板id
     */
    @JsonProperty("template_id")
    private String templateId;
    /**
     * 模板跳转链接（海外账号没有跳转能力）
     */
    private String url;
    /**
     * 模板数据
     */
    private Map<String, Map<String, String>> data = new ConcurrentHashMap<>();


    /**
     * 添加模板数据
     *
     * @param data  map数据
     * @param key   键
     * @param value 值
     */
    public static void put(Map<String, Map<String, String>> data, TemplateKey key, String value) {
        data.put(key.getKey(), new HashMap<>() {
            {
                put("value", value);
            }
        });
    }

    @Getter
    @AllArgsConstructor
    public enum TemplateKey {
        OPENID("openid"),
        TIME("time");

        private final String key;
    }
}
