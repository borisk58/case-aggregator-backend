package com.borisk58.caseaggregatorbackend.model;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
@Document(collection = "aggregated")
public class AggregatedCase {
    private int errorCode;
    int provider;
    private List<String> affectedProducts;
    private List<Case> cases;

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public List<String> getAffectedProducts() {
        return affectedProducts;
    }

    public void setAffectedProducts(List<String> affectedProducts) {
        this.affectedProducts = affectedProducts;
    }

    public int getProvider() {
        return provider;
    }

    public void setProvider(int provider) {
        this.provider = provider;
    }

    public List<Case> getCases() {
        return cases;
    }

    public void setCases(List<Case> cases) {
        this.cases = cases;
    }
}
