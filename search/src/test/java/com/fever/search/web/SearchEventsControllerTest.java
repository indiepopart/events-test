package com.fever.search.web;

import com.fever.search.model.EventSummary;
import com.fever.search.service.SearchService;
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
    private SearchService searchService;

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
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data", hasSize(1)))
                .andExpect(jsonPath("$.data[0].id").value("123e4567-e89b-12d3-a456-426614174000"))
                .andExpect(jsonPath("$.data[0].title").value("Test Event"))
                .andExpect(jsonPath("$.data[0].startDate").value("2021-01-01"))
                .andExpect(jsonPath("$.data[0].startTime").value("10:30:05"))
                .andExpect(jsonPath("$.data[0].endDate").value("2021-02-01"))
                .andExpect(jsonPath("$.data[0].endTime").value("12:30:15"))
                .andExpect(jsonPath("$.data[0].minPrice").value("10.0"))
                .andExpect(jsonPath("$.data[0].maxPrice").value("100.0"));
        // @formatter:on
    }
}
