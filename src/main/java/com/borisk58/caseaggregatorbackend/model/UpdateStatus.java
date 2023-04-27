package com.borisk58.caseaggregatorbackend.model;

import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "status")
public class UpdateStatus {
    private String crm;

    public String getCrm() {
        return crm;
    }

    public void setCrm(String crm) {
        this.crm = crm;
    }

    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(LocalDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public int getUpdateVersion() {
        return updateVersion;
    }

    public void setUpdateVersion(int updateVersion) {
        this.updateVersion = updateVersion;
    }

    private LocalDateTime lastUpdated;
    private int updateVersion;
}
