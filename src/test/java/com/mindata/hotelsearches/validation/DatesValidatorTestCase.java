package com.mindata.hotelsearches.validation;

import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;
import static org.mockito.Mockito.mock;

@ExtendWith(SpringExtension.class)
@TestInstance(PER_CLASS)
public class DatesValidatorTestCase {

    private ConstraintValidatorContext constraintValidatorContext;

    private LocalDate before;
    private LocalDate after;

    private DatesValidator datesValidator;

    @BeforeAll
    public void setUp() {
        constraintValidatorContext = mock(ConstraintValidatorContext.class);
        before = LocalDate.of(2023, 1, 1);
        after = LocalDate.of(2023, 1, 2);
        datesValidator = new DatesValidator();
        datesValidator.initialize(ClassWithDates.class.getAnnotation(CompareDates.class));
    }

    @Test
    public void testObjetWithValidDates() {
        boolean isValid = datesValidator.isValid(new ClassWithDates(before, after), constraintValidatorContext);

        assertThat(isValid, is(true));
    }

    @Test
    public void testObjetWithNotValidDates() {
        boolean isValid = datesValidator.isValid(new ClassWithDates(after, before), constraintValidatorContext);

        assertThat(isValid, is(false));
    }

    @CompareDates(before = "before", after = "after")
    private record ClassWithDates(LocalDate before, LocalDate after) {
    }

}
