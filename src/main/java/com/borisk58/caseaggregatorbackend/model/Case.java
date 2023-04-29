package com.borisk58.caseaggregatorbackend.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "cases")
public class Case {
  @JsonProperty("Case ID")
  private String originalCaseID;

  public String getOriginalCaseID() {
    return originalCaseID;
  }

  @JsonProperty("Customer ID")
  private int customerID;

  @JsonProperty("Provider")
  private Integer provider;

  public Integer getProvider() {
    return provider;
  }

  public Integer getErrorCode() {
    return errorCode;
  }

  public CaseStatus getStatus() {
    return status;
  }

  public String getProductName() {
    return productName;
  }

  @JsonProperty("CREATED_ERROR_CODE")
  private Integer errorCode;

  @JsonProperty("STATUS")
  private CaseStatus status;

  @JsonProperty("TICKET_CREATION_DATE")
  private String ticketCreationTime;

  @JsonProperty("LAST_MODIFIED_DATE")
  private String lastModified;

  @JsonProperty("PRODUCT_NAME")
  private String productName;

}


