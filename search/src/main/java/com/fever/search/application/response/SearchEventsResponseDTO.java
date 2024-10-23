package com.fever.search.application.response;

import com.fever.search.application.response.EventSummaryDTO;

import java.util.List;

public class SearchEventsResponseDTO {

    private SearchData data;
    private SearchError error;

    public SearchEventsResponseDTO() {
    }

    public SearchEventsResponseDTO(SearchError error) {
        this.error = error;
    }

    public SearchEventsResponseDTO(List<EventSummaryDTO> events) {
        this.data = new SearchData(events);
    }

    public SearchData getData() {
        return data;
    }

    public void setData(SearchData data) {
        this.data = data;
    }

    public SearchError getError() {
        return error;
    }

    public void setError(SearchError error) {
        this.error = error;
    }

    public static class SearchData {

        private List<EventSummaryDTO> events;

        public SearchData(List<EventSummaryDTO> events) {
            this.events = events;
        }

        public List<EventSummaryDTO> getEvents() {
            return events;
        }

        public void setEvents(List<EventSummaryDTO> events) {
            this.events = events;
        }
    }

    public static class SearchError {

        private String code;
        private String message;

        public SearchError() {
        }

        public SearchError(String code, String message) {
            this.code = code;
            this.message = message;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
