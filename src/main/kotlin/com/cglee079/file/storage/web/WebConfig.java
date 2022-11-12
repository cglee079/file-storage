package com.cglee079.file.storage.web;

import com.cglee079.file.storage.web.filter.RequestLoggingFilter;
import com.cglee079.file.storage.web.interceptor.RequestLoggingInterceptor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    final RequestLoggingInterceptor requestLoggingInterceptor;

    public WebConfig(RequestLoggingInterceptor requestLoggingInterceptor) {
        this.requestLoggingInterceptor = requestLoggingInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(requestLoggingInterceptor)
                .addPathPatterns("/api/**");
    }

    @Bean
    public FilterRegistrationBean getFilterRegistrationBean() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean(new RequestLoggingFilter());
        registrationBean.setOrder(Integer.MIN_VALUE);
        registrationBean.setUrlPatterns(Arrays.asList("/api/*"));
        return registrationBean;
    }

}




