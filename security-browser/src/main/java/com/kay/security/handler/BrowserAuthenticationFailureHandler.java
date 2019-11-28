package com.kay.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kay.security.core.properties.LoginResponseType;
import com.kay.security.core.properties.SecurityProperties;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author LiuKay
 * @since 2019/11/28
 */
@Component("browserAuthenticationFailureHandler")
@Log4j2
public class BrowserAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    private final ObjectMapper objectMapper;
    private final SecurityProperties securityProperties;

    public BrowserAuthenticationFailureHandler(ObjectMapper objectMapper, SecurityProperties securityProperties) {
        this.objectMapper = objectMapper;
        this.securityProperties = securityProperties;
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        logger.info(">>onAuthenticationFailure:Login Failed.");

        if (LoginResponseType.JSON.equals(securityProperties.getBrowser().getLoginResponseType())) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setContentType(MediaType.APPLICATION_JSON.toString());
            //TODO convert to JSON format
            response.getWriter().write(objectMapper.writeValueAsString(exception.getMessage()));

        } else {
            super.onAuthenticationFailure(request, response, exception);
        }

    }
}
