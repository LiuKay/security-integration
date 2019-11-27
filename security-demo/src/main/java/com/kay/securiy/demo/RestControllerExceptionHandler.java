package com.kay.securiy.demo;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * @author LiuKay
 * @since 2019/11/23
 */
@ControllerAdvice
@ResponseBody
public class RestControllerExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handle(UserNotFoundException ex) {
        Map<String, Object> result = new HashMap<>();
        result.put("message", ex.getMessage());
        result.put("userId", ex.getUserId());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
    }

}
