package com.borisk58.caseaggregatorbackend.controllers;

import com.borisk58.caseaggregatorbackend.model.AggregatedCase;
import com.borisk58.caseaggregatorbackend.model.CaseStatus;
import com.borisk58.caseaggregatorbackend.services.aggregator.AggregatorService;
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

    @GetMapping("/cases")
    public ResponseEntity<List<AggregatedCase>> getAggregatedCases(
            @RequestParam(value = "provider", required = false) Integer provider,
            @RequestParam(value = "errorCode", required = false) Integer errorCode,
            @RequestParam(value = "caseStatus", required = false) Integer caseStatus) {

        CaseStatus requiredStatus = caseStatus == null ? CaseStatus.Any : CaseStatus.valueOf(caseStatus.toString());
        int providerId = provider == null ? -1 : provider;
        int errorCodeId = errorCode == null ? -1 : errorCode;
        List<AggregatedCase> aggregatedCases = aggregatorService.fetchCases(providerId, errorCodeId, requiredStatus);

        return new ResponseEntity<>(aggregatedCases, HttpStatus.OK);
    }

    @PostMapping("/refresh")
    public void refreshOnDemand() {

    }
}
