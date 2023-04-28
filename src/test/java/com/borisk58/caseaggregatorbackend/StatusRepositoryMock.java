package com.borisk58.caseaggregatorbackend;

import com.borisk58.caseaggregatorbackend.model.UpdateStatus;
import com.borisk58.caseaggregatorbackend.repositories.StatusRepository;
import org.springframework.data.domain.Example;

import java.time.LocalDateTime;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public class StatusRepositoryMock implements StatusRepository {
    public static Map<String, UpdateStatus> statusMap = new Hashtable<>();
    @Override
    public void saveStatus(String crm, int version) {
        UpdateStatus status;
        if (statusMap.containsKey(crm)) {
            status = statusMap.get(crm);
            status.setUpdateVersion(version);
            status.setLastUpdated(LocalDateTime.now());
        } else {
            status = new UpdateStatus();
            status.setCrm(crm);
            status.setUpdateVersion(version);
            status.setLastUpdated(LocalDateTime.now());
            statusMap.put(crm, status);
        }
    }

    @Override
    public List<UpdateStatus> findAllStatuses() {
        return statusMap.values().stream().toList();
    }

    @Override
    public int getVersion(String key) {
        UpdateStatus updateStatus = statusMap.getOrDefault(key, null);
        return updateStatus == null ? 0 : updateStatus.getUpdateVersion();
    }
}
