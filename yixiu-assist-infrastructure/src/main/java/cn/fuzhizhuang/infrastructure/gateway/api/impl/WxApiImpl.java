package cn.fuzhizhuang.infrastructure.gateway.api.impl;

import cn.fuzhizhuang.infrastructure.gateway.api.WxApi;
import cn.fuzhizhuang.infrastructure.gateway.dto.WxQrCodeReqDTO;
import cn.fuzhizhuang.infrastructure.gateway.dto.WxQrCodeRespDTO;
import cn.fuzhizhuang.infrastructure.gateway.dto.WxTemplateMsgDTO;
import cn.fuzhizhuang.infrastructure.gateway.dto.WxTokenRespDTO;
import com.alibaba.fastjson2.JSON;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * 微信API实现
 *
 * @author Fu.zhizhuang
 */
@Service
public class WxApiImpl implements WxApi {

    @Override
    public WxTokenRespDTO getAccessToken(String grantType, String appId, String appSecret) throws URISyntaxException, IOException, InterruptedException {
        String url = String.format("https://api.weixin.qq.com/cgi-bin/token?grant_type=%s&appid=%s&secret=%s", grantType, appId, appSecret);
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(url))
                .GET()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() == 200) {
            return JSON.parseObject(response.body(), WxTokenRespDTO.class);
        } else {
            throw new RuntimeException("Failed to get access token: " + response.body());
        }
    }

    @Override
    public WxQrCodeRespDTO createQrCode(String accessToken, WxQrCodeReqDTO wxQrCodeReqDTO) throws URISyntaxException, IOException, InterruptedException {
        String url = String.format("https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=%s", accessToken);
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(url))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(JSON.toJSONString(wxQrCodeReqDTO)))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() == 200) {
            return JSON.parseObject(response.body(), WxQrCodeRespDTO.class);
        } else {
            throw new RuntimeException("Failed to create qr code: " + response.body());
        }
    }

    @Override
    public void sendTemplateMessage(String accessToken, WxTemplateMsgDTO wxTemplateMsgDTO) throws IOException, InterruptedException {
        String url = String.format("https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=%s", accessToken);
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(JSON.toJSONString(wxTemplateMsgDTO)))
                .build();
        client.send(request, HttpResponse.BodyHandlers.ofString());
    }
}
