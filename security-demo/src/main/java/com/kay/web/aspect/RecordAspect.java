package com.kay.web.aspect;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;

/**
 * @author LiuKay
 * @since 2019/11/24
 */
//@Aspect
//@Component
public class RecordAspect {

    @Around(value = "execution(* com.kay.web.controller.UserController.*(..))")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        System.out.println(">>RecordAspect");
        Object[] args = pjp.getArgs();
        for (Object arg : args) {
            System.out.println("arg is :" + arg);
        }
        Object proceed = pjp.proceed();
        System.out.println(ReflectionToStringBuilder.toString(proceed, ToStringStyle.MULTI_LINE_STYLE));
        System.out.println("<<RecordAspect");
        return proceed;
    }
}
