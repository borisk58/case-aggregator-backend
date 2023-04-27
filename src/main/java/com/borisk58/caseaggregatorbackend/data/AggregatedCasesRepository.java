package com.borisk58.caseaggregatorbackend.data;

import com.borisk58.caseaggregatorbackend.model.AggregatedCase;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AggregatedCasesRepository extends MongoRepository<AggregatedCase, Integer> {
}
