package com.borisk58.caseaggregatorbackend.repositories;

import com.borisk58.caseaggregatorbackend.model.UpdateStatus;

import java.util.List;

public interface StatusRepository {
    void saveStatus(String crm, int version);
    List<UpdateStatus> findAllStatuses();
}
