package com.kay.security.demo.code;

import com.kay.security.core.validationcode.VerificationCode;
import com.kay.security.core.validationcode.VerificationCodeGenerator;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * This is a demo for custom image code generator
 *
 * @author LiuKay
 * @since 2019/12/6
 */
//@Component("imageCodeGenerator")
@Log4j2
public class MyImageCodeGenerator implements VerificationCodeGenerator {

    @Override
    public VerificationCode generate(ServletWebRequest request) {

        log.warn(">>custom image code generator works.");
        return null;
    }
}
