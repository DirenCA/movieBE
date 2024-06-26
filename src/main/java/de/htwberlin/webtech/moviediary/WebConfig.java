package de.htwberlin.webtech.moviediary;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("https://moviefrontend-lxaz.onrender.com" , "https://moviebe-5i9h.onrender.com", "http://localhost:3003")
                .allowedMethods("*")
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}
