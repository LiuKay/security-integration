package com.kay.security.core.properties;

/**
 * @author LiuKay
 * @since 2019/12/6
 */
public class VerificationCodeProperties {

    ImageCodeProperties image = new ImageCodeProperties();

    public ImageCodeProperties getImage() {
        return image;
    }

    public void setImage(ImageCodeProperties image) {
        this.image = image;
    }
}
