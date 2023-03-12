package com.mindata.hotelsearches.deserializer;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

import static java.time.format.DateTimeFormatter.ofPattern;
import static java.time.LocalDate.parse;

/**
 * Custom {@link Converter} to convert date format dd/MM/yyyy
 *
 * @since 1.0.0
 */
@Component
public class LocalDateConverter implements Converter<String, LocalDate> {

    public static final String DATE_FORMATTER = "dd/MM/yyyy";

    @Override
    public LocalDate convert(String source) {
        return parse(source, ofPattern(DATE_FORMATTER));
    }
}
