package com.borisk58.caseaggregatorbackend.repositories;

import com.borisk58.caseaggregatorbackend.model.AggregatedCase;
import com.borisk58.caseaggregatorbackend.model.Case;
import org.springframework.data.domain.Example;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.query.MongoEntityInformation;
import org.springframework.data.mongodb.repository.support.SimpleMongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

@Repository
public class CasesRepositoryImpl extends SimpleMongoRepository<Case, Integer> implements CasesRepository {
    public CasesRepositoryImpl(MongoEntityInformation<Case, Integer> metadata, MongoOperations mongoOperations) {
        super(metadata, mongoOperations);
        this.mongoTemplate = (MongoTemplate) mongoOperations;
    }

    private final MongoTemplate mongoTemplate;

    @Override
    public void saveCases(List<Case> cases) {
        super.saveAll(cases);
    }

    @Override
    public void deleteCases(List<Case> cases) {
        super.deleteAll(cases);
    }

    @Override
    public List<Case> findCases(String crmName, int version) {
        Case template = new Case();
        template.setCrm(crmName);
        template.setVersion(version);
        Example<Case> example = Example.of(template);
        return super.findAll(example);
    }

    @Override
    public List<Case> findAllCases() {
        return super.findAll();
    }

    @Override
    public List<AggregatedCase> aggregate(int version) {
        Map<String, AggregatedCase> aggregatedCaseMap = new Hashtable<>();

        List<Case> cases = super.findAll();
        for (Case caseObj : cases) {
            Integer errorCode = caseObj.getErrorCode();
            Integer provider = caseObj.getProvider();
            String key = String.format("%d::%s", errorCode, provider);
            AggregatedCase agg;
            if (!aggregatedCaseMap.containsKey(key)) {
                agg = new AggregatedCase();
                agg.setErrorCode(errorCode);
                agg.setProvider(provider);
                aggregatedCaseMap.put(key, agg);
            } else {
                agg = aggregatedCaseMap.get(key);
            }
            agg.getCases().add(caseObj);
            agg.getAffectedProducts().add(caseObj.getProductName());
            agg.setVersion(version);
        }

        return aggregatedCaseMap.values().stream().toList();
    }
}
