package com.borisk58.caseaggregatorbackend.repositories;

import com.borisk58.caseaggregatorbackend.model.AggregatedCase;
import com.borisk58.caseaggregatorbackend.model.CaseStatus;

import java.util.List;

public interface AggregatedRepository {
    List<AggregatedCase> findAllAggregated(Integer provider, Integer errorCode, CaseStatus caseStatus);
    void saveAggregated(List<AggregatedCase> aggregated);
}
