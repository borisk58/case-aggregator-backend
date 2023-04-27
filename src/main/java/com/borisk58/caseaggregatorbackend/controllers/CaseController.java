package com.borisk58.caseaggregatorbackend.controllers;

import com.borisk58.caseaggregatorbackend.model.AggregatedCase;
import com.borisk58.caseaggregatorbackend.model.CaseStatus;
import com.borisk58.caseaggregatorbackend.services.aggregator.AggregatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
public class CaseController {
    @Autowired
    AggregatorService aggregatorService;

    @GetMapping("/cases")
    public List<AggregatedCase> getAggregatedCases(
            @RequestParam(value = "provider", required = false) Integer provider,
            @RequestParam(value = "errorCode", required = false) Integer errorCode,
            @RequestParam(value = "caseStatus", required = false) CaseStatus caseStatus) {
        return aggregatorService.fetchCases(provider, errorCode, caseStatus);
    }

    @PostMapping("/refresh")
    public void refreshOnDemand() {

    }
}
