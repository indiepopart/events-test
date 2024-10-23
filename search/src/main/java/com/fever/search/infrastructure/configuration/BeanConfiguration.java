package com.fever.search.infrastructure.configuration;

import com.fever.search.domain.repository.EventRepository;
import com.fever.search.domain.service.BaseEventService;
import com.fever.search.domain.service.EventService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    EventService eventService(EventRepository eventRepository) {
        return new BaseEventService(eventRepository);
    }
}
