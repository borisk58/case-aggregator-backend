package com.borisk58.caseaggregatorbackend.services.crmfetcher;

import com.borisk58.caseaggregatorbackend.repositories.CasesRepository;
import com.borisk58.caseaggregatorbackend.repositories.StatusRepository;
import com.borisk58.caseaggregatorbackend.model.Case;
import com.borisk58.caseaggregatorbackend.model.UpdateStatus;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.MongoCollection;
import jakarta.annotation.PostConstruct;

import java.io.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

public class CrmFetcherService {

    private final CasesRepository repository;

    private final StatusRepository statusRepository;

    protected int intervalHours = 4;

    protected final Map<String, Integer> currentVersion = new ConcurrentHashMap<>();
    private ScheduledExecutorService scheduler;

    public CrmFetcherService(CasesRepository repository, StatusRepository statusRepository) {
        this.repository = repository;
        this.statusRepository = statusRepository;
    }

    @PostConstruct
    public void startFetching() {
        updateStatus(); // get initial state

        scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(new Fetcher(), 0, intervalHours, TimeUnit.HOURS);
    }

    class Fetcher implements Runnable {
        @Override
        public void run() {
            fetchCases();
        }
    }

    private void updateStatus() {
        List<UpdateStatus> updateStatuses = statusRepository.findAllStatuses();
        currentVersion.clear();
        for (UpdateStatus status : updateStatuses) {
            currentVersion.put(status.getCrm(), status.getUpdateVersion());
        }
    }

    public void stopFetching() {
        if (scheduler != null)
            scheduler.shutdown();
    }

    public void fetchCases() {
        fetchCrm("banana");
        fetchCrm("strawberry");

        aggregateCases();
    }

    private void fetchCrm(String crmName) {
        String baseCrmUrl = "http://crm/homeassignment/";
        String url = String.format("%s%s", baseCrmUrl, crmName);
        // todo: implement calling the url (out of scope)

        int version = 0;
        if (currentVersion.containsKey(crmName)) {
            version = currentVersion.get(crmName);
        }

        ObjectMapper mapper = new ObjectMapper();
        TypeReference<List<Case>> typeReference = new TypeReference<>() {};
        List<Case> cases;
        try {
            // so far, read from <crmName>.json as if it was a response from CRM, and save to mongodb
            cases = mapper.readValue(new File("data/" + crmName + ".json"), typeReference);
            int finalVersion = version + 1;
            cases.forEach(c -> {
                c.setCrm(crmName);
                c.setVersion(finalVersion);
                c.setCaseId(c.getOriginalCaseID());
            });
            repository.saveCases(cases);

            List<Case> toDelete = repository.findCases(crmName, version);
            repository.deleteCases(toDelete);

            statusRepository.saveStatus(crmName, finalVersion);
            updateStatus();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void aggregateCases() {
        repository.aggregate();
    }
}
