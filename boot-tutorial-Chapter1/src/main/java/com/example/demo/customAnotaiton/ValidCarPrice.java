/**
 * 
 */
package com.example.demo.customAnotaiton;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Documented
@Constraint(validatedBy = ValidPrice.class)
@Retention(RUNTIME)
@Target({FIELD, METHOD, PARAMETER})
/**
 * 
 */
public @interface ValidCarPrice {
	String message() default "CarPrice must < 100.00";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
