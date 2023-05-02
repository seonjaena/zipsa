package com.project.zipsa.validate;

import javax.validation.Constraint;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = RegExValidator.class)
public @interface RegExValidate {

    String message() default "";
    Class[] groups() default {};
    Class[] payload() default {};

    VALIDATE_REGEX regex() default VALIDATE_REGEX.NONE;

}
