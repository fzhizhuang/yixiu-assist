package cn.fuzhizhuang.cases.service.impl;

import cn.fuzhizhuang.cases.dto.UserInfoDTO;
import cn.fuzhizhuang.cases.service.UserService;
import cn.fuzhizhuang.domain.user.model.entity.AccountEntity;
import cn.fuzhizhuang.domain.user.model.entity.UserEntity;
import cn.fuzhizhuang.domain.user.service.user.UserDomainService;
import cn.fuzhizhuang.types.utils.AssertUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

/**
 * 用户服务实现
 *
 * @author Fu.zhizhuang
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserDomainService userDomainService;

    @Override
    public UserInfoDTO getUserInfo(String userId) {
        // 1. 查询用户信息
        CompletableFuture<UserEntity> userFuture = CompletableFuture.supplyAsync(() -> userDomainService.queryUserByUserId(userId));
        // 2. 查询额度信息
        CompletableFuture<AccountEntity> accountFuture = CompletableFuture.supplyAsync(() -> userDomainService.queryAccountByUserId(userId));
        // 3. 合并结果
        return CompletableFuture.allOf(userFuture, accountFuture).thenApply(v -> {
            UserEntity userEntity = userFuture.join();
            AccountEntity accountEntity = accountFuture.join();
            UserInfoDTO userInfoDTO = new UserInfoDTO();
            // 用户信息
            userInfoDTO.setUserId(userEntity.getUserId());
            userInfoDTO.setUsername(userEntity.getUsername());
            userInfoDTO.setAvatar(userEntity.getAvatar());
            userInfoDTO.setEmail(userEntity.getEmail());
            userInfoDTO.setOpenid(userEntity.getOpenid());
            // 额度信息
            userInfoDTO.setTotal(accountEntity.getTotalBalance());
            userInfoDTO.setUsed(accountEntity.getUsedBalance());
            userInfoDTO.setSurplus(accountEntity.getAvailableBalance());
            return userInfoDTO;
        }).join();
    }

    @Override
    public void modifyUsername(String uid, String username) {
        // 参数校验
        AssertUtil.isNotBlank(username, "用户昵称为空");
        AssertUtil.isNotBlank(uid, "用户ID为空");
        // 查询用户昵称是否和原昵称一致
        UserEntity userEntity = userDomainService.queryUserByUserId(uid);
        String oldUsername = userEntity.getUsername();
        if (oldUsername.equals(username)) return;
        // 更新用户昵称
        userDomainService.modifyUsername(uid, username);
        // 更新用户缓存信息
        userDomainService.updateUserCache(uid);
    }
}
