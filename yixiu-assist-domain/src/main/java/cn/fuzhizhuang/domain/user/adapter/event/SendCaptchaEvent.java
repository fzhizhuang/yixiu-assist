package cn.fuzhizhuang.domain.user.adapter.event;

import cn.fuzhizhuang.domain.user.model.entity.EmailEntity;
import cn.fuzhizhuang.types.base.BaseEvent;
import cn.fuzhizhuang.types.utils.IdUtil;

/**
 * 发送验证码事件
 *
 * @author Fu.zhizhuang
 */
public class SendCaptchaEvent extends BaseEvent<EmailEntity> {

    public static final String TOPIC = "send_captcha";
    public static final String GROUP = "send_captcha_group";

    public static SendCaptchaEvent build(EmailEntity emailEntity) {
        SendCaptchaEvent event = new SendCaptchaEvent();
        event.setData(emailEntity);
        event.setBiz(IdUtil.getIdStr());
        return event;
    }
}
