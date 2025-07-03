package com.example.demo.services;

import com.example.demo.models.ClassificationIndex;
import com.example.demo.models.ClassificationEntity;
import com.example.demo.repositories.ClassificationEntityRepository;
import com.example.demo.repositories.ClassificationIndexRepository;
import org.slf4j.ILoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
@Service
public class ClassificationSyncService {
    @Autowired
    private ClassificationEntityRepository mysqlRepo;
    @Autowired
    private ClassificationIndexRepository esRepo;
    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(ClassificationSyncService.class);

    public void syncToElasticsearch() {
        logger.info("Synchronization started from MySQL to Elasticsearch");
        List<ClassificationEntity> entities = mysqlRepo.findAll();
        Set<String> mysqlIds = entities.stream()
                .map(ClassificationEntity::getId)
                .collect(Collectors.toSet());
        // Fetch all IDs from Elasticsearch
        Iterable<ClassificationIndex> esIterable = esRepo.findAll();
        List<ClassificationIndex> esIndices = StreamSupport
                .stream(esIterable.spliterator(), false)
                .collect(Collectors.toList());
        Set<String> esIds = esIndices.stream()
                .map(ClassificationIndex::getId)
                .collect(Collectors.toSet());
        // Find IDs to delete from Elasticsearch
        Set<String> idsToDelete = esIds.stream()
                .filter(id -> !mysqlIds.contains(id))
                .collect(Collectors.toSet());
        // Delete stale documents from Elasticsearch
        if (!idsToDelete.isEmpty()) {
            esRepo.deleteAllById(idsToDelete);
        }
        // Sync (add/update) all current MySQL entities to Elasticsearch
        for (ClassificationEntity entity : entities) {
         ClassificationIndex index = new ClassificationIndex();
            index.setId(entity.getId());
            index.setFieldName(entity.getFieldName());
            index.setClassificationName(entity.getClassificationName());
            index.setApprovedBy(entity.getApprovedBy());
            index.setModifiedBy(entity.getModifiedBy());
            index.setCreatedAt(entity.getCreatedAt());
            index.setUpdatedAt(entity.getUpdatedAt());
            esRepo.save(index);
        }
    }
}