package com.alexcibotari.zooplus.utils.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

/**
 * Validate Email according to RFC
 */
public class EmailRFCValidator implements ConstraintValidator<EmailRFC, String> {


    @Override
    public void initialize(EmailRFC constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if ( value == null ) {
            return true;
        }
        return Pattern.matches(ValidationConstants.EMAIL_RFC_REGEX, value);
    }
}
