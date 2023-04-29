package com.borisk58.caseaggregatorbackend;

import com.borisk58.caseaggregatorbackend.model.AggregatedCase;
import com.borisk58.caseaggregatorbackend.model.CaseStatus;
import com.borisk58.caseaggregatorbackend.repositories.AggregatedRepository;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AggregatedRepositoryMock implements AggregatedRepository {

    Map<String, AggregatedCase> cases = new Hashtable<>();

    @Override
    public List<AggregatedCase> findAllAggregated(Integer provider, Integer errorCode, CaseStatus caseStatus) {
        List<AggregatedCase> result = cases.values().stream().toList();
        if (provider != null)
            result = result.stream().filter(c -> c.getProvider() == provider).collect(Collectors.toList());
        if (errorCode != null)
            result = result.stream().filter(c -> c.getErrorCode() == errorCode).collect(Collectors.toList());
        if (caseStatus != CaseStatus.Any)
            result = result.stream().filter(c -> c.getCases().stream().allMatch(x -> x.getStatus() == caseStatus)).collect(Collectors.toList());
        return result;
    }

    @Override
    public void saveAggregated(List<AggregatedCase> aggregated) {
        cases.clear();
        aggregated.forEach(c -> {
            String key = String.format("%d::%s", c.getErrorCode(), c.getProvider());
            cases.put(key, c);
        });
    }

}
