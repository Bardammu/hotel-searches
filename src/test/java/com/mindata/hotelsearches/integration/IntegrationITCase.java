package com.mindata.hotelsearches.integration;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@TestInstance(PER_CLASS)
public abstract class IntegrationITCase {

    @Autowired
    protected TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    protected String HOST;
    protected String URL_HOTEL_SEARCH;
    protected String URL_HOTEL_COUNT;

    @BeforeAll
    public void setUp() {
        HOST = "http://localhost:" + port;
        URL_HOTEL_SEARCH = HOST + "/search";
        URL_HOTEL_COUNT = HOST + "/count";
    }
}
