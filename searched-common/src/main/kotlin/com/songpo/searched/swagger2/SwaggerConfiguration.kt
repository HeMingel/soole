package com.songpo.searched.swagger2

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.builders.OAuthBuilder
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.*
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2
import java.util.*

/**
 * @author 刘松坡
 */
@Configuration
@EnableSwagger2
class SwaggerConfiguration {

    @Bean
    fun baseApi(): Docket {
        return Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .groupName("Base_API")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.songpo.searched"))
                .paths(PathSelectors.ant("/api/base/**"))
                .build()
    }

    @Bean
    fun commonApi(): Docket {
        return Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .groupName("Common_API")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.songpo.searched"))
                .paths(PathSelectors.ant("/api/common/**"))
                .build()
                .securitySchemes(listOf(oauth2()))
                .protocols(object : HashSet<String>(2) {
                    init {
                        add("http")
                        add("https")
                    }
                })
    }

    fun oauth2(): SecurityScheme {
        return OAuthBuilder()
                .name("oauth2")
                .grantTypes(listOf<GrantType>(ClientCredentialsGrant("http://api1.xn--ykq093c.com/sl/oauth/token")))
                .scopes(listOf(
                        AuthorizationScope("read", ""),
                        AuthorizationScope("write", "")
                ))
                .build()
    }

    private fun apiInfo(): ApiInfo {
        return ApiInfoBuilder()
                .title("搜了平台 RESTful APIs")
                .description("搜了平台 RESTful APIs")
                .termsOfServiceUrl("https://www.xzcysoft.com/")
                .contact(Contact("刘松坡", "https://confluence.xzcysoft.com/pages/viewpage.action?pageId=11927565", "liusongop@gmail.co"))
                .version("1.0")
                .build()
    }
}
