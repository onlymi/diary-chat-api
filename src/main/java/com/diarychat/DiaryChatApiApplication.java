package com.diarychat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@ConfigurationPropertiesScan
@SpringBootApplication
public class DiaryChatApiApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(DiaryChatApiApplication.class, args);
    }
    
}
