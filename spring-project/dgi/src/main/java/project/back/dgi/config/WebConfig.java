package project.back.dgi.config;

import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import project.back.dgi.interceptor.AdminInterceptor;
import project.back.dgi.interceptor.AttemptInterceptor;
import project.back.dgi.interceptor.ProtectedInterceptor;
import project.back.dgi.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Autowired
    AdminInterceptor adminInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(attemptInterceptor(null)) // Intercepteur pour /auth/**
                .addPathPatterns("/auth/**");

        registry.addInterceptor(protectedInterceptor()) // Intercepteur pour /protected/**
                .addPathPatterns("/protected/**");

        registry.addInterceptor(adminInterceptor) // Intercepteur pour /admin/**
                .addPathPatterns("/admin/**");
    }

    @Bean
    public AttemptInterceptor attemptInterceptor(UserService userService) {
        return new AttemptInterceptor(userService);
    }

    @Bean
    public ProtectedInterceptor protectedInterceptor() {
        return new ProtectedInterceptor();
    }
}
