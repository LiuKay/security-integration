package com.kay.security.core.validationcode.sms;

import com.kay.security.core.properties.SecurityProperties;
import com.kay.security.core.properties.SmsCodeProperties;
import com.kay.security.core.validationcode.VerificationCode;
import com.kay.security.core.validationcode.VerificationCodeGenerator;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * @author LiuKay
 * @since 2019/12/7
 */
public class SmsCodeGenerator implements VerificationCodeGenerator {

    private final SecurityProperties properties;

    public SmsCodeGenerator(SecurityProperties properties) {
        this.properties = properties;
    }

    @Override
    public VerificationCode generate(ServletWebRequest request) {
        SmsCodeProperties sms = properties.getValidation().getSms();
        String alphanumeric = RandomStringUtils.randomAlphanumeric(sms.getLength());
        return new VerificationCode(alphanumeric, sms.getExpire());
    }
}
