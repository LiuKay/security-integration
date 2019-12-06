package com.kay.security.core.config;

import com.kay.security.core.properties.SecurityProperties;
import com.kay.security.core.validationcode.DefaultVerificationCodeGenerator;
import com.kay.security.core.validationcode.VerificationCodeGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author LiuKay
 * @since 2019/12/6
 */
@Configuration
public class VerificationConfiguration {

    @Autowired
    private SecurityProperties securityProperties;

    @Bean
    @ConditionalOnMissingBean(name = "imageCodeGenerator")
    VerificationCodeGenerator imageCodeGenerator() {
        DefaultVerificationCodeGenerator generator = new DefaultVerificationCodeGenerator();
        generator.setProperties(securityProperties);
        return generator;
    }
}
