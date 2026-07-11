package com.demo.springbootdistributedsystemdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class SpringbootDistributedSystemDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootDistributedSystemDemoApplication.class, args);
    }

}
