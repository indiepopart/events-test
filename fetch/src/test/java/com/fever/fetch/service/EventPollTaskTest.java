package com.fever.fetch.service;

import com.fever.fetch.model.EventDocument;
import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.HostConfig;
import com.github.dockerjava.api.model.PortBinding;
import com.github.dockerjava.api.model.Ports;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.IndexOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.elasticsearch.ElasticsearchContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@Testcontainers
public class EventPollTaskTest {

    // @formatter:off
    @Container
    @ServiceConnection
    static ElasticsearchContainer elasticsearchContainer = new ElasticsearchContainer("elasticsearch:8.15.1")
            .withExposedPorts(9200)
            .withEnv("discovery.type", "single-node")
            .withEnv("xpack.security.enabled", "false")
            .withStartupTimeout(Duration.ofSeconds(60))
            .withCreateContainerCmdModifier(cmd ->
                    cmd.withHostConfig(new HostConfig()
                            .withPortBindings(new PortBinding(Ports.Binding.bindPort(9200), new ExposedPort(9200)))));
    // @formatter:on
    @Autowired
    private EventPollTask eventPollTask;

    @Autowired
    private ElasticsearchOperations elasticsearchOperations;

    @Test
    public void testFetch() {
        eventPollTask.fetch();

        elasticsearchOperations.indexOps(EventDocument.class).refresh();

        Criteria criteria = new Criteria("minPrice").greaterThan(0.0);
        SearchHits<EventDocument> hits = elasticsearchOperations.search(new CriteriaQuery(criteria), EventDocument.class);
        assertThat(hits.getTotalHits()).isGreaterThan(0);
    }
}
