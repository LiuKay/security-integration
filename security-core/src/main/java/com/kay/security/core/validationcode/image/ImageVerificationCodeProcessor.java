package com.kay.security.core.validationcode.image;

import com.kay.security.core.validationcode.AbstractVerificationCodeProcessor;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

import javax.imageio.ImageIO;
import java.io.IOException;

/**
 * @author LiuKay
 * @since 2019/12/7
 */
@Component("imageVerificationCodeProcessor")
public class ImageVerificationCodeProcessor extends AbstractVerificationCodeProcessor<ImageCode> {

    /**
     * Send verificationCode to response
     *
     * @param request
     * @param verificationCode
     * @throws IOException
     */
    @Override
    protected void send(ServletWebRequest request, ImageCode verificationCode) throws IOException {
        ImageIO.write(verificationCode.getImage(), "JPEG", request.getResponse().getOutputStream());
    }
}
