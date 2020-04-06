package com.bunnyxt.tdd.auth;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebMvcConfigurer extends WebMvcConfigurerAdapter {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:8080", // for debug
                        "chrome-extension://phdhijbcfjgnhdajagackoddmfegcfeh", // for chrome extension
                        "http://tdd.bunnyxt.com", "https://tdd.bunnyxt.com", // for production
                        "http://tdd2.bunnyxt.com", "https://tdd2.bunnyxt.com") // for inner test
                .allowedMethods("GET", "HEAD", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("Content-Type", "Access-Control-Allow-Origin",
                        "Access-Control-Allow-Headers", "Authorization", "X-Requested-With")
                .allowCredentials(true);
    }

}
