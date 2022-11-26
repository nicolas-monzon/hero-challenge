package com.core.hero;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class App {
    private static final String SERVER_UP = "App started";

    public static void main(String[] args) {
        log.warn(SERVER_UP);
        SpringApplication.run(App.class, args);
    }
}