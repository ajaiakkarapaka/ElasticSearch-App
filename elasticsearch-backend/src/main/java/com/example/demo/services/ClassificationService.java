package com.example.demo.services;

import com.example.demo.models.ClassificationEntity;
import com.example.demo.repositories.ClassificationEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class ClassificationService {

@Autowired
private ClassificationEntityRepository mysqlRepo;
@Autowired
private ClassificationSyncService syncService;

    public void addClassification(ClassificationEntity entity) {
        mysqlRepo.save(entity);
        syncService.syncToElasticsearch();
    }

    public void updateClassification(ClassificationEntity entity) {
        mysqlRepo.save(entity);
        syncService.syncToElasticsearch();
    }

    public void deleteClassification(String id) {
        mysqlRepo.deleteById(id);
        syncService.syncToElasticsearch();
    }
}
