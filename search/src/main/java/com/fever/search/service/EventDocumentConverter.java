package com.fever.search.service;

import com.fever.search.model.EventSummary;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.TimeZone;

@Component
public class EventDocumentConverter implements Converter<EventDocument, EventSummary> {

    private SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
    private SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm:ss");


    public EventDocumentConverter() {
        dateFormatter.setTimeZone(TimeZone.getTimeZone("UTC"));
        timeFormatter.setTimeZone(TimeZone.getTimeZone("UTC"));
    }

    @Override
    public EventSummary convert(EventDocument source) {
        EventSummary summary = new EventSummary();
        summary.setId(source.getId());
        summary.setTitle(source.getTitle());

        summary.setStartDate(dateFormatter.format(source.getEventStartDate()));
        summary.setStartTime(timeFormatter.format(source.getEventStartDate()));
        summary.setEndDate(dateFormatter.format(source.getEventEndDate()));
        summary.setEndTime(timeFormatter.format(source.getEventEndDate()));

        summary.setMinPrice(source.getMinPrice());
        summary.setMaxPrice(source.getMaxPrice());
        return summary;
    }
}
