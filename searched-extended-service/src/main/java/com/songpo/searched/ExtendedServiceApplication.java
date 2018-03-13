package com.songpo.searched;

import com.spring4all.swagger.EnableSwagger2Doc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import tk.mybatis.spring.annotation.MapperScan;

@EnableOAuth2Sso
@EnableSwagger2Doc
@MapperScan(basePackages = "com.songpo.searched.mapper")
@SpringBootApplication
public class ExtendedServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExtendedServiceApplication.class, args);
    }
}
