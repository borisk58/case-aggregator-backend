package com.borisk58.caseaggregatorbackend.configuration;

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

@Configuration
public class Beans {
    @Bean
    public MongoTemplate mongoTemplate() throws Exception {
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
    public AggregatorService aggregatorService() {
        return new AggregatorService();
    }

    @Bean
    public CrmFetcherService crmFetcherService() { return new CrmFetcherService(); }
}
