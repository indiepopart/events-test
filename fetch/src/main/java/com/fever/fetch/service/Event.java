package com.fever.fetch.service;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElements;
import jakarta.xml.bind.annotation.XmlID;

import javax.xml.datatype.XMLGregorianCalendar;
import java.util.List;


public class Event {

    private String eventId;

    private XMLGregorianCalendar startDate;

    private XMLGregorianCalendar endDate;

    private XMLGregorianCalendar sellFrom;

    private XMLGregorianCalendar sellTo;

    private List<Zone> zones;

    public String getEventId() {
        return eventId;
    }
    @XmlAttribute(name = "event_id")
    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public XMLGregorianCalendar getStartDate() {
        return startDate;
    }

    @XmlAttribute(name = "event_start_date")
    public void setStartDate(XMLGregorianCalendar startDate) {
        this.startDate = startDate;
    }

    public XMLGregorianCalendar getEndDate() {
        return endDate;
    }
    @XmlAttribute(name = "event_end_date")
    public void setEndDate(XMLGregorianCalendar endDate) {
        this.endDate = endDate;
    }

    public XMLGregorianCalendar getSellFrom() {
        return sellFrom;
    }

    @XmlAttribute(name = "sell_from")
    public void setSellFrom(XMLGregorianCalendar sellFrom) {
        this.sellFrom = sellFrom;
    }

    public XMLGregorianCalendar getSellTo() {
        return sellTo;
    }

    @XmlAttribute(name = "sell_to")
    public void setSellTo(XMLGregorianCalendar sellTo) {
        this.sellTo = sellTo;
    }

    public List<Zone> getZones() {
        return zones;
    }

    @XmlElements(@XmlElement(name = "zone", type = Zone.class))
    public void setZones(List<Zone> zones) {
        this.zones = zones;
    }
}
