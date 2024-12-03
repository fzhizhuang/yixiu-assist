package cn.fuzhizhuang.infrastructure.gateway.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * 微信AccessToken响应DTO
 *
 * @author Fu.zhizhuang
 */
@Data
public class WxTokenRespDTO {


    /**
     * 获取到的凭证
     */
    @JsonProperty("access_token")
    private String accessToken;
    /**
     * 凭证有效时间，单位：秒
     */
    @JsonProperty("expires_in")
    private Integer expiresIn;
    /**
     * 错误码
     */
    @JsonProperty("errcode")
    private String errCode;
    /**
     * 错误信息
     */
    @JsonProperty("errmsg")
    private String errMsg;
}
