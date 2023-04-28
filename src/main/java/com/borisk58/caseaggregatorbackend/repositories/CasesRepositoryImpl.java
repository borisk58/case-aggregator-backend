package com.borisk58.caseaggregatorbackend.repositories;

import com.borisk58.caseaggregatorbackend.model.Case;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.springframework.data.domain.Example;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.query.MongoEntityInformation;
import org.springframework.data.mongodb.repository.support.SimpleMongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

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
    public void aggregate() {
        MongoCollection<Document> collection = mongoTemplate.getCollection("cases");
        AggregateIterable<Document> result = collection.aggregate(Arrays.asList(
                new Document("$group", new Document("_id", new Document("errorCode", "$errorCode").append("provider", "$provider"))
                        .append("documents", new Document("$push", "$$ROOT"))
                ),
                new Document("$out", "groupedByErrorCodeAndProvider")
        ));

        // Iterate over the results to trigger the aggregation
        for (Document document : result) {
            // Do nothing, just iterate over the result
        }

    }
}
