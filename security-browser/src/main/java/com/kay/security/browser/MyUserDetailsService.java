package com.kay.security.browser;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * @author LiuKay
 * @since 2019/11/27
 */
@Component("myUserDetailsService")
@Log4j2
public class MyUserDetailsService implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public MyUserDetailsService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // TODO load user by username from DB and check user status,such as locked, enable..

        // NOTE: this encodePassword should be queried from Database.
        String encodePassword = passwordEncoder.encode("123456");
        log.info("Password is :{}", encodePassword);

        return User.builder()
                .username(username)
                .password(encodePassword)
                .authorities(AuthorityUtils.commaSeparatedStringToAuthorityList("admin"))
                .build();
    }

}
