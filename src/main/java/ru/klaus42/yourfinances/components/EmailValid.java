package ru.klaus42.yourfinances.components;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Constraint(validatedBy = EmailValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)

public @interface EmailValid {
    String message() default "Email уже используется в системе";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

