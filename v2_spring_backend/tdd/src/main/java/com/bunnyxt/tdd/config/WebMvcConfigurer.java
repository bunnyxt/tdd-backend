package com.bunnyxt.tdd.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebMvcConfigurer extends WebMvcConfigurerAdapter {
    private final String[] allowedOrigins;

    public WebMvcConfigurer(@Value("${tdd.cors.allowed-origins:http://localhost:8080,chrome-extension://phdhijbcfjgnhdajagackoddmfegcfeh,http://tdd.bunnyxt.com,https://tdd.bunnyxt.com,http://tdd2.bunnyxt.com,https://tdd2.bunnyxt.com}") String[] allowedOrigins) {
        this.allowedOrigins = allowedOrigins;
    }


    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(allowedOrigins)
                .allowedMethods("GET", "HEAD", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("Content-Type", "Access-Control-Allow-Origin",
                        "Access-Control-Allow-Headers", "Authorization", "X-Requested-With")
                .allowCredentials(true);
    }
}
