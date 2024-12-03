package cn.fuzhizhuang.trigger.http;

import cn.fuzhizhuang.domain.user.model.entity.WxMsgText;
import cn.fuzhizhuang.domain.user.service.auth.strategy.WxAuthStrategy;
import cn.fuzhizhuang.types.config.WxProperties;
import cn.fuzhizhuang.types.utils.SignatureUtil;
import cn.fuzhizhuang.types.utils.XmlUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * 微信控制器
 *
 * @author Fu.zhizhuang
 */
@Slf4j
@CrossOrigin("*")
@RestController
@RequestMapping("/api/wx/portal")
@RequiredArgsConstructor
@Tag(name = "微信鉴权", description = "微信鉴权")
public class WxPortalController {

    private final WxProperties wxProperties;
    private final WxAuthStrategy wxAuthStrategy;

    @GetMapping(value = "receive", produces = "text/plain;charset=utf-8")
    @Operation(summary = "微信公众号验签")
    public String validate(@RequestParam(value = "signature", required = false) String signature,
                           @RequestParam(value = "timestamp", required = false) String timestamp,
                           @RequestParam(value = "nonce", required = false) String nonce,
                           @RequestParam(value = "echostr", required = false) String echostr) {
        try {
            log.info("微信公众号验签信息开始 [{}, {}, {}, {}]", signature, timestamp, nonce, echostr);
            if (StringUtils.isAnyBlank(signature, timestamp, nonce, echostr)) {
                throw new IllegalArgumentException("请求参数非法，请核实!");
            }
            boolean check = SignatureUtil.checkSignature(wxProperties.getToken(), signature, timestamp, nonce);
            log.info("微信公众号验签信息完成 check：{}", check);
            if (!check) {
                return null;
            }
            return echostr;
        } catch (Exception e) {
            log.error("微信公众号验签信息失败 [{}, {}, {}, {}]", signature, timestamp, nonce, echostr, e);
            return null;
        }
    }

    @PostMapping(value = "receive", produces = "application/xml; charset=UTF-8")
    @Operation(summary = "微信公众号消息推送")
    public String post(@RequestBody String requestBody,
                       @RequestParam("signature") String signature,
                       @RequestParam("timestamp") String timestamp,
                       @RequestParam("nonce") String nonce,
                       @RequestParam("openid") String openid,
                       @RequestParam(name = "encrypt_type", required = false) String encType,
                       @RequestParam(name = "msg_signature", required = false) String msgSignature) {
        try {
            log.info("接收微信公众号信息请求{}开始 {}", openid, requestBody);
            // 消息转换
            WxMsgText message = XmlUtil.xmlToBean(requestBody, WxMsgText.class);
            if ("event".equals(message.getMsgType()) && "SCAN".equals(message.getEvent())) {
                wxAuthStrategy.saveQrCodeTicket(message.getTicket(), openid);
            }
        } catch (Exception e) {
            log.error("接收微信公众号信息请求{}失败 {}", openid, requestBody, e);
        }
        return "";
    }
}