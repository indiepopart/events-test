package com.fever.search;

import com.fever.search.web.SearchEventsController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class SearchEventsIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

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
