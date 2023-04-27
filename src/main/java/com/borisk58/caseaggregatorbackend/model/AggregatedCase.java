package com.borisk58.caseaggregatorbackend.model;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
@Document(collection = "aggregated")
public class AggregatedCase {
    private int errorCode;
    private int version;
    private List<String> affectedProducts;
    private String provider;
    private List<Case> cases;
}
