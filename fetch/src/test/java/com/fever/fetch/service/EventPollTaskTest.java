package com.fever.fetch.service;

import com.fever.fetch.model.EventDocument;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
public class EventPollTaskTest {

    @Autowired
    private EventPollTask eventPollTask;

    @Autowired
    private ElasticsearchOperations elasticsearchOperations;

    @Test
    public void testFetch() {
        eventPollTask.fetch();

        EventDocument document = elasticsearchOperations.get("291", EventDocument.class);
        assertThat(document).isNotNull();
    }
}
