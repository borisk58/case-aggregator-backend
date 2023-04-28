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
        if (provider >= 0 || errorCode >= 0 || caseStatus != CaseStatus.Any) {
            AggregatedCase aggregatedCase = new AggregatedCase();
            if (provider >= 0) aggregatedCase.setProvider(provider);
            if (errorCode >= 0) aggregatedCase.setErrorCode(errorCode);
            if (caseStatus != CaseStatus.Any) aggregatedCase.setRequiredStatus(caseStatus);
            Example<AggregatedCase> example = Example.of(aggregatedCase);
            return super.findAll(example);
        } else {
            return super.findAll();
        }
    }

    @Override
    public void saveAggregated(List<AggregatedCase> aggregated) {
        super.saveAll(aggregated);
    }

    @Override
    public void deleteVersion(int version) {
        AggregatedCase agg = new AggregatedCase();
        agg.setVersion(version);
        Example<AggregatedCase> example = Example.of(agg);
        super.deleteAll(super.findAll(example));
    }

}
