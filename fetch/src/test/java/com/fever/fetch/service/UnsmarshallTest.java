package com.fever.fetch.service;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import org.junit.jupiter.api.Test;

import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;


public class UnsmarshallTest {

    @Test
    public void testUnsmarshall() throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(EventList.class);
        InputStream stream = this.getClass().getClassLoader().getResourceAsStream("provider.xml");
        EventList eventList = (EventList) context.createUnmarshaller().unmarshal(stream);
        assertThat(eventList).isNotNull();
        // Third element invalid date is parsed as null
        BaseEvent event = eventList.getOutput().get(2);
        assertNull(event.getEvent().getStartDate());
        assertNull(event.getEvent().getEndDate());
        assertNull(event.getEvent().getSellTo());
    }
}
