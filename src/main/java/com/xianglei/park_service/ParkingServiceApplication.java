package com.xianglei.park_service;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;

////////@EnableHystrix //开启 Hystrix 熔断机制的支持
@SpringBootApplication
@EnableEurekaClient
// 开启feign远程调用会扫描标记了指定包下@FeignClient注解的接口，并生成此接口的代理对象
@EnableFeignClients(basePackages= {"com.xianglei.park_service.service"})
@MapperScan("com.xianglei.park_service.mapper")
public class ParkingServiceApplication {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(ParkingServiceApplication.class, args);
    }
}

