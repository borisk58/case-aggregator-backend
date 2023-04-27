package com.borisk58.caseaggregatorbackend.data;

import com.borisk58.caseaggregatorbackend.model.Case;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CasesRepository  extends MongoRepository<Case, Integer> {
}
