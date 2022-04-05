package com.example.shopspringboot.validation;

import com.example.shopspringboot.bean.SignUpFormBean;
import com.example.shopspringboot.validation.annotation.PasswordMatches;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<PasswordMatches, SignUpFormBean> {
    private String message;

    @Override
    public void initialize(PasswordMatches validPassword) {
        this.message = validPassword.message();
    }

    @Override
    public boolean isValid(SignUpFormBean bean, ConstraintValidatorContext context) {
        boolean valid = bean.getPassword().equals(bean.getConfirmPassword());

        if (!valid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(message).addPropertyNode("confirmPassword")
                    .addConstraintViolation();
        }
        return valid;
    }
}