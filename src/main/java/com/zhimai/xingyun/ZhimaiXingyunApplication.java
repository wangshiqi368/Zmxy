package com.zhimai.xingyun;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.zhimai.xingyun.mapper")
public class ZhimaiXingyunApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZhimaiXingyunApplication.class, args);
    }

}
