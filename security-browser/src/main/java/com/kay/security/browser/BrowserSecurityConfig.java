package com.kay.security.browser;

import com.kay.security.core.properties.SecurityProperties;
import com.kay.security.core.validationcode.VerificationCodeAuthenticationFilter;
import com.kay.security.core.validationcode.VerificationCodeProcessorHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

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

    @Qualifier("myUserDetailsService")
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private VerificationCodeProcessorHolder verificationCodeProcessorHolder;

    @Bean
    PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        //TODO: only call once to create a table, return Exception secondly. Or you can create table by yourself.
//        tokenRepository.setCreateTableOnStartup(true);
        tokenRepository.setDataSource(dataSource);
        return tokenRepository;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        VerificationCodeAuthenticationFilter codeAuthenticationFilter = new VerificationCodeAuthenticationFilter();
        codeAuthenticationFilter.setFailureHandler(failureHandler);
        codeAuthenticationFilter.setSecurityProperties(securityProperties);
        codeAuthenticationFilter.setProcessorHolder(verificationCodeProcessorHolder);
        // TODO: don't forget this. ValidationCodeAuthenticationFilter currently is not a Spring bean
        codeAuthenticationFilter.afterPropertiesSet();

        http.addFilterBefore(codeAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .formLogin()
                .loginPage(AUTHENTICATION_URL)
                .loginProcessingUrl(LOGIN_PROCESSING_URL)
                .successHandler(successHandler)
                .failureHandler(failureHandler)
                .and()
                .rememberMe()
                .tokenRepository(persistentTokenRepository())
                .userDetailsService(userDetailsService)
                .tokenValiditySeconds(60)
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
