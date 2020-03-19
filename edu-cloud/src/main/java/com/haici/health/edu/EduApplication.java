package com.haici.health.edu;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @Auther: QiaoYang
 * @Date: 2020/1/21 16:50
 * @Description:
 */
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.haici.health.edu.mapper")
public class EduApplication {
    public static void main(String[] args) {
        SpringApplication.run(EduApplication.class, args);
    }
}
