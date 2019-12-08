package com.kay.security.core.validationcode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author LiuKay
 * @since 2019/12/5
 */
@RestController
public class VerificationCodeController {

    @Autowired
    private VerificationCodeProcessorHolder processorHolder;

    @GetMapping("/code/{type}")
    public void createImageCode(HttpServletRequest request, HttpServletResponse response, @PathVariable String type) throws Exception {
        processorHolder.findProcessor(type).create(new ServletWebRequest(request, response));
    }

}
