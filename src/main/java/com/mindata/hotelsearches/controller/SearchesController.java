package com.mindata.hotelsearches.controller;

import com.mindata.hotelsearches.model.HotelSearch;
import com.mindata.hotelsearches.model.response.HotelSearchCountResponse;
import com.mindata.hotelsearches.model.response.HotelSearchPostResponse;
import com.mindata.hotelsearches.service.DefaultSearchService;
import com.mindata.hotelsearches.service.SearchService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.http.ResponseEntity.status;

@RestController
public class SearchesController {

    private final SearchService searchService;

    public SearchesController(@Autowired DefaultSearchService searchService) {
        this.searchService = searchService;
    }

    @PostMapping(path = "/search", consumes = { APPLICATION_JSON_VALUE }, produces = { APPLICATION_JSON_VALUE })
    public ResponseEntity<HotelSearchPostResponse> postSearch(@Valid @RequestBody HotelSearch hotelSearch) {
        String uuid = searchService.storeSearch(hotelSearch);
        return status(CREATED).body(new HotelSearchPostResponse(uuid));
    }

    @GetMapping(path = "/count", produces = { APPLICATION_JSON_VALUE })
    public ResponseEntity<HotelSearchCountResponse> getSearchCount(@RequestParam String searchId) {
        HotelSearchCountResponse hotelSearchCountResponse = searchService.getHotelSearchCount(searchId);
        return ok().body(hotelSearchCountResponse);
    }
}
