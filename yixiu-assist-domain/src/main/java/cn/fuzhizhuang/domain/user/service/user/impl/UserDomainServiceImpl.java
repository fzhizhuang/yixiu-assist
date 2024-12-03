package cn.fuzhizhuang.domain.user.service.user.impl;

import cn.fuzhizhuang.domain.user.adapter.repository.UserRepository;
import cn.fuzhizhuang.domain.user.model.entity.AccountEntity;
import cn.fuzhizhuang.domain.user.model.entity.UserEntity;
import cn.fuzhizhuang.domain.user.service.user.UserDomainService;
import cn.fuzhizhuang.starter.redisson.annotation.MultiCache;
import cn.fuzhizhuang.starter.redisson.annotation.MultiCachePut;
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

    @Override
    @MultiCache(cacheName = "userCache", prefix = "userInfo", keys = {"#userId"})
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
    @MultiCachePut(cacheName = "userCache", prefix = "userInfo", keys = {"#uid"})
    public UserEntity updateUserCache(String uid) {
        return userRepository.queryUserByUserId(uid);
    }
}
