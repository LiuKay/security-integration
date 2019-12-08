package com.kay.security.demo.repo;

import com.kay.security.demo.entity.User;

/**
 * @author LiuKay
 * @since 2019/12/8
 */
public interface UserDao {
    User getUserByName(String name);
}
