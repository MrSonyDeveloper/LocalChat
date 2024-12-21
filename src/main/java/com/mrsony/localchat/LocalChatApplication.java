package com.mrsony.localchat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class LocalChatApplication {

    public static void main(String[] args) {
        SpringApplication.run(LocalChatApplication.class, args);
    }

}
