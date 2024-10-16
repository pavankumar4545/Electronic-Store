package com.lcwd.electronic.store.customAnnotation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class GenderPatternAnnotation implements ConstraintValidator<GenderPattern,String> {
    @Override
    public boolean isValid(String gender, ConstraintValidatorContext constraintValidatorContext) {
        if(gender==null || gender.trim().isEmpty()){
            return false;
        }
        String normalizeGender=gender.trim().toLowerCase();

        return normalizeGender.equals("male") || normalizeGender.equals("female");


    }
}
