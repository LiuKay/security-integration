package com.kay.web.config;

import com.kay.web.filter.MockThirdPartyFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author LiuKay
 * @since 2019/11/24
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     * register third-party filter as Spring Bean
     *
     * @return filter bean
     */
    @Bean
    public FilterRegistrationBean thirdPartyFilterBean() {
        FilterRegistrationBean<MockThirdPartyFilter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new MockThirdPartyFilter());
        return filterRegistrationBean;
    }

}
