package com.mindata.hotelsearches.model.response;

import com.mindata.hotelsearches.model.HotelSearch;

public record HotelSearchCountResponse(String searchId, HotelSearch search, Integer count) {
}
