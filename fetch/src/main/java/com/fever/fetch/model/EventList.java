package com.fever.fetch.model;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlElements;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.List;

@XmlRootElement(name = "eventList")
public class EventList {

    private List<BaseEvent> output;

    public List<BaseEvent> getOutput() {
        return output;
    }

    @XmlElementWrapper(name = "output")
    @XmlElements(@XmlElement(name = "base_event", type = BaseEvent.class))
    public void setOutput(List<BaseEvent> output) {
        this.output = output;
    }
}
