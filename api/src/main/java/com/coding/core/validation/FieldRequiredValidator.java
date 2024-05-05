package com.coding.core.validation;

import com.coding.core.util.Utils;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import static com.coding.core.constant.MessageKeyConstant.*;

import java.util.Locale;

@Component
public class FieldRequiredValidator implements ConstraintValidator<FieldRequired, Object> {
    private final MessageSource messageSource;
    private String fieldName;

    public FieldRequiredValidator(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Override
    public void initialize(FieldRequired constraintAnnotation) {
        this.fieldName = constraintAnnotation.fieldName();
    }

    @Override
    public boolean isValid(Object object, ConstraintValidatorContext context) {
        Locale locale = Locale.getDefault();
        String message = messageSource.getMessage(FIELD_REQUIRED, new Object[]{this.fieldName}, locale);
        return isValid(object, context, message);
    }

    private boolean isValid(Object object, ConstraintValidatorContext context, String message) {
        if (Utils.isObjectEmpty(object)) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
            return false;
        } else {
            return true;
        }
    }
}
