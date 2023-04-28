package com.borisk58.caseaggregatorbackend;

import com.borisk58.caseaggregatorbackend.model.Case;
import com.borisk58.caseaggregatorbackend.repositories.CasesRepository;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CasesRepositoryMock implements CasesRepository {
    public static Map<String, Case> caseMap = new Hashtable<>();
    @Override
    public void saveCases(List<Case> cases) {
        cases.forEach(c -> caseMap.put(c.getCaseId(), c));
    }

    @Override
    public void deleteCases(List<Case> cases) {
        cases.forEach(c -> caseMap.remove(c.getCaseId()));
    }

    @Override
    public List<Case> findCases(String crmName, int version) {
        return caseMap
                .values()
                .stream()
                .filter(c -> c.getCrm().equals(crmName) && c.getVersion() == version)
                .collect(Collectors.toList());
    }

    @Override
    public List<Case> findAllCases() {
        return caseMap.values().stream().toList();
    }

    @Override
    public void aggregate() {

    }
}
