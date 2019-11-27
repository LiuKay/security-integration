package com.kay.securiy.demo.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author LiuKay
 * @since 2019/11/23
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = MyFieldValidator.class)
public @interface MyConstrain {

    String message() default "{javax.validation.constraints.MyConstrain.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
