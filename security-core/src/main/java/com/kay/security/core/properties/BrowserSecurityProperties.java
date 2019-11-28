package com.kay.security.core.properties;

/**
 * @author LiuKay
 * @since 2019/11/27
 */
public class BrowserSecurityProperties {

    private static final String DEFAULT_LOGIN_PAGE = "/default_login.html";

    private String loginPage = DEFAULT_LOGIN_PAGE;

    private LoginResponseType loginResponseType = LoginResponseType.JSON;

    public String getLoginPage() {
        return loginPage;
    }

    public void setLoginPage(String loginPage) {
        this.loginPage = loginPage;
    }

    public LoginResponseType getLoginResponseType() {
        return loginResponseType;
    }

    public void setLoginResponseType(LoginResponseType loginResponseType) {
        this.loginResponseType = loginResponseType;
    }
}