package com.liu.org;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.liu.org.mapper")
public class FlightFirstApplication {

    public static void main(String[] args) {
        SpringApplication.run(FlightFirstApplication.class, args);
    }

}
