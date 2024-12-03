package cn.fuzhizhuang.types.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 微信Properties
 *
 * @author Fu.zhizhuang
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "wx")
public class WxProperties {

    /**
     * 公众号原始ID
     */
    private String originalId;

    /**
     * 公众号token
     */
    private String token;

    /**
     * 公众号appId
     */
    private String appId;

    /**
     * 公众号appSecret
     */
    private String appSecret;

    /**
     * 模板消息id
     */
    private String templateId;

    /**
     * 模板消息url
     */
    private String templateUrl;
}
