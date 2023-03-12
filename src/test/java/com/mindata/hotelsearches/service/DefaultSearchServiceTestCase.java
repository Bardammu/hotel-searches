package com.mindata.hotelsearches.service;

import com.mindata.hotelsearches.model.Search;
import com.mindata.hotelsearches.model.HotelSearch;
import com.mindata.hotelsearches.model.response.HotelSearchCountResponse;
import com.mindata.hotelsearches.repository.HotelSearchRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static java.util.Optional.of;
import static java.util.UUID.randomUUID;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@TestInstance(PER_CLASS)
public class DefaultSearchServiceTestCase {

    @Mock
    private HotelSearchRepository hotelSearchRepository;

    @Mock
    private KafkaTemplate<String, Search> kafkaTemplate;

    @Mock
    private MongoTemplate mongoTemplate;

    private HotelSearch hotelSearch;

    private DefaultSearchService defaultSearchService;

    @BeforeAll
    public void setUp() {
        defaultSearchService = new DefaultSearchService("topicName" ,hotelSearchRepository, kafkaTemplate, mongoTemplate);

        hotelSearch = new HotelSearch("hotelId", Date.valueOf("2023-10-30"), Date.valueOf("2023-11-30"), List.of(29, 30));
        Search search1 = new Search("id1", hotelSearch);
        Search search2 = new Search("id2", hotelSearch);
        CompletableFuture completableFuture = mock(CompletableFuture.class);

        when(kafkaTemplate.send(any(String.class), any(Search.class))).thenReturn(completableFuture);
        when(mongoTemplate.find(any(), eq(Search.class))).thenReturn(List.of(search1, search2));
        when(hotelSearchRepository.findById("id1")).thenReturn(of(search1));
    }

    @Test
    public void storeNewSearch() {
        HotelSearch hotelSearch = new HotelSearch("hotelID", Date.valueOf("2023-10-01"), Date.valueOf("2023-11-01"), List.of(31, 32));
        String searchId = defaultSearchService.storeSearch(hotelSearch);

        assertThat(searchId, not(is(nullValue())));
    }

    @Test
    public void getHotelSearchesCount() {
        HotelSearchCountResponse hotelSearchCountResponse = defaultSearchService.getHotelSearchCount("id1");

        assertThat(hotelSearchCountResponse.count(), is(2));
        assertThat(hotelSearchCountResponse.search(), is(hotelSearch));
    }

    @Test
    public void getHotelSearchesCountWithUnknownSearchId() {
        HotelSearchCountResponse hotelSearchCountResponse = defaultSearchService.getHotelSearchCount(randomUUID().toString());

        assertThat(hotelSearchCountResponse.count(), is(0));
    }

}
