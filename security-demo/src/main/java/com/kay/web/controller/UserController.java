package com.kay.web.controller;

import com.kay.entity.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author LiuKay
 * @since 2019/11/23
 */
@RestController
@RequestMapping
public class UserController {

    @GetMapping("/users")
    public ResponseEntity list() {
        List<User> userList = new ArrayList<User>();
        userList.add(new User());
        userList.add(new User());
        userList.add(new User());

        return ResponseEntity.ok(userList);
    }

    // id://d+ => id must be number
    @GetMapping("/users/{id:\\d+}")
    public ResponseEntity getUser(@PathVariable String id) {
        System.out.println(id);
        User user = new User();
        user.setId("1");
        user.setUsername("user1");
        return ResponseEntity.ok(user);
    }

}
