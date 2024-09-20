package com.fever.search.service;

import com.fever.search.model.EventSummary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SearchService {

    private static Logger logger = LoggerFactory.getLogger(SearchService.class);

    private EventDocumentConverter eventDocumentConverter;
    private ElasticsearchOperations elasticSearchOperations;

    public SearchService(EventDocumentConverter eventDocumentConverter, ElasticsearchOperations elasticSearchOperations) {
        this.eventDocumentConverter = eventDocumentConverter;
        this.elasticSearchOperations = elasticSearchOperations;
    }

    public List<EventSummary> searchEvents(Date startsAt, Date endsAt) {
        // @formatter:off

        Criteria criteria = new Criteria("eventStartDate").greaterThanEqual(startsAt).lessThanEqual(endsAt)
                .and("eventEndDate").greaterThanEqual(startsAt).lessThanEqual(endsAt);
        // @formatter:on
        Query query = new CriteriaQuery(criteria);
        SearchHits<EventDocument> searchHits = elasticSearchOperations.search(query, EventDocument.class);
        logger.debug("Found {} events", searchHits.getTotalHits());
        return searchHits.stream().map(searchHit -> eventDocumentConverter.convert(searchHit.getContent())).toList();
    }
}
