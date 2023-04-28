package com.borisk58.caseaggregatorbackend.configuration;

import com.borisk58.caseaggregatorbackend.model.AggregatedCase;
import com.borisk58.caseaggregatorbackend.model.Case;
import com.borisk58.caseaggregatorbackend.model.UpdateStatus;
import com.borisk58.caseaggregatorbackend.repositories.AggregatedRepositoryImpl;
import com.borisk58.caseaggregatorbackend.repositories.CasesRepositoryImpl;
import com.borisk58.caseaggregatorbackend.repositories.StatusRepositoryImpl;
import com.borisk58.caseaggregatorbackend.services.aggregator.AggregatorService;
import com.borisk58.caseaggregatorbackend.services.crmfetcher.CrmFetcherService;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.query.MongoEntityInformation;
import org.springframework.data.mongodb.repository.support.MongoRepositoryFactory;

@Configuration
public class Beans {
    @Bean
    public MongoTemplate mongoTemplate() {
        ConnectionString connectionString = new ConnectionString("mongodb://localhost:27017");
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .serverApi(ServerApi.builder()
                        .version(ServerApiVersion.V1)
                        .build())
                .build();
        MongoClient mongoClient = MongoClients.create(settings);
        return new MongoTemplate(mongoClient, "aggregated-cases");
    }

    @Bean
    MongoEntityInformation<Case, Integer> caseMetadata() {
        MongoRepositoryFactory factory = new MongoRepositoryFactory(mongoTemplate());
        return factory.getEntityInformation(Case.class);
    }

    @Bean
    MongoEntityInformation<AggregatedCase, Integer> aggregatedMetadata() {
        MongoRepositoryFactory factory = new MongoRepositoryFactory(mongoTemplate());
        return factory.getEntityInformation(AggregatedCase.class);
    }

    @Bean
    MongoEntityInformation<UpdateStatus, String> statusMetadata() {
        MongoRepositoryFactory factory = new MongoRepositoryFactory(mongoTemplate());
        return factory.getEntityInformation(UpdateStatus.class);
    }
    @Bean
    public CrmFetcherService crmFetcherService() {
        CasesRepositoryImpl casesRepository = new CasesRepositoryImpl(caseMetadata(), mongoTemplate());
        StatusRepositoryImpl statusRepository = new StatusRepositoryImpl(statusMetadata(), mongoTemplate());
        return new CrmFetcherService(casesRepository, statusRepository);
    }

    @Bean
    public AggregatorService aggregatorService() {
        return new AggregatorService(new AggregatedRepositoryImpl(aggregatedMetadata(), mongoTemplate()));
    }

}




