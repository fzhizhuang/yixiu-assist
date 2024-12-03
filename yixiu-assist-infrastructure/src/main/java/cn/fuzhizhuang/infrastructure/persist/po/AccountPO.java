package cn.fuzhizhuang.infrastructure.persist.po;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 账户PO
 *
 * @author Fu.zhizhuang
 */
@Data
@TableName("t_account")
public class AccountPO {

    // 自增ID
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    // 用户ID
    @TableField(value = "user_id")
    private String userId;
    // 总额度
    @TableField(value = "total_balance")
    private BigDecimal totalBalance;
    // 已使用额度
    @TableField(value = "used_balance")
    private BigDecimal usedBalance;
    // 可用额度
    @TableField(value = "available_balance")
    private BigDecimal availableBalance;
    // 创建时间
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;
    // 更新时间
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
}
