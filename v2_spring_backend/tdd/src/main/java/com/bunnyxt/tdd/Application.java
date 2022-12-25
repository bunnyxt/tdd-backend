package com.bunnyxt.tdd;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@MapperScan("com.bunnyxt.tdd.dao")
@PropertySource("/application-local.properties")
public class Application {

    public static void main(String[] args) {

        SpringApplication.run(Application.class,args);

    }
}
