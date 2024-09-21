package com.fever.fetch.service;

import com.fever.fetch.model.EventList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class EventListService {

    private static final Logger logger = LoggerFactory.getLogger(EventListService.class);

    private final RestClient restClient;

    public EventListService(RestClient.Builder restClientBuilder, EventListServiceConfig configuration) {
        this.restClient = restClientBuilder.baseUrl(configuration.getBaseUrl()).build();
    }

    public EventList getEvents() {
        ResponseEntity<EventList> response = this.restClient.get().uri("/api/events").retrieve().toEntity(EventList.class);
        return response.getBody();
    }
}
