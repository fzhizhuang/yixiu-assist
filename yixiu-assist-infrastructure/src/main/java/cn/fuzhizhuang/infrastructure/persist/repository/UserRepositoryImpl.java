package cn.fuzhizhuang.infrastructure.persist.repository;

import cn.fuzhizhuang.domain.user.adapter.repository.UserRepository;
import cn.fuzhizhuang.domain.user.model.aggregate.RegisterAggregate;
import cn.fuzhizhuang.domain.user.model.entity.AccountEntity;
import cn.fuzhizhuang.domain.user.model.entity.UserEntity;
import cn.fuzhizhuang.domain.user.model.valobj.EmailCaptchaVO;
import cn.fuzhizhuang.infrastructure.persist.converter.AccountConverter;
import cn.fuzhizhuang.infrastructure.persist.converter.UserConverter;
import cn.fuzhizhuang.infrastructure.persist.dao.impl.AccountDao;
import cn.fuzhizhuang.infrastructure.persist.dao.impl.UserDao;
import cn.fuzhizhuang.infrastructure.persist.po.AccountPO;
import cn.fuzhizhuang.infrastructure.persist.po.UserPO;
import cn.fuzhizhuang.starter.redisson.distribute.DistributeCache;
import cn.fuzhizhuang.types.constant.CacheKey;
import cn.fuzhizhuang.types.utils.AssertUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.concurrent.TimeUnit;

/**
 * 用户仓储实现
 *
 * @author Fu.zhizhuang
 */
@Slf4j
@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final UserDao userDao;
    private final AccountDao accountDao;
    private final AccountConverter accountConverter;
    private final UserConverter userConverter;
    private final DistributeCache distributeCache;
    private final TransactionTemplate transactionTemplate;

    @Override
    public UserEntity queryUserByEmailOrOpenid(String account) {
        AssertUtil.isNotBlank(account, "邮箱或openid为空");
        UserPO userPO = userDao.queryUserByEmailOrOpenid(account);
        return userConverter.po2UserEntity(userPO);
    }

    @Override
    public void verifyCaptcha(String email, String captcha, EmailCaptchaVO emailCaptchaVO) {
        // 构建缓存key
        String key = CacheKey.buildKey(CacheKey.CAPTCHA_PREFIX, email, emailCaptchaVO.getCode());
        String cacheCaptcha = distributeCache.getValue(key);
        AssertUtil.isNotBlank(cacheCaptcha, "验证码不存在或已过期,请重新发送验证码");
        AssertUtil.isTrue(cacheCaptcha.equals(captcha), "验证码错误,请输入正确的验证码");
        // 删除验证码
        distributeCache.removeValue(key);
    }

    @Override
    public UserEntity register(RegisterAggregate aggregate) {
        AssertUtil.notNull(aggregate, "注册聚合对象不能为空");
        UserEntity userEntity = aggregate.getUserEntity();
        AccountEntity accountEntity = aggregate.getAccountEntity();
        // 转换po
        UserPO userPO = userConverter.userEntity2Po(userEntity);
        AccountPO accountPO = accountConverter.accountEntity2Po(accountEntity);
        // 保存用户
        transactionTemplate.executeWithoutResult(status -> {
            try {
                userDao.save(userPO);
                accountDao.save(accountPO);
            } catch (Exception e) {
                status.setRollbackOnly();
                log.error("注册失败,异常信息:{}", e.getMessage());
            }
        });
        return userEntity;
    }

    @Override
    public String queryOpenidByTicket(String ticket) {
        String key = CacheKey.buildKey(CacheKey.WX_TICKET_PREFIX, ticket);
        return distributeCache.getValue(key);
    }

    @Override
    public void saveQrCodeTicket(String ticket, String openId) {
        String key = CacheKey.buildKey(CacheKey.WX_TICKET_PREFIX, ticket);
        distributeCache.setValue(key, openId, 5, TimeUnit.MINUTES);
    }

    @Override
    public void removeQrCodeTicket(String ticket) {
        String key = CacheKey.buildKey(CacheKey.WX_TICKET_PREFIX, ticket);
        distributeCache.removeValue(key);
    }

}