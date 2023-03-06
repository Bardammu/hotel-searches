package com.mindata.hotelsearches.service;

import com.mindata.hotelsearches.model.HotelSearch;
import com.mindata.hotelsearches.model.response.HotelSearchCountResponse;

/**
 * Service to store and retrieve count of Hotel Searches.
 *
 * @since 1.0.0
 */
public interface SearchService {

    /**
     * Stores a {@link HotelSearch}
     *
     * @param hotelSearch the {@link HotelSearch} to be stored
     * @return an ID of the stores {@link HotelSearch}
     */
    String storeSearch(HotelSearch hotelSearch);

    /**
     * Gets the number of {@link HotelSearch} that are equals to the {@link HotelSearch} with the given ID
     *
     * @param searchId the ID of an {@link HotelSearch}
     * @return {@link HotelSearchCountResponse} with the number of {@link HotelSearch} equals to the {@link HotelSearch}
     *         with the given ID
     */
    HotelSearchCountResponse getHotelSearchCount(String searchId);
}
