package com.borisk58.caseaggregatorbackend.services.aggregator;

import com.borisk58.caseaggregatorbackend.model.AggregatedCase;
import com.borisk58.caseaggregatorbackend.model.CaseStatus;
import com.borisk58.caseaggregatorbackend.repositories.AggregatedRepository;

import java.util.List;

public class AggregatorService {
    private final AggregatedRepository repository;

    public AggregatorService(AggregatedRepository repository) {
        this.repository = repository;
    }


    public List<AggregatedCase> fetchAggregatedCases(Integer provider, Integer errorCode, CaseStatus caseStatus) {
        try {
            return repository.findAllAggregated(provider, errorCode, caseStatus);
        } catch (Exception e) {
            // log the exception
            return null;
        }

    }
}
