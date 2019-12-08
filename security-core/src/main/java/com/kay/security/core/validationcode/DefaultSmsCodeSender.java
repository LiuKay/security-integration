package com.kay.security.core.validationcode;

import com.kay.security.core.validationcode.sms.SmsCodeSender;

/**
 * Default implementation of SmsCodeSender
 *
 * @author LiuKay
 * @since 2019/12/7
 */
public class DefaultSmsCodeSender implements SmsCodeSender {
    @Override
    public void send(String mobile, String code) {
        String format = String.format("Mock sending a sms code [%s] to mobile [%s] ", code, mobile);
        System.out.println(format);
    }
}
