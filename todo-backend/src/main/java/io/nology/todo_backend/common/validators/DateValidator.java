package io.nology.todo_backend.common.validators;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class DateValidator implements ConstraintValidator<ValidDate, String> {

    private String pattern;
    private TemporalValidation temporalValidation;

    @Override
    public void initialize(ValidDate constraintAnnotation) {
        this.pattern = constraintAnnotation.pattern();
        this.temporalValidation = constraintAnnotation.temporalValidation();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        if (value == null || value.isEmpty()) {
            return true;
        }

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
            LocalDate date = LocalDate.parse(value, formatter);
            LocalDate today = LocalDate.now();
            boolean valid = true;
            String message = "";
            switch (temporalValidation) {
                case FUTURE:
                    if (!date.isAfter(today)) {
                        valid = false;
                        message = "Date must be in the future";
                    }
                    break;
                case FUTURE_OR_PRESENT:
                    if (date.isBefore(today)) {
                        valid = false;
                        message = "Date must be today or in the future";
                    }
                    break;
                case PAST:
                    if (!date.isBefore(today)) {
                        valid = false;
                        message = "Date must be in the past";
                    }
                    break;
                case PAST_OR_PRESENT:
                    if (date.isAfter(today)) {
                        valid = false;
                        message = "Date must be today or in the past";
                    }
                    break;
                case NONE:
                default:
                    break;
            }

            if (!valid) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
            }
            return valid;

        } catch (DateTimeParseException e) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Invalid date format").addConstraintViolation();
            return false;
        }
    }

}
