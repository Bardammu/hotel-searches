package com.mindata.hotelsearches.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mindata.hotelsearches.validation.CompareDates;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;
import java.util.List;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING;

/**
 * Record representing a hotel search.
 *
 * @since 1.0.0
 */
@Validated
@CompareDates(before = "checkIn", after = "checkOut")
public record HotelSearch(@NotBlank(message = "Hotel Id should not be null or blank") String hotelId,
                          @NotNull(message = "Check-in should not be null or blank") @Future(message = "Check-in should not be in the past") @JsonFormat(shape = STRING, pattern = "dd/MM/yyyy")  LocalDate checkIn,
                          @NotNull(message = "Check-out should not be null or blank") @Future(message = "Check-out should not be in the past") @JsonFormat(shape = STRING, pattern = "dd/MM/yyyy") LocalDate checkOut,
                          @NotEmpty(message = "Ages should not be null or blank") List<@PositiveOrZero(message = "Ages should not be negative") Integer> ages) {
}
