package com.lcwd.electronic.store.customAnnotation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ImageNameValidator implements ConstraintValidator<ImagePattern,String> {
    @Override
    public boolean isValid(String imageName, ConstraintValidatorContext constraintValidatorContext) {

        if(imageName==null || imageName.trim().isEmpty()){
            return false;
        }

        return imageName.endsWith(".jpg") || imageName.endsWith(".png");
    }
}






