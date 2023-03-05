package com.mindata.hotelsearches.repository;

import com.mindata.hotelsearches.model.Search;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Mongo Repository of Hotel Searches
 *
 * @since 1.0.0
 */
public interface HotelSearchRepository extends MongoRepository<Search, String> {
}
