package com.fever.fetch.model;

import com.fever.fetch.service.BaseEvent;
import com.fever.fetch.service.Event;
import com.fever.fetch.service.Zone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Optional;


@Component
public class BaseEventConverter implements Converter<BaseEvent, EventDocument> {

    private static Logger logger = LoggerFactory.getLogger(BaseEventConverter.class);
    private XMLGregorianCalendarConverter dateConverter;

    public BaseEventConverter(XMLGregorianCalendarConverter xmlGregorianCalendarConverter) {
        this.dateConverter = xmlGregorianCalendarConverter;
    }

    @Override
    public EventDocument convert(BaseEvent source) {
        logger.debug("Converting BaseEvent to EventDocument {}", source);
        EventDocument document = new EventDocument();
        document.setId(source.getBaseEventId());
        document.setTitle(source.getTitle());
        Event event = source.getEvent();
        Date startDate = dateConverter.convert(event.getStartDate());
        Date endDate = dateConverter.convert(event.getEndDate());
        document.setEventStartDate(startDate);
        document.setEventEndDate(endDate);
        if (!event.getZones().isEmpty()) {
            Optional<Zone> maxPriceZone = event.getZones().stream().max((z1, z2) -> z1.getPrice().compareTo(z2.getPrice()));
            Optional<Zone> minPriceZone = event.getZones().stream().min((z1, z2) -> z1.getPrice().compareTo(z2.getPrice()));
            document.setMaxPrice(maxPriceZone.get().getPrice());
            document.setMinPrice(minPriceZone.get().getPrice());
        }
        return document;
    }
}
