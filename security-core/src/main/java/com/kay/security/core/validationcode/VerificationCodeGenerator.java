package com.kay.security.core.validationcode;

import javax.servlet.http.HttpServletRequest;

/**
 * @author LiuKay
 * @since 2019/12/6
 */
public interface VerificationCodeGenerator {
    ImageCode generate(HttpServletRequest request);
}
