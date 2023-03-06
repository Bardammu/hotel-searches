package com.mindata.hotelsearches.model.response;

import com.mindata.hotelsearches.model.HotelSearch;

/**
 * Record that represent a response of hotel search count GET
 *
 * @since 1.0.0
 */
public record HotelSearchCountResponse(String searchId, HotelSearch search, Integer count) {
}
