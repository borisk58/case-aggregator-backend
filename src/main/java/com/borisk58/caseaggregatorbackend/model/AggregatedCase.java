package com.borisk58.caseaggregatorbackend.model;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
@Document(collection = "aggregated")
public class AggregatedCase {
    private int errorCode;
    private int version;
    int provider;
    CaseStatus requiredStatus;
    private List<String> affectedProducts;
    private List<Case> cases;

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
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

    public CaseStatus getRequiredStatus() {
        return requiredStatus;
    }

    public void setRequiredStatus(CaseStatus requiredStatus) {
        this.requiredStatus = requiredStatus;
    }
}
