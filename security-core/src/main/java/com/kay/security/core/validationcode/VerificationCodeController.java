package com.kay.security.core.validationcode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author LiuKay
 * @since 2019/12/5
 */
@RestController
public class VerificationCodeController {

    SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    public static final String VALIDATION_CODE_IN_SESSION = "VALIDATION_CODE_IN_SESSION";

    @Autowired
    private VerificationCodeGenerator verificationCodeGenerator;

    @GetMapping("/code/image")
    public void createCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ServletWebRequest webRequest = new ServletWebRequest(request);
        ImageCode imageCode = verificationCodeGenerator.generate(request);
        sessionStrategy.setAttribute(webRequest, VALIDATION_CODE_IN_SESSION, imageCode);
        ImageIO.write(imageCode.getImage(), "JPEG", response.getOutputStream());
    }

}
