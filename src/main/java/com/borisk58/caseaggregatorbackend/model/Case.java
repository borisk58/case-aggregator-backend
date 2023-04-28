package com.borisk58.caseaggregatorbackend.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

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
  private String provider;

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

  // internal extensions
  private String crm;
  private int version;

  @MongoId
  private String caseId;

  public String getCaseId() {
    return caseId;
  }

  public void setCaseId(String caseId) {
    this.caseId = crm + "::" + originalCaseID;
  }

  public void setCrm(String crm) {
    this.crm = crm;
  }

  public void setVersion(int version) {
    this.version = version;
  }

}


