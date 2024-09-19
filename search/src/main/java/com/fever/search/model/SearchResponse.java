package com.fever.search.model;

import java.util.List;

public class SearchResponse {

    private List<EventSummary> data;

    private SearchError error;

    public SearchResponse() {
    }

    public SearchResponse(List<EventSummary> data, SearchError error) {
        this.data = data;
        this.error = error;
    }

    public List<EventSummary> getData() {
        return data;
    }

    public void setData(List<EventSummary> data) {
        this.data = data;
    }

    public SearchError getError() {
        return error;
    }

    public void setError(SearchError error) {
        this.error = error;
    }
}
