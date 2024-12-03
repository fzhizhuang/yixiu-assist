package cn.fuzhizhuang.domain.user.adapter.port;

/**
 * 微信adapter
 *
 * @author Fu.zhizhuang
 */
public interface WxPort {

    /**
     * 创建二维码ticket
     *
     * @return ticket
     */
    String createQrCodeTicket();

    /**
     * 发送模板消息
     *
     * @param openid 用户openid
     */
    void sendTemplateMsg(String openid);
}
