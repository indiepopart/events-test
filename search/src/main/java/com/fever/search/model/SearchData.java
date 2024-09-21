package com.fever.search.model;

import java.util.List;

public class SearchData {

    private List<EventSummary> events;

    public SearchData(List<EventSummary> events) {
        this.events = events;
    }

    public List<EventSummary> getEvents() {
        return events;
    }

    public void setEvents(List<EventSummary> events) {
        this.events = events;
    }
}
