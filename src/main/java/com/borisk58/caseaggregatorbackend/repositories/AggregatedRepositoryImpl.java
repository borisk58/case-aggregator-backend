package com.borisk58.caseaggregatorbackend.repositories;

import com.borisk58.caseaggregatorbackend.model.AggregatedCase;
import com.borisk58.caseaggregatorbackend.model.CaseStatus;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
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
//        if (provider != null || errorCode != null) {
//            ExampleMatcher matcher = ExampleMatcher.matching()
//                    .withIgnorePaths("affectedProducts", "cases");
//            AggregatedCase aggregatedCase = new AggregatedCase();
//            if (provider != null) aggregatedCase.setProvider(provider);
//            if (errorCode != null) aggregatedCase.setErrorCode(errorCode);
//            Example<AggregatedCase> example = Example.of(aggregatedCase, matcher);
//            List<AggregatedCase> result = super.findAll(example);
//            if (caseStatus != CaseStatus.Any)
//                result = result
//                        .stream()
//                        .filter(c -> c.getCases().stream().anyMatch(x -> x.getStatus() == caseStatus))
//                        .collect(Collectors.toList());
//            return result;
//        } else {
//            return super.findAll();
//        }

        List<AggregatedCase> result = super.findAll();
        if (provider != null)
            result = result.stream().filter(c -> c.getProvider() == provider).collect(Collectors.toList());
        if (errorCode != null)
            result = result.stream().filter(c -> c.getErrorCode() == errorCode).collect(Collectors.toList());
        if (caseStatus != CaseStatus.Any)
            result = result.stream().filter(c -> c.getCases().stream().anyMatch(x -> x.getStatus() == caseStatus)).collect(Collectors.toList());
        return result;

    }

    @Override
    public void saveAggregated(List<AggregatedCase> aggregated) {
        super.deleteAll();
        super.saveAll(aggregated);
    }

}
