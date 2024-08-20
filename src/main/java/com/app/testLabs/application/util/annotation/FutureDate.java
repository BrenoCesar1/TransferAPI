package com.app.testLabs.application.util.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = FutureDateValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface FutureDate {
    String message() default "O campo deve conter uma data futura!";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
