package com.mindata.hotelsearches.service;

import com.mindata.hotelsearches.model.HotelSearch;
import com.mindata.hotelsearches.model.response.HotelSearchCountResponse;

/**
 * Service to store and retrieve count of Hotel Searches.
 *
 * @since 1.0.0
 */
public interface SearchService {

    String storeSearch(HotelSearch hotelSearch);

    HotelSearchCountResponse getHotelSearchCount(String searchId);
}
