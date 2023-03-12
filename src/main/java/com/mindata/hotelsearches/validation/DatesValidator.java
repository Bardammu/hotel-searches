package com.mindata.hotelsearches.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.Date;

import static org.apache.logging.log4j.LogManager.getLogger;

/**
 * Implementation of {@link ConstraintValidator} that can be used in conjunction with {@link CompareDates} to
 * validate that a {@link Date} is older than another {@link Date}
 *
 * @since 1.0.0
 */
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

            final LocalDate beforeDate = (LocalDate) checkinField.get(value);
            final LocalDate afterDate = (LocalDate) checkoutField.get(value);

            return beforeDate != null && afterDate != null && beforeDate.isBefore(afterDate);
        } catch (final Exception e) {
            logger.error("Error validating check-in and check-out", e);
            return false;
        }
    }

}
