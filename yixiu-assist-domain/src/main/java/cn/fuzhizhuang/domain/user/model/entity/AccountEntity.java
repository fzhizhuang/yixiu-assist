package cn.fuzhizhuang.domain.user.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * 账号实体
 *
 * @author Fu.zhizhuang
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountEntity {

    private String userId;
    private BigDecimal totalBalance;
    private BigDecimal usedBalance;
    private BigDecimal availableBalance;

    /**
     * 注册账号
     *
     * @param userId 用户id
     */
    public void register(String userId) {
        this.userId = userId;
        this.totalBalance = BigDecimal.valueOf(100);
        this.usedBalance = BigDecimal.valueOf(0);
        this.availableBalance = BigDecimal.valueOf(100);
    }
}
