package cn.fuzhizhuang.domain.user.service.auth;

/**
 * 微信服务
 *
 * @author Fu.zhizhuang
 */
public interface WxDomainService {

    /**
     * 创建微信验证码ticket
     *
     * @return ticket
     */
    String createQrCodeTicket();

    /**
     * 校验微信验证码ticket
     *
     * @param ticket ticket
     * @return openid
     */
    String checkQrCodeTicket(String ticket);

    /**
     * 保存微信验证码ticket和openId
     *
     * @param ticket ticket
     * @param openId 微信openId
     */
    void saveQrCodeTicket(String ticket, String openId);

    /**
     * 移除微信验证码ticket
     *
     * @param ticket ticket
     */
    void removeQrCodeTicket(String ticket);

    /**
     * 展示微信二维码
     *
     * @param ticket ticket
     * @return 二维码地址
     */
    String showQrCode(String ticket);
}
