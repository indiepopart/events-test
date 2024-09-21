package com.fever.search.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class SearchResponse {

    private SearchData data;
    private SearchError error;

    public SearchResponse() {
    }

    public SearchResponse(SearchError error) {
        this.error = error;
    }

    public SearchResponse(List<EventSummary> events) {
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
}
