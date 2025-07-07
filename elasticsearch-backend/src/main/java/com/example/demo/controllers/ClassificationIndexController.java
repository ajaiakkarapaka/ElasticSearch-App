package com.example.demo.controllers;

import com.example.demo.models.ClassificationEntity;
import com.example.demo.models.ClassificationEntityDTO;
import com.example.demo.models.ClassificationIndex;
import com.example.demo.models.ClassificationLabels;
import com.example.demo.repositories.ClassificationEntityRepository;
import com.example.demo.repositories.ClassificationLabelsRepository;
import com.example.demo.services.ClassificationSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.UUID;
import java.util.List;
import java.util.Optional;
import java.time.LocalDateTime;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.example.demo.services.ClassificationService;

//HTTP Web API for managing classification labels and index.
@RestController
@RequestMapping("/api/classifications")
@Tag(name = "Classification Management")
public class ClassificationController {
    private static final Logger logger = LoggerFactory.getLogger(ClassificationController.class);
    @Autowired
    private ClassificationSearchService searchService;
    @Autowired
    private ClassificationService classificationService;
    @Autowired
    private ClassificationLabelsRepository labelRepo;
    @Autowired
    private ClassificationEntityRepository indexRepo;

    // === Create, Update and Delete methods for INDEX ===
    @PostMapping("/index")
    @Operation(summary = "Create Classification Index")
    public ClassificationEntity createIndex(@Valid @RequestBody ClassificationEntityDTO dto) {
        ClassificationEntity index = new ClassificationEntity();
        index.setFieldName(dto.getFieldName());
        index.setId(UUID.randomUUID().toString());
        // Fetch label and set classificationName to persistentData
        Optional<ClassificationLabels> labelOpt = labelRepo.findByName(dto.getClassificationName());
        if (labelOpt.isPresent()) {
            index.setClassificationName(labelOpt.get().getPersistentData());
        } else {
            index.setClassificationName(dto.getClassificationName());
        }
        index.setApprovedBy(dto.getApprovedBy());
        index.setModifiedBy(dto.getModifiedBy());
        index.setCreatedAt(LocalDateTime.now());
        index.setUpdatedAt(LocalDateTime.now());
        classificationService.addClassification(index);
        return indexRepo.save(index);
    }

    @PutMapping("/index/{id}")
    @Operation(summary = "Update Classification")
    public ResponseEntity<ClassificationEntity> updateIndex(@PathVariable String id, @RequestBody ClassificationEntityDTO dto) {
        return indexRepo.findById(id).map(index -> {
            index.setFieldName(dto.getFieldName());
            index.setClassificationName(dto.getClassificationName());
            index.setApprovedBy(dto.getApprovedBy());
            index.setModifiedBy(dto.getModifiedBy());
            index.setUpdatedAt(LocalDateTime.now());
            classificationService.updateClassification(index);
            return ResponseEntity.ok(indexRepo.save(index));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/index/{id}")
    @Operation(summary = "Delete Classification")
    public ResponseEntity<Void> deleteIndex(@PathVariable String id) {
        if (indexRepo.existsById(id)) {
            indexRepo.deleteById(id);
            classificationService.deleteClassification(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    // === Read, Search & Display method from Elasticsearch ===
    @GetMapping("/index")
    @Operation(summary = "Get all Classifications from Elasticsearch")
    public List<ClassificationIndex> getAllFromElasticsearch() {
        return searchService.searchAll();
    }
    @GetMapping("/index/{id}")
    @Operation(summary = "Get Classification by ID")
    public ResponseEntity<ClassificationIndex> getIndexById(@PathVariable String id) {
        return searchService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @GetMapping("/index/search")
    @Operation(summary = "Search Classification Index by Query")
    public List<ClassificationIndex> searchIndex(@RequestParam("q") String query) {
        return searchService.searchBy(query);
    }
}
