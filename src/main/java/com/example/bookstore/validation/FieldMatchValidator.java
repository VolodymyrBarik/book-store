package com.example.bookstore.validation;

import com.example.bookstore.dto.request.UserRegistrationRequestDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class FieldMatchValidator implements ConstraintValidator<FieldMatch,
        UserRegistrationRequestDto> {

    @Override
    public boolean isValid(UserRegistrationRequestDto dto, ConstraintValidatorContext context) {
        return dto.getPassword().equals(dto.getRepeatPassword());
    }
}
