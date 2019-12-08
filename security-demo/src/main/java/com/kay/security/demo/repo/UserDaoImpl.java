package com.kay.security.demo.repo;

import com.kay.security.demo.entity.User;
import org.springframework.stereotype.Component;

/**
 * @author LiuKay
 * @since 2019/12/8
 */
@Component
public class UserDaoImpl implements UserDao {
    @Override
    public User getUserByName(String name) {
        // TODO: Mock
        User user = new User();
        user.setUsername(name);
        // 123456
        user.setPassword("$2a$10$5UuIEE2zBnhba.fPHPR61OIuC9jT23Rig.GoCIQqKK4V7W8YX8M66");
        return user;
    }
}
