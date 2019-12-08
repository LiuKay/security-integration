package com.kay.security;

import com.kay.security.core.authentication.AbstractSecurityConfiguration;
import com.kay.security.core.authentication.mobile.SmsCodeSecurityConfiguration;
import com.kay.security.core.config.VerificationCodeSecurityConfiguration;
import com.kay.security.core.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

import static com.kay.security.core.properties.SecurityConstants.*;

/**
 * @author LiuKay
 * @since 2019/11/27
 */
@Configuration
public class BrowserSecurityConfiguration extends AbstractSecurityConfiguration {

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private VerificationCodeSecurityConfiguration verificationCodeSecurityConfiguration;

    @Autowired
    private SmsCodeSecurityConfiguration smsCodeSecurityConfiguration;

    @Bean
    PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        //TODO: only call once to create a table, return Exception secondly. Or you can create table by yourself.
//        tokenRepository.setCreateTableOnStartup(true);
        tokenRepository.setDataSource(dataSource);
        return tokenRepository;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        applyFormLoginConfig(http);

        http.apply(verificationCodeSecurityConfiguration)
                .and()
                .apply(smsCodeSecurityConfiguration)
                .and()
                .rememberMe()
                .tokenRepository(persistentTokenRepository())
                .userDetailsService(userDetailsService)
                .tokenValiditySeconds(60)
                .and()
            .authorizeRequests()
                .antMatchers(
                        securityProperties.getBrowser().getLoginPage(),
                        AUTHENTICATION_URL,
                        LOGIN_MOBILE_PROCESSING_URL,
                        VERIFICATION_CODE_URL)
                .permitAll()
            .anyRequest()
            .authenticated()
            .and()
            .csrf().disable();
    }

}
