package com.fever.search.domain.service;

import com.fever.search.domain.BaseEvent;
import com.fever.search.domain.repository.EventRepository;

import java.util.Date;
import java.util.List;

public class BaseEventService implements EventService {


    private EventRepository eventRepository;

    public BaseEventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Override
    public List<BaseEvent> findEventsByStartAndEndDate(Date startsAt, Date endsAt) {
        return eventRepository.findEventsByStartAndEndDate(startsAt, endsAt);
    }
}
