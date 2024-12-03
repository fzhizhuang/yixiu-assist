package cn.fuzhizhuang.infrastructure.persist.converter;

import cn.fuzhizhuang.domain.user.model.entity.UserEntity;
import cn.fuzhizhuang.domain.user.model.valobj.UserRoleVO;
import cn.fuzhizhuang.domain.user.model.valobj.UserStatusVO;
import cn.fuzhizhuang.infrastructure.persist.po.UserPO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

import java.util.Objects;

/**
 * 用户Converter
 *
 * @author Fu.zhizhuang
 */
@Mapper(componentModel = "spring")
public interface UserConverter {


    @Mappings({
            @Mapping(source = "userId", target = "userId"),
            @Mapping(source = "username", target = "username"),
            @Mapping(source = "password", target = "password"),
            @Mapping(source = "email", target = "email"),
            @Mapping(source = "openid", target = "openid"),
            @Mapping(source = "avatar", target = "avatar"),
            @Mapping(source = "role", target = "userRole", qualifiedByName = "getUserRoleVO"),
            @Mapping(source = "status", target = "userStatus", qualifiedByName = "getUserStatusVO")
    })
    UserEntity po2UserEntity(UserPO userPO);

    @Named("getUserRoleVO")
    default UserRoleVO getUserRoleVO(Integer code) {
        return UserRoleVO.getUserRole(code);
    }

    @Named("getUserStatusVO")
    default UserStatusVO getUserStatusVO(Integer code) {
        return UserStatusVO.getUserStatus(code);
    }


    @Mappings({
            @Mapping(source = "userId", target = "userId"),
            @Mapping(source = "username", target = "username"),
            @Mapping(source = "password", target = "password"),
            @Mapping(source = "email", target = "email"),
            @Mapping(source = "openid", target = "openid"),
            @Mapping(source = "avatar", target = "avatar"),
            @Mapping(source = "userRole", target = "role", qualifiedByName = "getRole"),
            @Mapping(source = "userStatus", target = "status", qualifiedByName = "getStatus")
    })
    UserPO userEntity2Po(UserEntity userEntity);

    @Named("getRole")
    default Integer getRole(UserRoleVO userRoleVO) {
        if (Objects.isNull(userRoleVO)) return null;
        return userRoleVO.getCode();
    }

    @Named("getStatus")
    default Integer getStatus(UserStatusVO userStatusVO) {
        if (Objects.isNull(userStatusVO)) return null;
        return userStatusVO.getCode();
    }
}
