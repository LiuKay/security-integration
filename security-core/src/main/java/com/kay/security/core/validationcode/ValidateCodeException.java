package com.kay.security.core.validationcode;

import org.springframework.security.core.AuthenticationException;

/**
 * @author LiuKay
 * @since 2019/12/5
 */
class ValidateCodeException extends AuthenticationException {

    public ValidateCodeException(String msg, Throwable t) {
        super(msg, t);
    }

    public ValidateCodeException(String msg) {
        super(msg);
    }
}
