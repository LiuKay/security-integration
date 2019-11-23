package com.kay.entity;

import com.fasterxml.jackson.annotation.JsonView;

/**
 * @author LiuKay
 * @since 2019/11/23
 */
public class User {

    public interface UserSimpleView{}
    public interface UserDetailView extends UserSimpleView{}


    private String id;
    private String username;
    private String password;

    @JsonView(UserSimpleView.class)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @JsonView(UserSimpleView.class)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @JsonView(UserDetailView.class)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
