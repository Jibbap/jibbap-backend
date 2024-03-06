package dev.beomseok.jibbap.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // 모든 요청에 대해 CORS 설정 적용
                .allowedOrigins("*") // 모든 도메인 허용
                .allowedMethods("*") // 모든 HTTP 메서드 허용
                .allowedHeaders("*"); // 모든 HTTP 헤더 허용
    }
}
