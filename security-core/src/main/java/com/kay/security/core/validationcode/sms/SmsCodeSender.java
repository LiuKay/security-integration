package com.kay.security.core.validationcode.sms;

/**
 * @author LiuKay
 * @since 2019/12/7
 */
public interface SmsCodeSender {
    void send(String mobile, String code);
}
