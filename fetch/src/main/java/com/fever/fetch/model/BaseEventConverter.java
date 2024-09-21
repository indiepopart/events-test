package com.fever.fetch.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.stream.Collectors;


@Component
public class BaseEventConverter implements Converter<BaseEvent, EventDocument> {

    private static Logger logger = LoggerFactory.getLogger(BaseEventConverter.class);
    private XMLGregorianCalendarConverter dateConverter;

    public BaseEventConverter(XMLGregorianCalendarConverter xmlGregorianCalendarConverter) {
        this.dateConverter = xmlGregorianCalendarConverter;
    }

    @Override
    public EventDocument convert(BaseEvent source) {
        logger.debug("Converting BaseEvent to EventDocument {}", source.getBaseEventId());
        EventDocument document = new EventDocument();
        document.setId(source.getBaseEventId());
        document.setTitle(source.getTitle());
        Event event = source.getEvent();
        Date startDate = dateConverter.convert(event.getStartDate());
        Date endDate = dateConverter.convert(event.getEndDate());
        document.setEventStartDate(startDate);
        document.setEventEndDate(endDate);
        List<Zone> zones = event.getZones();
        if (zones == null || zones.isEmpty()) {
            return document;
        }
        DoubleSummaryStatistics statistics = zones.stream().collect(Collectors.summarizingDouble(Zone::getPrice));
        document.setMaxPrice(statistics.getMax());
        document.setMinPrice(statistics.getMin());
        return document;
    }
}
