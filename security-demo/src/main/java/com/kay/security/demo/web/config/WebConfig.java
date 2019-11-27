package com.kay.security.demo.web.config;

import com.kay.security.demo.web.filter.MockThirdPartyFilter;
import com.kay.security.demo.web.interceptor.RecordInterceptor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author LiuKay
 * @since 2019/11/24
 */
//@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final RecordInterceptor recordInterceptor;

    public WebConfig(RecordInterceptor recordInterceptor) {
        this.recordInterceptor = recordInterceptor;
    }

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


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(recordInterceptor);
    }
}
