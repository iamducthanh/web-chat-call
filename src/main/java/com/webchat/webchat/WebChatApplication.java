package com.webchat.webchat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class WebChatApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(WebChatApplication.class, args);
    }

}
