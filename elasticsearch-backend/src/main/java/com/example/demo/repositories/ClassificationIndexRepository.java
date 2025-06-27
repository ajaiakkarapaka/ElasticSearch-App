package com.example.demo.repositories;

import com.example.demo.models.ClassificationIndex;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ClassificationIndexRepository extends ElasticsearchRepository<ClassificationIndex, String> {
}