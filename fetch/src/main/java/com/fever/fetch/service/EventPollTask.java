package com.fever.fetch.service;

import com.fever.fetch.model.BaseEventConverter;
import com.fever.fetch.model.EventDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.validation.Validator;

import java.util.List;

@Component
public class EventPollTask {

    private static Logger logger = LoggerFactory.getLogger(EventPollTask.class);
    private final EventListService eventListService;

    private final ElasticOperationsService elasticOperationsService;

    private final BaseEventConverter eventDocumentConverter;

    private Validator validator;

    public EventPollTask(EventListService eventListService, ElasticOperationsService elasticOperationsService, BaseEventConverter eventDocumentConverter) {
        this.eventListService = eventListService;
        this.elasticOperationsService = elasticOperationsService;
        this.eventDocumentConverter = eventDocumentConverter;
    }

    @Scheduled(fixedDelayString = "${fever.fetch.fixed-delay.in.milliseconds}")
    public void fetch() {
        logger.info("Polling events");
        EventList eventList = this.eventListService.getEvents();

        List<EventDocument> documents = eventList.getOutput().stream()
                .filter(event -> event.getSellMode().equals(BaseEvent.SELL_MODE_OFFLINE))
                .map(eventDocumentConverter::convert).toList();

        this.elasticOperationsService.save(documents);
    }
}
