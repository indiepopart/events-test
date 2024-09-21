package com.fever.search.web;

import com.fever.search.model.EventSummary;
import com.fever.search.service.ElasticsearchService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SearchEventsController.class)
public class SearchEventsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ElasticsearchService searchService;

    @Test
    public void testSearchEvents() throws Exception {
        List<EventSummary> eventSummaryList = new ArrayList<>();
        EventSummary eventSummary = new EventSummary();
        eventSummary.setId("123e4567-e89b-12d3-a456-426614174000");
        eventSummary.setTitle("Test Event");
        eventSummary.setStartDate("2021-01-01");
        eventSummary.setStartTime("10:30:05");
        eventSummary.setEndDate("2021-02-01");
        eventSummary.setEndTime("12:30:15");
        eventSummary.setMinPrice(10.0);
        eventSummary.setMaxPrice(100.0);
        eventSummaryList.add(eventSummary);

        given(searchService.searchEvents(any(), any())).willReturn(eventSummaryList);

        // @formatter:off
        this.mockMvc.perform(get("/search")
                .param("starts_at", "2021-01-01T00:00:00Z")
                .param("ends_at", "2021-12-31T23:59:59Z"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.events").isArray())
                .andExpect(jsonPath("$.data.events", hasSize(1)))
                .andExpect(jsonPath("$.data.events[0].id").value("123e4567-e89b-12d3-a456-426614174000"))
                .andExpect(jsonPath("$.data.events[0].title").value("Test Event"))
                .andExpect(jsonPath("$.data.events[0].start_date").value("2021-01-01"))
                .andExpect(jsonPath("$.data.events[0].start_time").value("10:30:05"))
                .andExpect(jsonPath("$.data.events[0].end_date").value("2021-02-01"))
                .andExpect(jsonPath("$.data.events[0].end_time").value("12:30:15"))
                .andExpect(jsonPath("$.data.events[0].min_price").value("10.0"))
                .andExpect(jsonPath("$.data.events[0].max_price").value("100.0"));
        // @formatter:on
    }

    @Test
    public void testSearch_missingParams() throws Exception {
        this.mockMvc.perform(get("/search")
                        .param("starts_at", "2021-01-01T00:00:00Z"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").isNotEmpty())
                .andExpect(jsonPath("$.error.code").isNotEmpty())
                .andExpect(jsonPath("$.error.message").isNotEmpty());
    }

    @Test
    public void testSearch_paramTypeMissmatch() throws Exception {
        this.mockMvc.perform(get("/search")
                        .param("starts_at", "2021-01-01 00:00:00")
                        .param("ends_at", "2021-12-31 23:59:59"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").isNotEmpty())
                .andExpect(jsonPath("$.error.code").isNotEmpty())
                .andExpect(jsonPath("$.error.message").isNotEmpty());;
    }

    @Test
    public void testSearch_unknownError() throws Exception {

        given(searchService.searchEvents(any(), any())).willThrow(new RuntimeException("Unknown error"));

        this.mockMvc.perform(get("/search")
                .param("starts_at", "2021-01-01T00:00:00Z")
                .param("ends_at", "2021-12-31T23:59:59Z"))
                .andExpect(status().isInternalServerError());
    }
}
