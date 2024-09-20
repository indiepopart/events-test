package com.fever.search;


import com.fever.search.service.EventDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.io.InputStream;

@Component
@Profile("integration-test")
public class InsertEventsCommand implements CommandLineRunner {

    private Logger logger = LoggerFactory.getLogger(InsertEventsCommand.class);

    private ElasticsearchOperations elasticsearchOperations;

    public InsertEventsCommand(ElasticsearchOperations elasticsearchOperations) {
        this.elasticsearchOperations = elasticsearchOperations;
    }

    @Override
    public void run(String... args) throws Exception {
        logger.debug("Inserting events.json into Elasticsearch");

        InputStream stream = this.getClass().getClassLoader().getResourceAsStream("events.json");
        ObjectMapper objectMapper = new ObjectMapper();
        EventDocument[] documents = objectMapper.readValue(stream, EventDocument[].class);
        elasticsearchOperations.save(documents);

        logger.debug("Events inserted");

        elasticsearchOperations.indexOps(EventDocument.class).refresh();

        EventDocument document = elasticsearchOperations.get("1", EventDocument.class);
        Assert.notNull(document, "Document not found");

    }
}
