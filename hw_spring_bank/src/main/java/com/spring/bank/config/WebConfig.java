package com.spring.bank.config;

import com.spring.bank.web.RequestHasUserInterceptor;
import com.spring.bank.web.RequestNoUserInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/preline/**")
                .addResourceLocations("classpath:/frontend/node_modules/preline/dist/");
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("redirect:/accounts");
        registry.addViewController("/register").setViewName("redirect:/auth/register");
        registry.addViewController("/login").setViewName("redirect:/auth/login");
        registry.addViewController("/logout").setViewName("redirect:/auth/logout");
        registry.addViewController("/error").setViewName("");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new RequestHasUserInterceptor())
                .addPathPatterns("/auth/login", "/auth/register");
        registry.addInterceptor(new RequestNoUserInterceptor())
                .addPathPatterns("/accounts/**");
    }
}
