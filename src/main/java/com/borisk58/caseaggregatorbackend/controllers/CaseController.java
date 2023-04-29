package com.borisk58.caseaggregatorbackend.controllers;

import com.borisk58.caseaggregatorbackend.model.AggregatedCase;
import com.borisk58.caseaggregatorbackend.model.CaseStatus;
import com.borisk58.caseaggregatorbackend.services.aggregator.AggregatorService;
import com.borisk58.caseaggregatorbackend.services.crmfetcher.CrmFetcherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
public class CaseController {
    @Autowired
    AggregatorService aggregatorService;

    @Autowired
    CrmFetcherService crmFetcherService;

    @GetMapping("/cases")
    public ResponseEntity<List<AggregatedCase>> getAggregatedCases(
            @RequestParam(value = "provider", required = false) Integer provider,
            @RequestParam(value = "errorCode", required = false) Integer errorCode,
            @RequestParam(value = "caseStatus", required = false) String caseStatus,
            @RequestParam(value = "refresh", required = false) Boolean refresh) {

        if (refresh) {
            crmFetcherService.fetchCases();
        }

        CaseStatus requiredStatus;
        try {
            requiredStatus = caseStatus == null ? CaseStatus.Any : CaseStatus.valueOf(caseStatus);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        List<AggregatedCase> aggregatedCases = aggregatorService.fetchAggregatedCases(provider, errorCode, requiredStatus);

        if (aggregatedCases == null)
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(aggregatedCases, HttpStatus.OK);
    }
}
