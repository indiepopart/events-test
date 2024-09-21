package com.fever.search.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class SearchResponse {

    private SearchData data;
    private SearchError error;

    public SearchResponse() {
    }

    public SearchResponse(List<EventSummary> events, SearchError error) {
        this.data = new SearchData(events);
        this.error = error;
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
