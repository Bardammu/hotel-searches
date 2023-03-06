package com.mindata.hotelsearches.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Field;
import java.util.Date;

import static org.apache.logging.log4j.LogManager.getLogger;

public class DatesValidator implements ConstraintValidator<CompareDates, Object> {

    Logger logger = getLogger(DatesValidator.class);

    private String beforeFieldName;

    private String afterFieldName;

    public void initialize(final CompareDates compareDatesAnnotation) {
        beforeFieldName = compareDatesAnnotation.before();
        afterFieldName = compareDatesAnnotation.after();
    }

    @Override
    public boolean isValid(final Object value, final ConstraintValidatorContext context) {
        try {
            final Field checkinField = value.getClass().getDeclaredField(beforeFieldName);
            checkinField.setAccessible(true);

            final Field checkoutField = value.getClass().getDeclaredField(afterFieldName);
            checkoutField.setAccessible(true);

            final Date beforeDate = (Date) checkinField.get(value);
            final Date afterDate = (Date) checkoutField.get(value);

            return beforeDate != null && afterDate != null && beforeDate.before(afterDate);
        } catch (final Exception e) {
            logger.error("Error validating check-in and check-out", e);
            return false;
        }
    }

}
