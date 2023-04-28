package com.borisk58.caseaggregatorbackend.repositories;

import com.borisk58.caseaggregatorbackend.model.Case;

import java.util.List;

public interface CasesRepository {
    void saveCases(List<Case> cases);
    void deleteCases(List<Case> cases);
    List<Case> findCases(String crmName, int version);
    List<Case> findAllCases();
}
