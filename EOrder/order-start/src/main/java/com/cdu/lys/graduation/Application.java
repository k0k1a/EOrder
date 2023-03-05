package com.cdu.lys.graduation;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author liyinsong
 * @date 2022/1/3 14:09
 */
@SpringBootApplication
@ComponentScan(basePackages = "com.cdu.lys")
@MapperScan("com.cdu.lys.graduation.repository.dao")
@EnableScheduling
@EnableCaching
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
