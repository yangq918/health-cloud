package com.haici.health.educomsumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @Auther: QiaoYang
 * @Date: 2020/3/15 19:46
 * @Description:
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.haici.health")
public class EduConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(EduConsumerApplication.class, args);
    }
}
