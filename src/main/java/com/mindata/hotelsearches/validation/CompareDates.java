package com.mindata.hotelsearches.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

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
