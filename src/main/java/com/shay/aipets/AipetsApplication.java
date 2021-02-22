package com.shay.aipets;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


@MapperScan("com.shay.aipets.mapper")
@Configuration
@SpringBootApplication
public class AipetsApplication {


    public static void main(String[] args) {
        SpringApplication.run(AipetsApplication.class, args);

    }

}
