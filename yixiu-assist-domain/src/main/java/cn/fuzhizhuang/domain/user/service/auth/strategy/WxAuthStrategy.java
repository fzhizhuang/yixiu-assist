package cn.fuzhizhuang.domain.user.service.auth.strategy;

import cn.fuzhizhuang.domain.user.adapter.port.WxPort;
import cn.fuzhizhuang.domain.user.model.aggregate.RegisterAggregate;
import cn.fuzhizhuang.domain.user.model.entity.AccountEntity;
import cn.fuzhizhuang.domain.user.model.entity.AuthEntity;
import cn.fuzhizhuang.domain.user.model.entity.UserEntity;
import cn.fuzhizhuang.domain.user.model.valobj.AuthTypeVO;
import cn.fuzhizhuang.domain.user.service.auth.WxDomainService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * 微信认证策略
 *
 * @author Fu.zhizhuang
 */
@Service
public class WxAuthStrategy extends AbstractAuthStrategy implements WxDomainService {

    @Resource
    private WxPort wxPort;

    @Override
    protected UserEntity register(AuthEntity authEntity) {
        String account = authEntity.getAccount();
        // 构建注册聚合对象
        UserEntity userEntity = new UserEntity();
        userEntity.register(null, account);
        AccountEntity accountEntity = new AccountEntity();
        accountEntity.register(userEntity.getUserId());
        RegisterAggregate aggregate = RegisterAggregate.builder().userEntity(userEntity).accountEntity(accountEntity).build();
        return userRepository.register(aggregate);
    }

    @Override
    protected UserEntity login(UserEntity userEntity, AuthEntity authEntity) {
        return userEntity;
    }

    @Override
    public AuthTypeVO getAuthType() {
        return AuthTypeVO.WECHAT;
    }

    @Override
    public String createQrCodeTicket() {
        return wxPort.createQrCodeTicket();
    }

    @Override
    public String checkQrCodeTicket(String ticket) {
        return userRepository.queryOpenidByTicket(ticket);
    }

    @Override
    public void saveQrCodeTicket(String ticket, String openId) {
        // 保存登录消息
        userRepository.saveQrCodeTicket(ticket, openId);
        // 发送模板消息
        wxPort.sendTemplateMsg(openId);
    }

    @Override
    public void removeQrCodeTicket(String ticket) {
        userRepository.removeQrCodeTicket(ticket);
    }

    @Override
    public String showQrCode(String ticket) {
        return "https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=" + ticket;
    }
}
