package com.fever.fetch.service;

import com.fever.fetch.model.EventList;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
public class EventListServiceIntegtrationTest {

    @Autowired
    private EventListService service;

    @Test
    public void testGetEvents() {

        EventList eventList = service.getEvents();
        assertThat(eventList).isNotNull();
    }
}
