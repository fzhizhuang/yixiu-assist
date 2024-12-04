package cn.fuzhizhuang.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * knife4j配置
 *
 * @author Fu.zhizhuang
 */
@Configuration
public class Knife4jConfiguration {

    // 配置knife4j文档
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI().info(new Info().title("接口文档").description("一休助手接口文档")
                .version("1.0").contact(new Contact().name("Fu.zhizhuang").email("jason_zong@hotmail.com"))
                .license(new License().name("Apache 2.0").url("http://springdoc.org")));
    }
}
