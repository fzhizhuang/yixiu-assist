package cn.fuzhizhuang.domain.user.service.user.impl;

import cn.fuzhizhuang.domain.user.adapter.repository.UserRepository;
import cn.fuzhizhuang.domain.user.model.entity.AccountEntity;
import cn.fuzhizhuang.domain.user.model.entity.UserEntity;
import cn.fuzhizhuang.domain.user.model.valobj.EmailCaptchaVO;
import cn.fuzhizhuang.domain.user.service.code.CodeService;
import cn.fuzhizhuang.domain.user.service.user.UserDomainService;
import cn.fuzhizhuang.starter.redisson.annotation.MultiCache;
import cn.fuzhizhuang.starter.redisson.annotation.MultiCachePut;
import cn.fuzhizhuang.types.utils.AssertUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 用户服务实现
 *
 * @author Fu.zhizhuang
 */
@Service
@RequiredArgsConstructor
public class UserDomainServiceImpl implements UserDomainService {

    private final UserRepository userRepository;
    private final CodeService codeService;

    @Override
    @MultiCache(cacheName = "userCache", prefix = "userInfo:", keys = {"#userId"})
    public UserEntity queryUserByUserId(String userId) {
        return userRepository.queryUserByUserId(userId);
    }

    @Override
    public AccountEntity queryAccountByUserId(String userId) {
        return userRepository.queryAccountByUserId(userId);
    }

    @Override
    public void modifyUsername(String uid, String username) {
        UserEntity userEntity = new UserEntity();
        userEntity.modifyUsername(uid, username);
        userRepository.modifyUsername(userEntity);
    }

    @Override
    @MultiCachePut(cacheName = "userCache", prefix = "userInfo:", keys = {"#uid"})
    public UserEntity updateUserCache(String uid) {
        return userRepository.queryUserByUserId(uid);
    }

    @Override
    public void modifyAvatar(String uid, String avatarUrl) {
        UserEntity userEntity = new UserEntity();
        userEntity.modifyAvatar(uid, avatarUrl);
        userRepository.modifyAvatar(userEntity);
    }

    @Override
    public void modifyPassword(String uid, String password) {
        UserEntity userEntity = new UserEntity();
        userEntity.modifyPassword(uid, password);
        userRepository.modifyPassword(userEntity);
    }

    @Override
    public void modifyEmail(String uid, String email, String captcha) {
        // 校验验证码
        codeService.verifyCaptcha(email, captcha, EmailCaptchaVO.MODIFY_EMAIL);
        // 更新用户邮箱
        UserEntity userEntity = new UserEntity();
        userEntity.modifyEmail(uid, email);
        userRepository.modifyEmail(userEntity);
    }

    @Override
    public UserEntity queryUserByEmail(String email) {
        AssertUtil.isNotBlank(email, "邮箱为空");
        return userRepository.queryUserByEmailOrOpenid(email);
    }
}
