package com.kay.security.browser;

import com.kay.security.core.properties.SecurityProperties;
import com.kay.security.core.validationcode.ValidationCodeAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author LiuKay
 * @since 2019/11/27
 */
@Configuration
public class BrowserSecurityConfig extends WebSecurityConfigurerAdapter {

    public static final String AUTHENTICATION_URL = "/authentication/require";
    public static final String LOGIN_PROCESSING_URL = "/authentication/form";

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private AuthenticationSuccessHandler successHandler;

    @Autowired
    private AuthenticationFailureHandler failureHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        ValidationCodeAuthenticationFilter codeAuthenticationFilter = new ValidationCodeAuthenticationFilter(failureHandler);

        http.addFilterBefore(codeAuthenticationFilter, UsernamePasswordAuthenticationFilter.class).
                formLogin()
                .loginPage(AUTHENTICATION_URL)
                .loginProcessingUrl(LOGIN_PROCESSING_URL)
                .successHandler(successHandler)
                .failureHandler(failureHandler)
            .and()
            .authorizeRequests()
                .antMatchers(securityProperties.getBrowser().getLoginPage(),
                        AUTHENTICATION_URL,
                        "/code/image").permitAll()
            .anyRequest()
            .authenticated()
            .and()
            .csrf().disable();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
