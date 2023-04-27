package com.borisk58.caseaggregatorbackend.services.aggregator;

import com.borisk58.caseaggregatorbackend.data.AggregatedCasesRepository;
import com.borisk58.caseaggregatorbackend.model.AggregatedCase;
import com.borisk58.caseaggregatorbackend.model.CaseStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class AggregatorService {
    @Autowired
    AggregatedCasesRepository repository;


    public Page<AggregatedCase> fetchCases(Integer provider, Integer errorCode, CaseStatus caseStatus) {
        try {
            AggregatedCase example = new AggregatedCase();
            example.setProvider(provider);
            example.setErrorCode(errorCode);
            example.setRequiredStatus(caseStatus);
            return repository.findAll((Pageable) example);

            //return repository.fetch(provider, errorCode, caseStatus);
        } catch (Exception e) {
            // log the exception
            return null;
        }

    }
}
