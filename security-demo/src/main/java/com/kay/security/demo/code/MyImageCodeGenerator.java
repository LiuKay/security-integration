package com.kay.security.demo.code;

import com.kay.security.core.validationcode.ImageCode;
import com.kay.security.core.validationcode.VerificationCodeGenerator;
import lombok.extern.log4j.Log4j2;

import javax.servlet.http.HttpServletRequest;

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
    public ImageCode generate(HttpServletRequest request) {

        log.warn(">>custom image code generator works.");
        return null;
    }
}
