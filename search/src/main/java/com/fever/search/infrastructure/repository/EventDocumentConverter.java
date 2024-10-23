package com.fever.search.infrastructure.repository;

import com.fever.search.domain.BaseEvent;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.TimeZone;

@Component
public class EventDocumentConverter implements Converter<EventDocument, BaseEvent> {

    private SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
    private SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm:ss");


    public EventDocumentConverter() {
        dateFormatter.setTimeZone(TimeZone.getTimeZone("UTC"));
        timeFormatter.setTimeZone(TimeZone.getTimeZone("UTC"));
    }

    @Override
    public BaseEvent convert(EventDocument source) {
        BaseEvent summary = new BaseEvent();
        summary.setId(source.getId());
        summary.setTitle(source.getTitle());

        summary.setStartDate(source.getEventStartDate());
        summary.setEndDate(source.getEventEndDate());

        summary.setMinPrice(source.getMinPrice());
        summary.setMaxPrice(source.getMaxPrice());
        return summary;
    }
}
