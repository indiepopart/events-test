package com.fever.fetch.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EventListServiceIntegtrationTest {

    @Autowired
    private EventListService service;

    @Test
    public void testPollEvents() {
        service.pollEvents();
    }
}
