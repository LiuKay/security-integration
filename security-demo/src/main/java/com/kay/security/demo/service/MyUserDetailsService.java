package com.kay.security.demo.service;

import com.kay.security.demo.repo.UserDao;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * Demo for {@link UserDetailsService}
 * @author LiuKay
 * @since 2019/11/27
 */
@Component
@Log4j2
public class MyUserDetailsService implements UserDetailsService {

    private final UserDao userDao;

    @Autowired
    public MyUserDetailsService(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // TODO load user by username from DB and check user status,such as locked, enable..
        com.kay.security.demo.entity.User user = userDao.getUserByName(username);

        log.info("loadUserByUsername>>user={}", user);


        return User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .authorities(AuthorityUtils.commaSeparatedStringToAuthorityList("admin"))
                .build();
    }

}
