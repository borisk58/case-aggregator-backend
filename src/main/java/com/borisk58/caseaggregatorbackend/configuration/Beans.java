package com.borisk58.caseaggregatorbackend.configuration;

import com.borisk58.caseaggregatorbackend.model.AggregatedCase;
import com.borisk58.caseaggregatorbackend.repositories.AggregatedRepositoryImpl;
import com.borisk58.caseaggregatorbackend.services.aggregator.AggregatorService;
import com.borisk58.caseaggregatorbackend.services.crmfetcher.CrmFetcherService;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.query.MongoEntityInformation;
import org.springframework.data.mongodb.repository.support.MongoRepositoryFactory;

@Configuration
public class Beans {
    @Value("${spring.data.mongodb.uri}")
    String databaseUri;

    @Value("${spring.data.mongodb.database}")
    String databaseName;

    @Bean
    public MongoTemplate mongoTemplate() {
        ConnectionString connectionString = new ConnectionString(this.databaseUri);
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .serverApi(ServerApi.builder()
                        .version(ServerApiVersion.V1)
                        .build())
                .build();
        MongoClient mongoClient = MongoClients.create(settings);
        return new MongoTemplate(mongoClient, databaseName);
    }

    @Bean
    MongoEntityInformation<AggregatedCase, Integer> aggregatedMetadata() {
        MongoRepositoryFactory factory = new MongoRepositoryFactory(mongoTemplate());
        return factory.getEntityInformation(AggregatedCase.class);
    }

    @Bean
    public CrmFetcherService crmFetcherService() {
        AggregatedRepositoryImpl aggregatedRepository = new AggregatedRepositoryImpl(aggregatedMetadata(), mongoTemplate());
        return new CrmFetcherService(aggregatedRepository);
    }

    @Bean
    public AggregatorService aggregatorService() {
        return new AggregatorService(new AggregatedRepositoryImpl(aggregatedMetadata(), mongoTemplate()));
    }

}




