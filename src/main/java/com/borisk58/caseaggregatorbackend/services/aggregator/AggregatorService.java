package com.borisk58.caseaggregatorbackend.services.aggregator;

import com.borisk58.caseaggregatorbackend.data.AggregatedCasesRepository;
import com.borisk58.caseaggregatorbackend.model.AggregatedCase;
import com.borisk58.caseaggregatorbackend.model.CaseStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;

import java.util.List;

public class AggregatorService {
    @Autowired
    AggregatedCasesRepository repository;


    public List<AggregatedCase> fetchCases(int provider, int errorCode, CaseStatus caseStatus) {
        try {
            AggregatedCase aggregatedCase = new AggregatedCase();
            aggregatedCase.setProvider(provider);
            aggregatedCase.setErrorCode(errorCode);
            aggregatedCase.setRequiredStatus(caseStatus);
            Example<AggregatedCase> example = Example.of(aggregatedCase);
            return repository.findAll(example);

            //return repository.fetch(provider, errorCode, caseStatus);
        } catch (Exception e) {
            // log the exception
            return null;
        }

    }
}
