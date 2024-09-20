package com.fever.fetch.service;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;

public class BaseEvent {

    public static final String SELL_MODE_OFFLINE = "offline";

    private String baseEventId;
    private String sellMode;
    private String title;

    private Event event;

    public String getBaseEventId() {
        return baseEventId;
    }

    @XmlAttribute(name = "base_event_id")
    public void setBaseEventId(String baseEventId) {
        this.baseEventId = baseEventId;
    }

    public String getSellMode() {
        return sellMode;
    }

    @XmlAttribute(name = "sell_mode")
    public void setSellMode(String sellMode) {
        this.sellMode = sellMode;
    }

    public String getTitle() {
        return title;
    }

    @XmlAttribute(name = "title")
    public void setTitle(String title) {
        this.title = title;
    }

    public Event getEvent() {
        return event;
    }

    @XmlElement(name = "event")
    public void setEvent(Event event) {
        this.event = event;
    }
}
