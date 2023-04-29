package com.borisk58.caseaggregatorbackend;

import com.borisk58.caseaggregatorbackend.model.AggregatedCase;
import com.borisk58.caseaggregatorbackend.model.CaseStatus;
import com.borisk58.caseaggregatorbackend.repositories.AggregatedRepository;

import java.util.List;

public class AggregatedRepositoryMock implements AggregatedRepository {
    @Override
    public List<AggregatedCase> findAllAggregated(Integer provider, Integer errorCode, CaseStatus caseStatus) {
        return null;
    }

    @Override
    public void saveAggregated(List<AggregatedCase> aggregated) {

    }
}
