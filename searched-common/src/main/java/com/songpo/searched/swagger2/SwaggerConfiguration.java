package com.songpo.searched.swagger2;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.OAuthBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

/**
 * @author 刘松坡
 */
@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

    @Bean
    public Docket baseApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .groupName("Base_API")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.songpo.searched"))
                .paths(PathSelectors.ant("/api/base/**"))
                .build();
    }

    @Bean
    public Docket commonApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .groupName("Common_API")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.songpo.searched"))
                .paths(PathSelectors.ant("/api/common/**"))
                .build()
                .securitySchemes(Collections.singletonList(oauth2()))
                .protocols(new HashSet<>(2) {{
                    add("http");
                    add("https");
                }});
    }

    public SecurityScheme oauth2() {
        return new OAuthBuilder()
                .name("oauth2")
//                .grantTypes(List.of(new ClientCredentialsGrant("http://api1.xn--ykq093c.com/sl/oauth/token")))
                .grantTypes(Collections.singletonList(new ClientCredentialsGrant("http://localhost:8080/sl/oauth/token")))
                .scopes(Arrays.asList(
                        new AuthorizationScope("read", ""),
                        new AuthorizationScope("write", "")
                ))
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("搜了平台 RESTful APIs")
                .description("搜了平台 RESTful APIs")
                .termsOfServiceUrl("https://www.xzcysoft.com/")
                .contact(new Contact("刘松坡", "https://confluence.xzcysoft.com/pages/viewpage.action?pageId=11927565", "liusongop@gmail.co"))
                .version("1.0")
                .build();
    }
}
