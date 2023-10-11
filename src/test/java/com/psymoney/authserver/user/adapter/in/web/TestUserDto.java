package com.psymoney.authserver.user.adapter.in.web;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestUserDto {
    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    public void testValidUserDto() {
        UserDto userDto = new UserDto("validUsername", "validPassword");

        Set<ConstraintViolation<UserDto>> violations = validator.validate(userDto);

        assertEquals(0, violations.size());
    }

    @Test
    public void testInvalidBlankUsername() {
        UserDto userDto = new UserDto("", "validPassword");

        Set<ConstraintViolation<UserDto>> violations = validator.validate(userDto);

        assertEquals(1, violations.size());
    }

    @Test
    public void testInvalidBlankPassword() {
        UserDto userDto = new UserDto("n", "");

        Set<ConstraintViolation<UserDto>> violations = validator.validate(userDto);

        assertEquals(2, violations.size());
    }


    @Test
    public void testInvalidShortPassword() {
        UserDto userDto = new UserDto("validUsername", "1234567");

        Set<ConstraintViolation<UserDto>> violations = validator.validate(userDto);

        assertEquals(1, violations.size());
    }

    @Test
    public void testInvalidBlankUsernameAndInvalidBlankPassword() {
        UserDto userDto = new UserDto("", "");

        Set<ConstraintViolation<UserDto>> violations = validator.validate(userDto);

        assertEquals(3, violations.size());
    }

    @Test
    public void testInvalidBlankUsernameAndInvalidShortPassword() {
        UserDto userDto = new UserDto("", "1234567");

        Set<ConstraintViolation<UserDto>> violations = validator.validate(userDto);

        assertEquals(2, violations.size());
    }
}
