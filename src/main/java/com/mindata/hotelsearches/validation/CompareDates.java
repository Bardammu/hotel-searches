package com.mindata.hotelsearches.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.util.Date;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Custom annotation that can be used to compare if one {@link Date} is older that another {@link Date}
 *
 * @since 1.0.0
 */
@Constraint(validatedBy = DatesValidator.class)
@Target({ TYPE, ANNOTATION_TYPE  })
@Retention(RUNTIME)
@Documented
public @interface CompareDates {

    String message() default "Check-In should happen before check-out";

    Class <?> [] groups() default {};

    Class <? extends Payload> [] payload() default {};

    String before();

    String after();
}
