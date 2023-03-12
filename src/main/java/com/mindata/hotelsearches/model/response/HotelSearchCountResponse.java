package com.mindata.hotelsearches.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.mindata.hotelsearches.model.HotelSearch;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

/**
 * Record that represent a response of hotel search count GET
 *
 * @since 1.0.0
 */
@JsonInclude(NON_NULL)
public record HotelSearchCountResponse(String searchId, HotelSearch search, Integer count) {
}
