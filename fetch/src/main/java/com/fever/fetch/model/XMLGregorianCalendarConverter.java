package com.fever.fetch.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import javax.xml.datatype.XMLGregorianCalendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

@Component
public class XMLGregorianCalendarConverter implements Converter<XMLGregorianCalendar, Date> {

    private static Logger logger = LoggerFactory.getLogger(XMLGregorianCalendarConverter.class);

    @Override
    public Date convert(XMLGregorianCalendar source) {
        if (source == null){
            logger.warn("XMLGregorianCalendar is null {}", source);
            return null;
        }
        GregorianCalendar calendar = source.toGregorianCalendar();
        calendar.setTimeZone(TimeZone.getTimeZone("UTC"));
        return calendar.getTime();
    }
}
