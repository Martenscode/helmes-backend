package com.marten.helmesbackend.config.logging;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

@Configuration
public class LoggingConfig {

    @Bean
    public FilterRegistrationBean<RequestLoggingFilter> loggingFilter() {
        FilterRegistrationBean<RequestLoggingFilter> loggingFilter = new FilterRegistrationBean<>();
        loggingFilter.setFilter(new RequestLoggingFilter());
        loggingFilter.addUrlPatterns("/api/*");
        loggingFilter.setOrder(Ordered.LOWEST_PRECEDENCE);
        return loggingFilter;
    }

}