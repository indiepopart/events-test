package com.fever.fetch.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class EventListService {

    private static final Logger logger = LoggerFactory.getLogger(EventListService.class);

    private final RestClient restClient;

    public EventListService(RestClient.Builder restClientBuilder, EventListServiceConfiguration configuration) {
        this.restClient = restClientBuilder.baseUrl(configuration.getBaseUrl()).requestFactory(new HttpComponentsClientHttpRequestFactory())
                .build();
    }

    public EventList getEvents() {
        ResponseEntity<EventList> response = this.restClient.get().uri("/api/events").retrieve().toEntity(EventList.class);
        return response.getBody();
    }
}
