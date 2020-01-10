package com.baeldung.um.spring;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.ShallowEtagHeaderFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@ComponentScan({ "com.baeldung.common.web", "com.baeldung.um.web" })
@EnableWebMvc
public class UmWebConfig implements WebMvcConfigurer {

    public UmWebConfig() {
        super();
    }

    // beans

    @Bean(name = "etagFilter")
    public ShallowEtagHeaderFilter etagFilter() {
        return new ShallowEtagHeaderFilter();
    }

    @Bean
    public FilterRegistrationBean shallowEtagHeaderFilter() {
        FilterRegistrationBean filterRegistrationBean
                = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(etagFilter());
        filterRegistrationBean.addUrlPatterns("*");
        filterRegistrationBean.setName("etagFilter");
        filterRegistrationBean.setOrder(1);
        return filterRegistrationBean;
    }



}
