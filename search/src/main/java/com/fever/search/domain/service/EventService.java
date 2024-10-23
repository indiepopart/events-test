package com.fever.search.domain.service;

import com.fever.search.domain.BaseEvent;

import java.util.Date;
import java.util.List;

public interface EventService {


    List<BaseEvent> findEventsByStartAndEndDate(Date startsAt, Date endsAt);
}
