package com.backend;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * API底座项目启动类
 * 
 * @author backend
 * @since 1.0.0
 */
@SpringBootApplication
@MapperScan("com.backend.mapper")
public class ApiFoundationApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiFoundationApplication.class, args);
        System.out.println("=================================");
        System.out.println("API底座项目启动成功！");
        System.out.println("接口文档地址: http://localhost:8080/api/swagger-ui/");
        System.out.println("数据库监控: http://localhost:8080/api/druid/");
        System.out.println("=================================");
    }
}