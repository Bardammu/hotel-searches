package com.mindata.hotelsearches.derializer;

import com.mindata.hotelsearches.deserializer.LocalDateConverter;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

@ExtendWith(SpringExtension.class)
@TestInstance(PER_CLASS)
public class LocalDateConverterTestCase {

    private LocalDateConverter localDateConverter;

    @BeforeAll
    public void setUp() {
        localDateConverter = new LocalDateConverter();
    }

    @Test
    public void deserializeCheckInCheckOutDateFormat() {
        String date = "04/07/2023";
        LocalDate localDate =localDateConverter.convert(date);

        assertThat(localDate, is(LocalDate.of(2023, 7, 4)));
    }
}
