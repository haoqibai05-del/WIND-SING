package com.windsing;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 风歌 WIND-SING 后端服务启动类
 */
@SpringBootApplication
@MapperScan("com.windsing.mapper")
public class WindSingApplication {

    public static void main(String[] args) {
        SpringApplication.run(WindSingApplication.class, args);
    }
}
