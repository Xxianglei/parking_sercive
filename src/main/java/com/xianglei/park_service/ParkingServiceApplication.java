package com.xianglei.park_service;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
@MapperScan("com.xianglei.park_service.mapper")
public class ParkingServiceApplication {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(ParkingServiceApplication.class, args);
    }
}

