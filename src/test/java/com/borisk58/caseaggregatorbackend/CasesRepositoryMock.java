package com.borisk58.caseaggregatorbackend;

import com.borisk58.caseaggregatorbackend.model.Case;
import com.borisk58.caseaggregatorbackend.repositories.CasesRepository;

import java.util.List;

public class CasesRepositoryMock implements CasesRepository {
    @Override
    public void saveCases(List<Case> cases) {

    }

    @Override
    public void deleteCases(List<Case> cases) {

    }

    @Override
    public List<Case> findCases(String crmName, int version) {
        return null;
    }

    @Override
    public List<Case> findAllCases() {
        return null;
    }
}
