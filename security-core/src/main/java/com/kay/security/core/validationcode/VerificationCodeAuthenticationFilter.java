package com.kay.security.core.validationcode;

import com.kay.security.core.properties.SecurityProperties;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * @author LiuKay
 * @since 2019/12/5
 */
@Log4j2
public class VerificationCodeAuthenticationFilter extends OncePerRequestFilter implements InitializingBean {

    private static final String FORM_LOGIN_URL = "/authentication/form";
    private static final String VERIFICATION_CODE_PARAMETER = "imageCode";

    private AuthenticationFailureHandler failureHandler;

    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    private SecurityProperties securityProperties;

    private Set<String> urls = new HashSet<>();

    // for url match
    private AntPathMatcher pathMatcher = new AntPathMatcher();

    // this is for securityProperties is filled in.
    @Override
    public void afterPropertiesSet() throws ServletException {
        super.afterPropertiesSet();
        String configUrls = securityProperties.getValidation().getImage().getUrl();
        if (StringUtils.isNoneBlank(configUrls)) {
            String[] strings = StringUtils.splitByWholeSeparatorPreserveAllTokens(configUrls, ",");
            for (String url : strings) {
                urls.add(url);
            }
        }

        urls.add(FORM_LOGIN_URL);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        boolean action = false;
        for (String url : urls) {
            if (pathMatcher.match(url, request.getRequestURI())) {
                action = true;
            }
        }

        if (action) {
            try {
                checkValidationCode(new ServletWebRequest(request));
            } catch (VerificationCodeException e) {
                log.info("login failed in validation code.");
                failureHandler.onAuthenticationFailure(request, response, e);
                return;
            }
        }
        filterChain.doFilter(request, response);
    }


    private void checkValidationCode(ServletWebRequest request) {
        ImageCode imageCode = (ImageCode) sessionStrategy.getAttribute(request, VerificationCodeController.VALIDATION_CODE_IN_SESSION);
        String codeInRequest = request.getParameter(VERIFICATION_CODE_PARAMETER);
        if (StringUtils.isBlank(codeInRequest)) {
            throw new VerificationCodeException("验证码的值不能为空");
        }

        if (imageCode.getCode() == null) {
            throw new VerificationCodeException("验证码不存在");
        }

        if (imageCode.isExpired()) {
            throw new VerificationCodeException("验证码已过期");
        }

        if (!StringUtils.equalsAnyIgnoreCase(codeInRequest, imageCode.getCode())) {
            throw new VerificationCodeException("验证码不匹配");
        }

        sessionStrategy.removeAttribute(request, VerificationCodeController.VALIDATION_CODE_IN_SESSION);
    }

    public VerificationCodeAuthenticationFilter(AuthenticationFailureHandler failureHandler) {
        this.failureHandler = failureHandler;
    }

    public void setSecurityProperties(SecurityProperties securityProperties) {
        this.securityProperties = securityProperties;
    }
}
