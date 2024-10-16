package com.lcwd.electronic.store.customAnnotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD,ElementType.CONSTRUCTOR})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = GenderPatternAnnotation.class)
public @interface GenderPattern {

    String message() default "Gender must be Male or Female";

    Class<?>[] groups() default {};

    Class<? extends Payload >[] payload() default {};
}
