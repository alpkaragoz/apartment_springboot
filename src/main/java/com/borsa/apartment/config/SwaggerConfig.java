package com.borsa.apartment.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "HomeHunt API Documentation",
                version = "1.0.0",
                description = "This is the API documentation for the HomeHunt system.",
                contact = @Contact(
                        name = "Alp Karagöz",
                        email = "alpkaragoz3@gmail.com"
                )
        )
)
public class SwaggerConfig {
}