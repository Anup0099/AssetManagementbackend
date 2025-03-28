package com.hexaware.assetmanagement.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOrigin("http://localhost:3000"); // Allow frontend origin
        config.addAllowedHeader("*"); // Allow all headers
        config.addAllowedMethod("*"); // Allow all HTTP methods (POST, GET, etc.)
        config.setAllowCredentials(true); // Allow cookies and authorization headers

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config); // Apply this configuration to all endpoints

        return new CorsFilter(source);
    }
}
