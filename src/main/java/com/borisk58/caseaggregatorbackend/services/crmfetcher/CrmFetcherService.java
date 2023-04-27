package com.borisk58.caseaggregatorbackend.services.crmfetcher;

import com.borisk58.caseaggregatorbackend.repositories.CasesRepository;
import com.borisk58.caseaggregatorbackend.repositories.UpdateStatusRepository;
import com.borisk58.caseaggregatorbackend.model.Case;
import com.borisk58.caseaggregatorbackend.model.UpdateStatus;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;

import java.io.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

public class CrmFetcherService {
    @Autowired
    CasesRepository repository;

    @Autowired
    UpdateStatusRepository statusRepository;

    protected int intervalHours = 4;

    protected final Map<String, Integer> currentVersion = new ConcurrentHashMap<>();

    @PostConstruct
    public void startFetching() {
        updateStatus(); // get initial state

        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(new Fetcher(), 0, intervalHours, TimeUnit.HOURS);
    }

    class Fetcher implements Runnable {
        @Override
        public void run() {
            fetchCases();
        }
    }

    private void updateStatus() {
        Example<UpdateStatus> example = Example.of(new UpdateStatus());
        List<UpdateStatus> updateStatuses = statusRepository.findAll(example);

        for (UpdateStatus status : updateStatuses) {
            currentVersion.put(status.getCrm(), status.getUpdateVersion());
        }
    }

    public void stopFetching() {

    }

    private void fetchCases() {
        fetchCrm("banana");
        fetchCrm("strawberry");
    }

    private void fetchCrm(String crmName) {
        String baseCrmUrl = "http://crm/homeassignment/";
        String url = String.format("%s%s", baseCrmUrl, crmName);
        // todo: implement calling the url (out of scope)

        int version = 0;
        if (currentVersion.containsKey(crmName)) {
            version = currentVersion.get(crmName);
        }

        // so far, read from <crmName>.json and save to mongodb
        ObjectMapper mapper = new ObjectMapper();
        TypeReference<List<Case>> typeReference = new TypeReference<>() {};
        List<Case> cases;
        try {
            cases = mapper.readValue(new File("data/" + crmName + ".json"), typeReference);
            int finalVersion = version + 1;
            cases.forEach(c -> {c.setCrm(crmName); c.setVersion(finalVersion);});
            repository.saveAll(cases);

            Case caseToDelete = new Case();
            caseToDelete.setCrm(crmName);
            caseToDelete.setVersion(version);
            Example<Case> example = Example.of(caseToDelete);
            List<Case> toDelete = repository.findAll(example);
            repository.deleteAll(toDelete);

            UpdateStatus status = new UpdateStatus();
            status.setCrm(crmName);
            status.setUpdateVersion(finalVersion);
            status.setLastUpdated(LocalDateTime.now());
            statusRepository.save(status);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
