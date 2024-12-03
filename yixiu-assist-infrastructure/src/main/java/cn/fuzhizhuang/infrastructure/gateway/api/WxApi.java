package cn.fuzhizhuang.infrastructure.gateway.api;

import cn.fuzhizhuang.infrastructure.gateway.dto.WxQrCodeReqDTO;
import cn.fuzhizhuang.infrastructure.gateway.dto.WxQrCodeRespDTO;
import cn.fuzhizhuang.infrastructure.gateway.dto.WxTemplateMsgDTO;
import cn.fuzhizhuang.infrastructure.gateway.dto.WxTokenRespDTO;

import java.io.IOException;
import java.net.URISyntaxException;

/**
 * 微信API
 *
 * @author Fu.zhizhuang
 */
public interface WxApi {

    /**
     * 获取access_token
     * 请求地址：GET <a href="https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET">...</a>
     *
     * @param grantType 授权类型，此处只需填写client_credential
     * @param appId     公众号的唯一标识
     * @param appSecret 公众号的appsecret
     * @return token信息
     */
    WxTokenRespDTO getAccessToken(String grantType, String appId, String appSecret) throws URISyntaxException, IOException, InterruptedException;


    /**
     * 创建二维码
     * 请求地址：POST <a href="https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=TOKEN">...</a>
     *
     * @param accessToken        access_token
     * @param wxQrCodeReqDTO 二维码参数
     * @return 二维码信息
     */
    WxQrCodeRespDTO createQrCode(String accessToken, WxQrCodeReqDTO wxQrCodeReqDTO) throws URISyntaxException, IOException, InterruptedException;


    /**
     * 发送模板消息  POST <a href="https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=ACCESS_TOKEN">...</a>
     *
     * @param accessToken access_token
     * @param wxTemplateMsgDTO 模板消息
     */
    void sendTemplateMessage(String accessToken, WxTemplateMsgDTO wxTemplateMsgDTO) throws IOException, InterruptedException;

}
