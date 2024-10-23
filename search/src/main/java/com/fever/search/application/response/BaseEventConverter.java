package com.fever.search.application.response;

import com.fever.search.application.response.EventSummaryDTO;
import com.fever.search.domain.BaseEvent;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.TimeZone;

@Component
public class BaseEventConverter implements Converter<BaseEvent, EventSummaryDTO> {

    private SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
    private SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm:ss");


    public BaseEventConverter() {
        dateFormatter.setTimeZone(TimeZone.getTimeZone("UTC"));
        timeFormatter.setTimeZone(TimeZone.getTimeZone("UTC"));
    }

    @Override
    public EventSummaryDTO convert(BaseEvent source) {
        EventSummaryDTO summary = new EventSummaryDTO();
        summary.setId(source.getId());
        summary.setTitle(source.getTitle());

        summary.setStartDate(dateFormatter.format(source.getStartDate()));
        summary.setStartTime(timeFormatter.format(source.getStartDate()));
        summary.setEndDate(dateFormatter.format(source.getEndDate()));
        summary.setEndTime(timeFormatter.format(source.getEndDate()));

        summary.setMinPrice(source.getMinPrice());
        summary.setMaxPrice(source.getMaxPrice());
        return summary;
    }
}
