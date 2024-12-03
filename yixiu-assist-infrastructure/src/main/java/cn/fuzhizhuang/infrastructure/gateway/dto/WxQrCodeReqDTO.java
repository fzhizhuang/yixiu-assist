package cn.fuzhizhuang.infrastructure.gateway.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

/**
 * 微信验证码请求DTO
 *
 * @author Fu.zhizhuang
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WxQrCodeReqDTO {

    @JsonProperty("expire_seconds")
    @Builder.Default
    private int expireSeconds = 2592000;
    @JsonProperty("action_name")
    private String actionName;
    @JsonProperty("action_info")
    private ActionInfo actionInfo;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ActionInfo {

        @JsonProperty("scene")
        Scene scene;

        @Data
        @Builder
        @AllArgsConstructor
        @NoArgsConstructor
        public static class Scene {
            @JsonProperty("scene_id")
            Integer sceneId;
            @JsonProperty("scene_str")
            String sceneStr;
        }
    }

    @Getter
    @AllArgsConstructor
    public enum ActionNameTypeVO {
        QR_SCENE("QR_SCENE", "临时的整型参数值"),
        QR_STR_SCENE("QR_STR_SCENE", "临时的字符串参数值"),
        QR_LIMIT_SCENE("QR_LIMIT_SCENE", "永久的整型参数值"),
        QR_LIMIT_STR_SCENE("QR_LIMIT_STR_SCENE", "永久的字符串参数值");

        private final String code;
        private final String info;
    }
}
