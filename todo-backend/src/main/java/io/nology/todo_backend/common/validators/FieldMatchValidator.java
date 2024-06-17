package io.nology.todo_backend.common.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.BeanWrapperImpl;

public class FieldMatchValidator implements ConstraintValidator<Matches, Object> {

    private String fieldName;
    private String matchingFieldName;

    @Override
    public void initialize(Matches constraintAnnotation) {
        this.fieldName = constraintAnnotation.field();
        this.matchingFieldName = constraintAnnotation.matchingField();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null) {
            return true; // Use @NotNull for null checks
        }

        Object fieldValue = new BeanWrapperImpl(value).getPropertyValue(fieldName);
        Object matchingFieldValue = new BeanWrapperImpl(value).getPropertyValue(matchingFieldName);

        if (fieldValue != null) {
            return fieldValue.equals(matchingFieldValue);
        } else {
            return matchingFieldValue == null;
        }
    }
}
