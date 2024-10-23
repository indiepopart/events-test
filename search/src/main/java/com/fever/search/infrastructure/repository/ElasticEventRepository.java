package com.fever.search.infrastructure.repository;

import com.fever.search.domain.BaseEvent;
import com.fever.search.domain.repository.EventRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class ElasticEventRepository implements EventRepository {

    private static Logger logger = LoggerFactory.getLogger(ElasticEventRepository.class);


    // Wraps SpringData repository
    private EventDocumentConverter eventDocumentConverter;
    private ElasticsearchOperations elasticSearchOperations;

    public ElasticEventRepository(EventDocumentConverter eventDocumentConverter, ElasticsearchOperations elasticSearchOperations) {
        this.eventDocumentConverter = eventDocumentConverter;
        this.elasticSearchOperations = elasticSearchOperations;
    }

    @Override
    public List<BaseEvent> findEventsByStartAndEndDate(Date startsAt, Date endsAt) {
        // @formatter:off

        Criteria criteria = new Criteria("eventStartDate")
                .greaterThanEqual(startsAt)
                .lessThanEqual(endsAt)
                .and("eventEndDate")
                .greaterThanEqual(startsAt)
                .lessThanEqual(endsAt);
        // @formatter:on
        Query query = new CriteriaQuery(criteria);
        SearchHits<EventDocument> searchHits = elasticSearchOperations.search(query, EventDocument.class);
        logger.debug("Found {} events", searchHits.getTotalHits());
        return searchHits.stream().map(searchHit -> eventDocumentConverter.convert(searchHit.getContent())).toList();
    }
}
