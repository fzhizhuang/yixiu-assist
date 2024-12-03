package cn.fuzhizhuang.infrastructure.persist.converter;

import cn.fuzhizhuang.domain.user.model.entity.AccountEntity;
import cn.fuzhizhuang.infrastructure.persist.po.AccountPO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

/**
 * 账户转换器
 *
 * @author Fu.zhizhuang
 */
@Mapper(componentModel = "spring")
public interface AccountConverter {

    @Mappings({
            @Mapping(source = "userId", target = "userId"),
            @Mapping(source = "totalBalance", target = "totalBalance"),
            @Mapping(source = "usedBalance", target = "usedBalance"),
            @Mapping(source = "availableBalance", target = "availableBalance")
    })
    AccountPO accountEntity2Po(AccountEntity accountEntity);

    @Mappings({
            @Mapping(source = "userId", target = "userId"),
            @Mapping(source = "totalBalance", target = "totalBalance"),
            @Mapping(source = "usedBalance", target = "usedBalance"),
            @Mapping(source = "availableBalance", target = "availableBalance")
    })
    AccountEntity po2AccountEntity(AccountPO accountPO);
}
