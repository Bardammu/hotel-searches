package com.mindata.hotelsearches.integration;

import com.mindata.hotelsearches.model.HotelSearch;
import com.mindata.hotelsearches.model.response.HotelSearchCountResponse;
import com.mindata.hotelsearches.model.response.HotelSearchPostResponse;
import com.mindata.hotelsearches.model.response.SimpleMessageResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Objects;

import static java.sql.Date.valueOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_PROBLEM_JSON;
import static org.springframework.web.util.UriComponentsBuilder.fromHttpUrl;

@SpringBootTest(webEnvironment = RANDOM_PORT)
public class SearchesITCase {

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    private String HOST;

    @BeforeEach
    public void setUp() {
        HOST = "http://localhost:" + port;
    }

    @Test
    public void postNewSearch() {
        String URL = HOST + "/search";
        HotelSearch hotelSearch = new HotelSearch( "9999aBc", valueOf("2023-12-29"), valueOf("2023-12-31"), List.of(30, 29, 1 ,3));

        HttpEntity<HotelSearch> requestSuperheroEntity = new HttpEntity<>(hotelSearch);
        ResponseEntity<HotelSearchPostResponse> responseEntity = restTemplate.postForEntity(URL, requestSuperheroEntity , HotelSearchPostResponse.class);

        assertThat(responseEntity.getStatusCode(), is(CREATED));
        assertThat(responseEntity.getHeaders().getContentType(), is(APPLICATION_JSON));
    }

    @Test
    public void getCountSearch() {
        String URL = HOST + "/count";
        String urlTemplate = fromHttpUrl(URL)
                .queryParam("searchId", "c1b7a472-3c33-4385-81ee-ac20dc5c871c")
                .encode()
                .toUriString();
        ResponseEntity<HotelSearchCountResponse> responseEntity = restTemplate.getForEntity(urlTemplate, HotelSearchCountResponse.class);

        assertThat(responseEntity.getStatusCode(), is(OK));
        assertThat(Objects.requireNonNull(responseEntity.getBody()).count(), is(2));
    }

    @Test
    public void postNewSearchMissingHotelId() {
        String URL = HOST + "/search";
        HotelSearch hotelSearch = new HotelSearch( null, valueOf("2023-12-29"), valueOf("2023-12-31"), List.of(30, 29, 1 ,3));

        HttpEntity<HotelSearch> requestSuperheroEntity = new HttpEntity<>(hotelSearch);
        ResponseEntity<SimpleMessageResponse> responseEntity = restTemplate.postForEntity(URL, requestSuperheroEntity , SimpleMessageResponse.class);

        assertThat(responseEntity.getStatusCode(), is(BAD_REQUEST));
        assertThat(responseEntity.getHeaders().getContentType(), is(APPLICATION_PROBLEM_JSON));
        assertThat(responseEntity.getBody().message(), is("Hotel Id should not be null or blank"));
    }

    @Test
    public void postNewSearchWithCheckInInThePast() {
        String URL = HOST + "/search";
        HotelSearch hotelSearch = new HotelSearch( "9999aBd", valueOf("2020-12-29"), valueOf("2023-12-31"), List.of(30, 29, 1 ,3));

        HttpEntity<HotelSearch> requestSuperheroEntity = new HttpEntity<>(hotelSearch);
        ResponseEntity<SimpleMessageResponse> responseEntity = restTemplate.postForEntity(URL, requestSuperheroEntity , SimpleMessageResponse.class);

        assertThat(responseEntity.getStatusCode(), is(BAD_REQUEST));
        assertThat(responseEntity.getHeaders().getContentType(), is(APPLICATION_PROBLEM_JSON));
        assertThat(responseEntity.getBody().message(), is("Check-in should not be in the past"));
    }

    @Test
    public void postNewSearchWithCheckOutThePast() {
        String URL = HOST + "/search";
        HotelSearch hotelSearch = new HotelSearch( "9999aBd", valueOf("2023-12-29"), valueOf("2020-12-31"), List.of(30, 29, 1 ,3));

        HttpEntity<HotelSearch> requestSuperheroEntity = new HttpEntity<>(hotelSearch);
        ResponseEntity<SimpleMessageResponse> responseEntity = restTemplate.postForEntity(URL, requestSuperheroEntity , SimpleMessageResponse.class);

        assertThat(responseEntity.getStatusCode(), is(BAD_REQUEST));
        assertThat(responseEntity.getHeaders().getContentType(), is(APPLICATION_PROBLEM_JSON));
        assertThat(responseEntity.getBody().message(), is("Check-out should not be in the past"));
    }

    @Test
    public void postNewSearchWithNegativeAges() {
        String URL = HOST + "/search";
        HotelSearch hotelSearch = new HotelSearch( "9999aBd", valueOf("2023-12-29"), valueOf("2023-12-31"), List.of(-1, -2));

        HttpEntity<HotelSearch> requestSuperheroEntity = new HttpEntity<>(hotelSearch);
        ResponseEntity<SimpleMessageResponse> responseEntity = restTemplate.postForEntity(URL, requestSuperheroEntity , SimpleMessageResponse.class);

        assertThat(responseEntity.getStatusCode(), is(BAD_REQUEST));
        assertThat(responseEntity.getHeaders().getContentType(), is(APPLICATION_PROBLEM_JSON));
        assertThat(responseEntity.getBody().message(), is("Ages should not be negative"));
    }

    @Test
    public void postNewSearchWithEmptyAgesList() {
        String URL = HOST + "/search";
        HotelSearch hotelSearch = new HotelSearch( "9999aBd", valueOf("2023-12-29"), valueOf("2023-12-31"), List.of());

        HttpEntity<HotelSearch> requestSuperheroEntity = new HttpEntity<>(hotelSearch);
        ResponseEntity<SimpleMessageResponse> responseEntity = restTemplate.postForEntity(URL, requestSuperheroEntity , SimpleMessageResponse.class);

        assertThat(responseEntity.getStatusCode(), is(BAD_REQUEST));
        assertThat(responseEntity.getHeaders().getContentType(), is(APPLICATION_PROBLEM_JSON));
        assertThat(responseEntity.getBody().message(), is("Ages should not be null or blank"));
    }

    @Test
    public void postNewSearchWithCheckOutBeforeCheckIn() {
        String URL = HOST + "/search";
        HotelSearch hotelSearch = new HotelSearch( "9999aBd", valueOf("2023-12-31"), valueOf("2023-12-29"), List.of(29, 39));

        HttpEntity<HotelSearch> requestSuperheroEntity = new HttpEntity<>(hotelSearch);
        ResponseEntity<SimpleMessageResponse> responseEntity = restTemplate.postForEntity(URL, requestSuperheroEntity , SimpleMessageResponse.class);

        assertThat(responseEntity.getStatusCode(), is(BAD_REQUEST));
        assertThat(responseEntity.getHeaders().getContentType(), is(APPLICATION_PROBLEM_JSON));
        assertThat(responseEntity.getBody().message(), is("Check-In should happen before check-out"));
    }
}
