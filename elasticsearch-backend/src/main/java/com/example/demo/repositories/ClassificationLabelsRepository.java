package com.example.demo.repositories;

import com.example.demo.models.ClassificationLabels;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.Optional;

public interface ClassificationLabelsRepository extends ElasticsearchRepository<ClassificationLabels, String> {
    Optional<ClassificationLabels> findByName(String name);

    Optional<ClassificationLabels> findByPersistentData(String persistentData);
}