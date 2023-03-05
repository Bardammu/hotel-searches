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
public class Search {

    @MongoId
    private String _id;

    @NotBlank
    private HotelSearch hotelSearch;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public HotelSearch getHotelSearch() {
        return hotelSearch;
    }

    public void setHotelSearch(HotelSearch hotelSearch) {
        this.hotelSearch = hotelSearch;
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
