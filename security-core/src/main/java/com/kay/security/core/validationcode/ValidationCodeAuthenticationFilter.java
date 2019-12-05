package com.kay.security.core.validationcode;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author LiuKay
 * @since 2019/12/5
 */
@Log4j2
public class ValidationCodeAuthenticationFilter extends OncePerRequestFilter {

    private AuthenticationFailureHandler failureHandler;

    public ValidationCodeAuthenticationFilter(AuthenticationFailureHandler failureHandler) {
        this.failureHandler = failureHandler;
    }

    SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (StringUtils.equals("/authentication/form", request.getRequestURI()) && StringUtils.equals(request.getMethod(), "POST")) {
            try {
                checkValidationCode(new ServletWebRequest(request));
            } catch (ValidateCodeException e) {
                log.info("login failed in va,idation code.");
                failureHandler.onAuthenticationFailure(request, response, e);
            }
        }

        filterChain.doFilter(request, response);
    }


    private void checkValidationCode(ServletWebRequest request) {
        ImageCode imageCode = (ImageCode) sessionStrategy.getAttribute(request, ValidationCodeController.VALIDATION_CODE_IN_SESSION);
        String codeInRequest = request.getParameter("imageCode");
        if (StringUtils.isBlank(codeInRequest)) {
            throw new ValidateCodeException("验证码的值不能为空");
        }

        if (imageCode.getCode() == null) {
            throw new ValidateCodeException("验证码不存在");
        }

        if (imageCode.isExpired()) {
            throw new ValidateCodeException("验证码已过期");
        }

        if (!StringUtils.equalsAnyIgnoreCase(codeInRequest, imageCode.getCode())) {
            throw new ValidateCodeException("验证码不匹配");
        }

        sessionStrategy.removeAttribute(request, ValidationCodeController.VALIDATION_CODE_IN_SESSION);
    }
}
