package com.mindata.hotelsearches.service;

import com.mindata.hotelsearches.model.Search;
import com.mindata.hotelsearches.model.HotelSearch;
import com.mindata.hotelsearches.model.response.HotelSearchCountResponse;
import com.mindata.hotelsearches.repository.HotelSearchRepository;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import static java.util.UUID.randomUUID;
import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.data.mongodb.core.query.Criteria.where;

/**
 * Default implementation of {@link SearchService}
 *
 * @since 1.0.0
 */
@Service
public class DefaultSearchService implements SearchService {

    private static final Logger logger = getLogger(DefaultSearchService.class);

    private final KafkaTemplate<String, Search> kafkaTemplate;

    private final MongoTemplate mongoTemplate;

    private final HotelSearchRepository hotelSearchRepository;

    private final String topicName;

    public DefaultSearchService(@Value("${hotelsearches.kafka.topic-name}") String topicName,
                                @Autowired HotelSearchRepository hotelSearchRepository,
                                @Autowired KafkaTemplate<String, Search> kafkaTemplate,
                                @Autowired MongoTemplate mongoTemplate) {
        this.topicName = topicName;
        this.hotelSearchRepository = hotelSearchRepository;
        this.kafkaTemplate = kafkaTemplate;
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public String storeSearch(HotelSearch hotelSearch) {
        Search search = new Search();
        search.set_id(randomUUID().toString());
        search.setHotelSearch(hotelSearch);

        CompletableFuture<SendResult<String, Search>> future = kafkaTemplate.send(topicName, search);
        future.thenAccept((result) -> logger.info("Sent message=[{}] with offset=[{}]", search, result.getRecordMetadata().offset()));

        return search.get_id();
    }

    @Override
    public HotelSearchCountResponse getHotelSearchCount(String searchId) {
        Optional<Search> search = hotelSearchRepository.findById(searchId);

        if (search.isEmpty()) {
            return new HotelSearchCountResponse(searchId, null, 0);
        }

        Query query = new Query();
        query.addCriteria(where("hotelSearch.hotelId").is(search.get().getHotelSearch().hotelId()))
                .addCriteria(where("hotelSearch.checkIn").is(search.get().getHotelSearch().checkIn().getTime()))
                .addCriteria(where("hotelSearch.checkOut").is(search.get().getHotelSearch().checkOut().getTime()))
                .addCriteria(where("hotelSearch.ages").is(search.get().getHotelSearch().ages()));

        List<Search> searches = mongoTemplate.find(query, Search.class);

        return new HotelSearchCountResponse(searchId, search.get().getHotelSearch(), searches.size());
    }
}
