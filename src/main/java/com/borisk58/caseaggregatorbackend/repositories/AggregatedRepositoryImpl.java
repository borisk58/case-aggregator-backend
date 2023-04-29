package com.borisk58.caseaggregatorbackend.repositories;

import com.borisk58.caseaggregatorbackend.model.AggregatedCase;
import com.borisk58.caseaggregatorbackend.model.CaseStatus;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.repository.query.MongoEntityInformation;
import org.springframework.data.mongodb.repository.support.SimpleMongoRepository;

import java.util.List;
import java.util.stream.Collectors;

public class AggregatedRepositoryImpl extends SimpleMongoRepository<AggregatedCase, Integer> implements AggregatedRepository {
    public AggregatedRepositoryImpl(MongoEntityInformation<AggregatedCase, Integer> metadata, MongoOperations mongoOperations) {
        super(metadata, mongoOperations);
    }

    @Override
    public List<AggregatedCase> findAllAggregated(Integer provider, Integer errorCode, CaseStatus caseStatus) {
//        if (provider >= 0 || errorCode >= 0 || caseStatus != CaseStatus.Any) {
//            AggregatedCase aggregatedCase = new AggregatedCase();
//            if (provider >= 0) aggregatedCase.setProvider(provider);
//            if (errorCode >= 0) aggregatedCase.setErrorCode(errorCode);
//            if (caseStatus != CaseStatus.Any) aggregatedCase.setRequiredStatus(caseStatus);
//            Example<AggregatedCase> example = Example.of(aggregatedCase);
//            //return super.findAll(example);
//        } else {
//            return super.findAll();
//        }
        List<AggregatedCase> result = super.findAll();
        if (provider != null)
            result = result.stream().filter(c -> c.getProvider() == provider).collect(Collectors.toList());
        if (errorCode != null)
            result = result.stream().filter(c -> c.getErrorCode() == errorCode).collect(Collectors.toList());
        if (caseStatus != CaseStatus.Any)
            result = result.stream().filter(c -> c.getCases().stream().allMatch(x -> x.getStatus() == caseStatus)).collect(Collectors.toList());
        return result;

    }

    @Override
    public void saveAggregated(List<AggregatedCase> aggregated) {
        super.deleteAll();
        super.saveAll(aggregated);
    }

}
