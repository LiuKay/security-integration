package com.kay.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author LiuKay
 * @since 2019/11/23
 */
public class MyFieldValidator implements ConstraintValidator<MyConstrain, Object> {


    @Override
    public void initialize(MyConstrain constraintAnnotation) {
        System.out.println("init MyFieldValidator");
    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        System.out.println(o);
        return false;
    }
}
