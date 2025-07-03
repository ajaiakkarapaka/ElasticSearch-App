package com.example.demo.repositories;

import com.example.demo.models.ClassificationIndex;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import java.util.List;

public interface ClassificationIndexRepository extends ElasticsearchRepository<ClassificationIndex, String> {
    List<ClassificationIndex>
    findByFieldNameContainingIgnoreCaseOrClassificationNameContainingIgnoreCaseOrApprovedByContainingIgnoreCaseOrModifiedByContainingIgnoringCase(String fieldName, String classificationName, String approvedBy, String modifiedBy);
}