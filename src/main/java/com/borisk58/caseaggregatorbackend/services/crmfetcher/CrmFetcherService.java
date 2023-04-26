package com.borisk58.caseaggregatorbackend.services.crmfetcher;

import com.borisk58.caseaggregatorbackend.data.AggregatedCasesRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class CrmFetcherService {
    @Autowired
    AggregatedCasesRepository repository;

    private int intervalHours = 4;

    public void startFetching() {

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
        // todo: implement call url (out of scope)
        // so far: read from <crmName>.json and save to mongodb
    }
}
