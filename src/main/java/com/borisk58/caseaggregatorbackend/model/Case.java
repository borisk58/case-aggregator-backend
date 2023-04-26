package com.borisk58.caseaggregatorbackend.model;

import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "cases")
public class Case {
  private int caseID;
  private int customerID;
  private int providerID;
  private ErrorCode errorCode;
  private CaseStatus status;
  private LocalDateTime ticketCreationTime;
  private LocalDateTime lastModified;
  private String productName;
}


