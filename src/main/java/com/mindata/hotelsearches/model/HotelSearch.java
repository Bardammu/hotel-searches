package com.mindata.hotelsearches.model;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import static java.util.List.copyOf;

/**
 * Record representing a hotel search.
 *
 * @param hotelId
 * @param checkIn
 * @param checkOut
 * @param ages
 *
 * @since 1.0.0
 */
public record HotelSearch(@NotBlank(message = "Hotel Id should not be null or blank") String hotelId,
                          @NotNull(message = "Check-in should not be null or blank") @Future Date checkIn,
                          @NotNull(message = "Check-out should not be null or blank") @Future Date checkOut,
                          @NotNull(message = "Ages should not be null or blank") List<Integer> ages) {

    public HotelSearch(String hotelId, Date checkIn, Date checkOut, List<@PositiveOrZero(message = "Ages should not be negative") Integer> ages) {
        this.hotelId = hotelId;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.ages = copyOf(ages);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof HotelSearch that)) {
            return false;
        }
        return Objects.equals(hotelId, that.hotelId) &&
                Objects.equals(checkIn, that.checkIn) &&
                Objects.equals(checkOut, that.checkOut) &&
                Objects.equals(ages, that.ages);
    }

    @Override
    public int hashCode() {
        return Objects.hash(hotelId, checkIn, checkOut, ages);
    }
}
