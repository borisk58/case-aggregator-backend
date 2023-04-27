package com.borisk58.caseaggregatorbackend.services.aggregator;

import com.borisk58.caseaggregatorbackend.model.AggregatedCase;
import com.borisk58.caseaggregatorbackend.model.CaseStatus;

import java.util.List;

public class AggregatorService {
//    @Autowired
//    AggregatedCasesRepository repository;


    public List<AggregatedCase> fetchCases(Integer provider, Integer errorCode, CaseStatus caseStatus) {
        try {
            return null;
            //return repository.fetch(provider, errorCode, caseStatus);
        } catch (Exception e) {
            // log the exception
            return null;
        }

    }
}
