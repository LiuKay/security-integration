package com.kay.security.core.validationcode;

import java.awt.image.BufferedImage;
import java.time.LocalDateTime;

/**
 * @author LiuKay
 * @since 2019/12/5
 */
public class ImageCode {

    private BufferedImage image;
    private String code;
    private LocalDateTime expiredTime;

    public ImageCode(BufferedImage image, String code, int expiredSeconds) {
        this.image = image;
        this.code = code;
        this.expiredTime = LocalDateTime.now().plusSeconds(expiredSeconds);
    }

    boolean isExpired() {
        return LocalDateTime.now().isAfter(expiredTime);
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }
}
