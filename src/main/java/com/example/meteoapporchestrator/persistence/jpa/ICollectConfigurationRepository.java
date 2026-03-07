package com.example.meteoapporchestrator.persistence.jpa;

import com.example.meteoapporchestrator.persistence.jpa.model.CollectConfigurationEntity;
import org.jspecify.annotations.NonNull;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ICollectConfigurationRepository extends CrudRepository<CollectConfigurationEntity, UUID> {
    @Override
    @NonNull
    List<CollectConfigurationEntity> findAll();
}
