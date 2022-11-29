package com.core.hero;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@OpenAPIDefinition(info = @Info(title = "Heroes API", version = "1.0", description = "Some super heroes"))
@SpringBootApplication
public class App {
    private static final String SERVER_UP = "App started";

    public static void main(String[] args) {
        log.warn(SERVER_UP);
        SpringApplication.run(App.class, args);
    }
}