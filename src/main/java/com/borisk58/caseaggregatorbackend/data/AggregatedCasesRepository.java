package com.borisk58.caseaggregatorbackend.data;

import com.borisk58.caseaggregatorbackend.model.AggregatedCase;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AggregatedCasesRepository extends MongoRepository<AggregatedCase, Integer> {

//    public void update(List<AggregatedCase> data);
//
//    public default List<AggregatedCase> fetch(Integer provider, Integer errorCode, CaseStatus caseStatus) {
//        return null;
//    }
}
