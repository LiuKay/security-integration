package com.kay.securiy.demo.web.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.kay.securiy.demo.UserNotFoundException;
import com.kay.securiy.demo.entity.User;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * @author LiuKay
 * @since 2019/11/23
 */
@RestController
@RequestMapping("/users")
public class UserController {

    /**
     * 使用 JsonView 给对象定义不同的视图，隐藏字段等
     */
    @GetMapping
    @JsonView(User.UserSimpleView.class)
    public ResponseEntity list() {
        List<User> userList = new ArrayList<>();
        User user1 = new User();
        user1.setId("u001");
        user1.setUsername("Tom");
        userList.add(user1);

        User user2 = new User();
        user2.setId("u002");
        user2.setUsername("Jim");
        userList.add(user2);

        User user3 = new User();
        user3.setId("u003");
        user3.setUsername("Lucy");
        userList.add(user3);

        return ResponseEntity.ok(userList);
    }

    // id://d+ => id must be number
    @GetMapping("/{id:\\d+}")
    @JsonView(User.UserDetailView.class)
    public ResponseEntity getUser(@PathVariable String id) {
        throw new UserNotFoundException(id);
//        System.out.println(id);
//        User user = new User();
//        user.setId("1");
//        user.setUsername("user1");
//        return ResponseEntity.ok(user);
    }

    @PostMapping
    public ResponseEntity<User> create(@Valid @RequestBody User user, BindingResult result) {
        if (result.hasErrors()) {
            result.getAllErrors().forEach(error -> System.out.println(error.getDefaultMessage()));
        }
        user.setId("1");
        return ResponseEntity.ok(user);
    }

    @PutMapping("/{id:\\d+}")
    public ResponseEntity<User> update(@PathVariable String id, @RequestBody User user) {
        user.setUsername("update_name");
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/{id:\\d+}")
    public ResponseEntity delete(@PathVariable String id) {
        System.out.println("delete user id " + id);
        return ResponseEntity.ok().build();
    }
}
