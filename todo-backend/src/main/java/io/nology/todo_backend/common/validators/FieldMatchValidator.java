package io.nology.todo_backend.common.validators;

import java.lang.reflect.Field;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class FieldMatchValidator implements ConstraintValidator<Matches, Object> {

    private String fieldName;

    @Override
    public void initialize(Matches constraintAnnotation) {
        this.fieldName = constraintAnnotation.field();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }
        try {
            Object bean = getRootBean(context);
            Field field = bean.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            Object fieldValue = field.get(bean);
            return value.equals(fieldValue);
        } catch (Exception e) {
            return false;
        }
    }

    private Object getRootBean(ConstraintValidatorContext context) {
        try {
            Field field = context.getClass().getDeclaredField("rootBean");
            field.setAccessible(true);
            return field.get(context);
        } catch (Exception e) {
            throw new RuntimeException("Unable to access root bean");
        }
    }
}
