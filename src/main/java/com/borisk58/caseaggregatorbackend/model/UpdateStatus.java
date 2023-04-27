package com.borisk58.caseaggregatorbackend.model;

import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "status")
public class UpdateStatus {
    private String crm;
    private LocalDateTime lastUpdated;
    private int updateVersion;
}
