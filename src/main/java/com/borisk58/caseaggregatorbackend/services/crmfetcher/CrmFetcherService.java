package com.borisk58.caseaggregatorbackend.services.crmfetcher;

import com.borisk58.caseaggregatorbackend.model.AggregatedCase;
import com.borisk58.caseaggregatorbackend.model.Case;
import com.borisk58.caseaggregatorbackend.repositories.AggregatedRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class CrmFetcherService {

    private final AggregatedRepository aggregatedRepository;

    protected int intervalHours = 4;

    private ScheduledExecutorService scheduler;

    public CrmFetcherService(AggregatedRepository aggregatedRepository) {
        this.aggregatedRepository = aggregatedRepository;
    }

    @PostConstruct
    public void startFetching() {
        scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(new Fetcher(), 0, intervalHours, TimeUnit.HOURS);
    }

    class Fetcher implements Runnable {
        @Override
        public void run() {
            fetchCases();
        }
    }

    public void stopFetching() {
        if (scheduler != null)
            scheduler.shutdown();
    }

    private final Map<String, AggregatedCase> aggregatedCaseMap = new Hashtable<>();

    public void fetchCases() {
        aggregatedCaseMap.clear();
        fetchCrm("banana");
        fetchCrm("strawberry");
        aggregatedRepository.saveAggregated(aggregatedCaseMap.values().stream().toList());
    }

    private void fetchCrm(String crmName) {
        ObjectMapper mapper = new ObjectMapper();
        TypeReference<List<Case>> typeReference = new TypeReference<>() {};
        List<Case> cases;
        try {
            // so far, read from <crmName>.json as if it was a response from CRM, and save to mongodb
            cases = mapper.readValue(new File("data/" + crmName + ".json"), typeReference);
//            cases.forEach(c -> {
//                c.setCrm(crmName);
//                c.setCaseId(c.getOriginalCaseID());
//            });

            updateAggregated(cases);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void updateAggregated(List<Case> cases) {
        for (Case caseObj : cases) {
            Integer errorCode = caseObj.getErrorCode();
            Integer provider = caseObj.getProvider();
            String key = String.format("%d::%s", errorCode, provider);
            AggregatedCase agg;
            if (!aggregatedCaseMap.containsKey(key)) {
                agg = new AggregatedCase();
                agg.setErrorCode(errorCode);
                agg.setProvider(provider);
                agg.setCases(new ArrayList<>());
                agg.setAffectedProducts(new ArrayList<>());
                aggregatedCaseMap.put(key, agg);
            } else {
                agg = aggregatedCaseMap.get(key);
            }
            agg.getCases().add(caseObj);
            agg.getAffectedProducts().add(caseObj.getProductName());
        }
    }
}
