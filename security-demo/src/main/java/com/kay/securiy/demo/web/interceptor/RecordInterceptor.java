package com.kay.securiy.demo.web.interceptor;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;

/**
 * @author LiuKay
 * @since 2019/11/24
 */
//@Component
public class RecordInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("---------------RecordInterceptor start---------------------------");
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            String className = handlerMethod.getBean().getClass().getName();
            String methodName = handlerMethod.getMethod().getName();
            System.out.println("RecordInterceptor preHandle at " + LocalDateTime.now().toString() + " for handler :" + className + " " + methodName);
        } else {
            System.out.println("RecordInterceptor preHandle handler:" + handler.getClass().getName());
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            String className = handlerMethod.getBean().getClass().getName();
            String methodName = handlerMethod.getMethod().getName();
            System.out.println("RecordInterceptor postHandle at " + LocalDateTime.now().toString() + " for handler :" + className + " " + methodName);
        } else {
            System.out.println("RecordInterceptor postHandle handler:" + handler.getClass().getName());
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            String className = handlerMethod.getBean().getClass().getName();
            String methodName = handlerMethod.getMethod().getName();
            System.out.println("RecordInterceptor afterCompletion at " + LocalDateTime.now().toString() + "for handler :" + className + " " + methodName);
            System.out.println("ex is " + ex);
        } else {
            System.out.println("RecordInterceptor afterCompletion handler:" + handler.getClass().getName());
        }
        System.out.println("---------------RecordInterceptor end---------------------------");
    }
}
