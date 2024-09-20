package com.fever.fetch.service;

import com.fever.fetch.model.EventDocument;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class ElasticOperationsService {

    private static Logger logger = LoggerFactory.getLogger(ElasticOperationsService.class);

    private ElasticsearchOperations elasticSearchOperations;
    private Validator validator;

    public ElasticOperationsService(ElasticsearchOperations elasticSearchOperations, Validator validator) {
        this.elasticSearchOperations = elasticSearchOperations;
        this.validator = validator;
    }

    public void save(List<EventDocument> documents) {
        List<EventDocument> validDocuments = new ArrayList<>();
        documents.forEach(document -> {
            Set<ConstraintViolation<EventDocument>> violations = validator.validate(document);

            if (violations.isEmpty()) {
                validDocuments.add(document);
            } else {
                logger.warn("Skip invalid event document id={} violations={}", document.getId(), violations.toString());
            }
        });

        elasticSearchOperations.save(validDocuments);
    }
}
