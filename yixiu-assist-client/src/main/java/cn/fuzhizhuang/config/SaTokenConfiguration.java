package cn.fuzhizhuang.config;

import cn.dev33.satoken.jwt.StpLogicJwtForSimple;
import cn.dev33.satoken.stp.StpLogic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * saToken配置
 *
 * @author Fu.zhizhuang
 */
@Configuration
public class SaTokenConfiguration {


    /**
     * jwt token 模式
     *
     * @return StpLogic
     */
    @Bean
    public StpLogic getStpLogicJwt() {
        return new StpLogicJwtForSimple();
    }
}
