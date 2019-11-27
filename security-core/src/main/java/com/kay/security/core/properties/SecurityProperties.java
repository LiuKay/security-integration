package com.kay.security.core.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author LiuKay
 * @since 2019/11/27
 */
@ConfigurationProperties(prefix = "integration.security")
public class SecurityProperties {

    private BrowserSecurityProperties browser = new BrowserSecurityProperties();

    public BrowserSecurityProperties getBrowser() {
        return browser;
    }

    public void setBrowser(BrowserSecurityProperties browser) {
        this.browser = browser;
    }
}
