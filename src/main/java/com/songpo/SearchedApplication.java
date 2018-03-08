package com.songpo;

import com.spring4all.swagger.EnableSwagger2Doc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

//import org.mybatis.spring.annotation.MapperScan;

@EnableSwagger2Doc
@MapperScan(basePackages = "com.songpo.mapper")
@SpringBootApplication
public class SearchedApplication {

    public static void main(String[] args) {
        SpringApplication.run(SearchedApplication.class, args);
    }
}
