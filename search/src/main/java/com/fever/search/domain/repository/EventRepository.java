package com.fever.search.domain.repository;

import com.fever.search.domain.BaseEvent;

import java.util.Date;
import java.util.List;

public interface EventRepository {

    List<BaseEvent> findEventsByStartAndEndDate(Date startDate, Date endDate);
}
