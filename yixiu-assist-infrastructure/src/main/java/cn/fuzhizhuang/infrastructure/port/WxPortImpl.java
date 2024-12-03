package cn.fuzhizhuang.infrastructure.port;

import cn.fuzhizhuang.domain.user.adapter.port.WxPort;
import cn.fuzhizhuang.infrastructure.gateway.api.WxApi;
import cn.fuzhizhuang.infrastructure.gateway.dto.WxQrCodeReqDTO;
import cn.fuzhizhuang.infrastructure.gateway.dto.WxQrCodeRespDTO;
import cn.fuzhizhuang.infrastructure.gateway.dto.WxTemplateMsgDTO;
import cn.fuzhizhuang.infrastructure.gateway.dto.WxTokenRespDTO;
import cn.fuzhizhuang.starter.redisson.distribute.DistributeCache;
import cn.fuzhizhuang.types.config.WxProperties;
import cn.fuzhizhuang.types.constant.CacheKey;
import cn.fuzhizhuang.types.utils.AssertUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 微信Port实现
 *
 * @author Fu.zhizhuang
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class WxPortImpl implements WxPort {

    private final WxProperties properties;
    private final WxApi wxApi;
    private final DistributeCache distributeCache;

    private static final String CLIENT_CREDENTIAL = "client_credential";

    @Override
    public String createQrCodeTicket() {
        // 生成accessToken
        String accessToken = getAccessToken();
        // 生成ticket
        WxQrCodeReqDTO wxQrCodeRequestDTO = WxQrCodeReqDTO.builder()
                .actionName(WxQrCodeReqDTO.ActionNameTypeVO.QR_SCENE.getCode())
                .actionInfo(WxQrCodeReqDTO.ActionInfo.builder()
                        .scene(WxQrCodeReqDTO.ActionInfo.Scene.builder()
                                .sceneId(2024).build())
                        .build())
                .build();
        WxQrCodeRespDTO qrCode = null;
        try {
            qrCode = wxApi.createQrCode(accessToken, wxQrCodeRequestDTO);
        } catch (Exception e) {
            log.error("获取二维码失败", e);
        }
        AssertUtil.notNull(qrCode, "获取二维码失败");
        return Objects.requireNonNull(qrCode).getTicket();
    }

    @Override
    public void sendTemplateMsg(String openid) {
        // 生成accessToken
        String accessToken = getAccessToken();
        // 发送消息模板
        Map<String, Map<String, String>> data = new HashMap<>();
        WxTemplateMsgDTO.put(data, WxTemplateMsgDTO.TemplateKey.OPENID, openid);
        WxTemplateMsgDTO.put(data, WxTemplateMsgDTO.TemplateKey.TIME, getLocalDateTime());
        WxTemplateMsgDTO wxTemplateMessageDTO = WxTemplateMsgDTO.builder().toUser(openid).url(properties.getTemplateUrl()).templateId(properties.getTemplateId()).data(data).build();
        try {
            wxApi.sendTemplateMessage(accessToken, wxTemplateMessageDTO);
        } catch (Exception e) {
            log.error("发送模板消息失败", e);
        }
    }

    /**
     * 获取accessToken
     *
     * @return accessToken
     */
    private String getAccessToken() {
        String key = CacheKey.buildKey(CacheKey.WX_TOKEN_PREFIX, properties.getAppId());
        String accessToken = distributeCache.getValue(key);
        if (Objects.isNull(accessToken)) {
            WxTokenRespDTO tokenRespDTO = null;
            try {
                tokenRespDTO = wxApi.getAccessToken(CLIENT_CREDENTIAL, properties.getAppId(), properties.getAppSecret());
            } catch (Exception e) {
                log.error("获取accessToken失败", e);
            }
            if (Objects.nonNull(tokenRespDTO)) {
                accessToken = tokenRespDTO.getAccessToken();
                // 缓存accessToken
                distributeCache.setValue(key, accessToken);
            }
        }
        return accessToken;
    }

    /**
     * 获取当前时间
     *
     * @return 当前时间
     */
    private String getLocalDateTime() {
        // 获取当前时间
        LocalDateTime now = LocalDateTime.now();
        // 定义日期时间格式
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        // 格式化当前时间为字符串
        return now.format(formatter);
    }
}
