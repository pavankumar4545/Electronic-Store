package com.lcwd.electronic.store.customAnnotation;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD,ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy=ImageNameValidator.class)
public @interface ImagePattern {

    String message() default "Invalid Image formate! Only jpg and png are supported";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}




