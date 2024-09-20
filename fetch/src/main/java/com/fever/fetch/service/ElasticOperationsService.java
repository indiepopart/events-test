package com.fever.fetch.service;

import com.fever.fetch.model.EventDocument;
import com.fever.fetch.model.BaseEventConverter;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ElasticOperationsService {

    private BaseEventConverter eventDocumentConverter;
    private ElasticsearchOperations elasticSearchOperations;

    public ElasticOperationsService(BaseEventConverter eventDocumentConverter, ElasticsearchOperations elasticSearchOperations) {
        this.eventDocumentConverter = eventDocumentConverter;
        this.elasticSearchOperations = elasticSearchOperations;
    }

    public void save(List<BaseEvent> events) {
        List<EventDocument> documents = events.stream().map(eventDocumentConverter::convert).toList();
        elasticSearchOperations.save(documents);
    }
}
