package com.example.demo.services;

import com.example.demo.models.ClassificationIndex;
import com.example.demo.models.ClassificationEntity;
import com.example.demo.repositories.ClassificationEntityRepository;
import com.example.demo.repositories.ClassificationIndexRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ClassificationSyncService {
    @Autowired
    private ClassificationEntityRepository mysqlRepo;
    @Autowired
    private ClassificationIndexRepository esRepo;

    public void syncToElasticsearch() {
        List<ClassificationEntity> entities = mysqlRepo.findAll();
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