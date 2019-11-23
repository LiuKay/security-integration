package com.kay;

/**
 * @author LiuKay
 * @since 2019/11/23
 */
public class UserNotFoundException extends RuntimeException {

    private String userId;

    private static final String MESSAGE = "user not found";

    public UserNotFoundException(String userId) {
        super(MESSAGE);
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

}
