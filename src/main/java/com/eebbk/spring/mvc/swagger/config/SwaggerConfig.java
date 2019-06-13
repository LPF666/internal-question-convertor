package com.eebbk.spring.mvc.swagger.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.async.DeferredResult;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import com.eebbk.edu.common.mvc.config.BaseProperty;
import com.google.common.base.Predicates;

/**
 * @项目名称：spring-boot
 * @类名称：SwaggerConfig
 * @类描述：api接口文档配置 访问路径 http://yourdomain/swagger-ui.html
 *                eg.http://127.0.0.1:6001/swagger-ui.html
 * @创建人：杨一中
 * @创建时间：2017年2月3日 下午7:57:55
 * @company:步步高教育电子有限公司
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig
{
    /**
     * 可以定义多个组 （访问页面就可以看到效果了）
     */
    @Bean
    public Docket appApi()
    {
        return createDocket("app系列接口", "app");
    }

    @Bean
    public Docket serviceApi()
    {
        return createDocket("第三方服务系列接口", "service");
    }

    @Bean
    public Docket adminApi()
    {
        return createDocket("后台系列接口", "admin");
    }

    @Bean
    public Docket api()
    {
        return createDocket("api接口", "api");
    }

    @SuppressWarnings("unchecked")
    private Docket createDocket(String title, String path)
    {

        return new Docket(DocumentationType.SWAGGER_2)
                .groupName(title)
                .genericModelSubstitutes(DeferredResult.class)
                .useDefaultResponseMessages(false)
                .forCodeGeneration(false)
                .pathMapping("/")
                .select()
                // 需要过滤的接口
                .paths(Predicates.or(PathSelectors.regex("/" + path + "/.*")))
                .build()
                .apiInfo(
                        new ApiInfoBuilder().title(title).version("1.0").contact("eebbk").license("步步高教育电子有限公司")
                                .build())
                // 如果是生产环境，不能使用swagger
                .enable(!isRealEnv());
    }

    private boolean isRealEnv()
    {
        return BaseProperty.ENV_REAL.equals(System.getProperty(BaseProperty.ENV_ACTIVE_NAME));
    }
}
