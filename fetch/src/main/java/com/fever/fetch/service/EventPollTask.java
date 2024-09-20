package com.fever.fetch.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class EventPollTask {

    private static Logger logger = LoggerFactory.getLogger(EventPollTask.class);
    private final EventListService eventListService;

    private final ElasticOperationsService elasticOperationsService;

    public EventPollTask(EventListService eventListService, ElasticOperationsService elasticOperationsService) {
        this.eventListService = eventListService;
        this.elasticOperationsService = elasticOperationsService;
    }

    @Scheduled(fixedDelayString = "${fever.fetch.fixed-delay.in.milliseconds}")
    public void pollEvents() {
        logger.info("Polling events");
        EventList eventList = this.eventListService.pollEvents();
        this.elasticOperationsService.save(eventList.getOutput());
    }
}
