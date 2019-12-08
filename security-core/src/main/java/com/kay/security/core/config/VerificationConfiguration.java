package com.kay.security.core.config;

import com.kay.security.core.properties.SecurityProperties;
import com.kay.security.core.validationcode.*;
import com.kay.security.core.validationcode.image.ImageCodeGenerator;
import com.kay.security.core.validationcode.sms.SmsCodeGenerator;
import com.kay.security.core.validationcode.sms.SmsCodeSender;
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
        return new ImageCodeGenerator(securityProperties);
    }

    @Bean
    @ConditionalOnMissingBean(name = "smsCodeGenerator")
    VerificationCodeGenerator smsCodeGenerator() {
        return new SmsCodeGenerator(securityProperties);
    }

    @Bean
    @ConditionalOnMissingBean(SmsCodeSender.class)
    SmsCodeSender smsCodeSender() {
        return new DefaultSmsCodeSender();
    }
}
