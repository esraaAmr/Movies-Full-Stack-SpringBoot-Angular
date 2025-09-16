package com.example.movie.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Movie Management API")
                        .version("1.0.0")
                        .description("API for Movie Management System with OMDB Integration")
                        .contact(new Contact()
                                .name("Movie API Team")
                                .email("esraaamrkandil@gmail.com")));
    }
}
