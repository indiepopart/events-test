package com.fever.search;


import com.fever.search.service.EventDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.GregorianCalendar;
import java.util.TimeZone;

@Component
public class InsertEventsCommand implements CommandLineRunner {

    private Logger logger = LoggerFactory.getLogger(InsertEventsCommand.class);

    private ElasticsearchOperations elasticsearchOperations;

    public InsertEventsCommand(ElasticsearchOperations elasticsearchOperations) {
        this.elasticsearchOperations = elasticsearchOperations;
    }

    @Override
    public void run(String... args) throws Exception {
        logger.debug("Inserting events into Elasticsearch");

        GregorianCalendar calendar = new GregorianCalendar();
        calendar.set(GregorianCalendar.MILLISECOND, 0);
        calendar.setTimeZone(TimeZone.getTimeZone("UTC"));

        EventDocument ed1 = new EventDocument();
        ed1.setId("1");
        ed1.setTitle("Event 1");
        calendar.set(2021,1,1,10,30,5);
        ed1.setEventStartDate(calendar.getTime());
        calendar.set(2021,1,1,12,30,9);
        ed1.setEventEndDate(calendar.getTime());
        ed1.setMinPrice(10.0);
        ed1.setMaxPrice(100.0);

        EventDocument ed2 = new EventDocument();
        ed2.setId("2");
        ed2.setTitle("Los Angeles Lakers vs. Golden State Warriors");
        calendar.set(2019,1,1,22,30,5);
        ed2.setEventStartDate(calendar.getTime());
        calendar.set(2019,1,1,23,30,5);
        ed2.setEventEndDate(calendar.getTime());
        ed2.setMinPrice(10.0);
        ed2.setMaxPrice(100.0);

        EventDocument ed3 = new EventDocument();
        ed3.setId("3");
        ed3.setTitle("Las Vegas Raiders vs. San Francisco 49ers");
        calendar.set(2024,11,1,22,30,5);
        ed3.setEventStartDate(calendar.getTime());
        calendar.set(2024,11,7,23,30,5);
        ed3.setEventEndDate(calendar.getTime());
        ed3.setMinPrice(10.0);
        ed3.setMaxPrice(100.0);

        elasticsearchOperations.save(ed1);
        elasticsearchOperations.save(ed2);
        elasticsearchOperations.save(ed3);

        EventDocument document = elasticsearchOperations.get("1", EventDocument.class);
        Assert.notNull(document, "Document not found");

        logger.debug("Events inserted");
    }
}
