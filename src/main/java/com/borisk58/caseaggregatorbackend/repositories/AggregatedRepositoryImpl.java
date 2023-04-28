package com.borisk58.caseaggregatorbackend.repositories;

import com.borisk58.caseaggregatorbackend.model.AggregatedCase;
import com.borisk58.caseaggregatorbackend.model.CaseStatus;
import org.springframework.data.domain.Example;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.repository.query.MongoEntityInformation;
import org.springframework.data.mongodb.repository.support.SimpleMongoRepository;

import java.util.List;

public class AggregatedRepositoryImpl extends SimpleMongoRepository<AggregatedCase, Integer> implements AggregatedRepository {
    public AggregatedRepositoryImpl(MongoEntityInformation<AggregatedCase, Integer> metadata, MongoOperations mongoOperations) {
        super(metadata, mongoOperations);
    }

    @Override
    public List<AggregatedCase> findAllAggregated(int provider, int errorCode, CaseStatus caseStatus) {
        AggregatedCase aggregatedCase = new AggregatedCase();
        aggregatedCase.setProvider(provider);
        aggregatedCase.setErrorCode(errorCode);
        aggregatedCase.setRequiredStatus(caseStatus);
        Example<AggregatedCase> example = Example.of(aggregatedCase);
        return super.findAll(example);
    }
}
