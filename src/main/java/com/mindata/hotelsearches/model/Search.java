package com.mindata.hotelsearches.model;

import jakarta.validation.constraints.NotBlank;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.Objects;

/**
 * Document representing a hotel search that can be stored
 *
 * @since 1.0.0
 */
@Document(collection="hotel_availability_searches")
public final class Search {

    @MongoId
    private final String _id;

    @NotBlank
    private final HotelSearch hotelSearch;

    public Search(String _id, HotelSearch hotelSearch) {
        this._id = _id;
        this.hotelSearch = hotelSearch;
    }

    public String get_id() {
        return _id;
    }

    public HotelSearch getHotelSearch() {
        return hotelSearch;
    }

    @Override
    public String toString() {
        return "Search{" +
                "_id='" + _id + '\'' +
                ", hotelSearch=" + hotelSearch.toString() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Search that)) {
            return false;
        }
        return Objects.equals(_id, that._id) && Objects.equals(hotelSearch, that.hotelSearch);
    }

    @Override
    public int hashCode() {
        return Objects.hash(_id, hotelSearch);
    }
}
