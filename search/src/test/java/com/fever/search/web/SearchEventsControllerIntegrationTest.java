package com.fever.search.web;

import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.HostConfig;
import com.github.dockerjava.api.model.PortBinding;
import com.github.dockerjava.api.model.Ports;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.elasticsearch.ElasticsearchContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.Duration;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
@ActiveProfiles("integration-test")
public class SearchEventsControllerIntegrationTest {

    @Container
    @ServiceConnection
    static ElasticsearchContainer elasticsearchContainer = new ElasticsearchContainer("elasticsearch:8.15.1").withExposedPorts(9200).withEnv("discovery.type", "single-node").withEnv("xpack.security.enabled", "false").withStartupTimeout(Duration.ofSeconds(60)).withCreateContainerCmdModifier(cmd -> cmd.withHostConfig(new HostConfig().withPortBindings(new PortBinding(Ports.Binding.bindPort(9200), new ExposedPort(9200)))));

    @Autowired
    private MockMvc mockMvc;
    ;

    @Test
    public void testSearchEvents() throws Exception {

        // @formatter:off
        this.mockMvc.perform(get("/search")
                        .param("starts_at", "2017-01-01T00:00:00Z")
                        .param("ends_at", "2025-12-31T23:59:59Z"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data", hasSize(3)));
        // @formatter:on
    }

    @Test
    public void testSearchEventSameDateRange() throws Exception {

        // @formatter:off
        this.mockMvc.perform(get("/search")
                        .param("starts_at", "2021-02-01T10:30:05Z")
                        .param("ends_at", "2021-02-01T12:30:09Z"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data", hasSize(1)));
        // @formatter:on
    }
}
