package com.borisk58.caseaggregatorbackend.repositories;

import com.borisk58.caseaggregatorbackend.model.UpdateStatus;
import org.springframework.data.domain.Example;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.repository.query.MongoEntityInformation;
import org.springframework.data.mongodb.repository.support.SimpleMongoRepository;

import java.time.LocalDateTime;
import java.util.List;

public class StatusRepositoryImpl extends SimpleMongoRepository implements StatusRepository {
    public StatusRepositoryImpl(MongoEntityInformation metadata, MongoOperations mongoOperations) {
        super(metadata, mongoOperations);
    }

    @Override
    public void saveStatus(String crm, int version) {
        UpdateStatus status = new UpdateStatus();
        status.setCrm(crm);
        status.setUpdateVersion(version);
        status.setLastUpdated(LocalDateTime.now());
        super.save(status);
    }

    @Override
    public List<UpdateStatus> findAllStatuses() {
        return super.findAll();
    }

    @Override
    public int getVersion(String key) {
        UpdateStatus status = new UpdateStatus();
        status.setCrm(key);
        Example<UpdateStatus> example = Example.of(status);
        UpdateStatus saved = (UpdateStatus) super.findOne(example).get();
        return saved.getUpdateVersion();
    }
}
