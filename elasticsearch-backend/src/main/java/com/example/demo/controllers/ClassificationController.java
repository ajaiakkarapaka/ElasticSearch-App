package com.example.demo.controllers;

import com.example.demo.models.ClassificationEntity;
import com.example.demo.models.ClassificationEntityDTO;
import com.example.demo.models.ClassificationLabels;
import com.example.demo.repositories.ClassificationEntityRepository;
import com.example.demo.repositories.ClassificationLabelsRepository;
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

//HTTP Web API for managing classification labels and indices
@RestController
@RequestMapping("/api/classifications")
@Tag(name = "Classification Management")
public class ClassificationController {

    private static final Logger logger = LoggerFactory.getLogger(ClassificationController.class);

    @Autowired
    private ClassificationLabelsRepository labelRepo;
    @Autowired
    private ClassificationEntityRepository indexRepo;

    // === READ ONLY for LABEL ===
    @GetMapping("/label")
    @Operation(summary = "Get all Classification Labels (read-only)")
    public Iterable<ClassificationLabels> getAllLabels() {
        logger.info("Fetching all classification labels...");
        try {
            return labelRepo.findAll();
        } catch (Exception e) {
            logger.error("Error fetching classification labels: ", e);
            throw e;
        }
    }

    @GetMapping("/label/{labelName}")
    @Operation(summary = "Get a Classification Label by Name (read-only)")
    public ResponseEntity<ClassificationLabels> getLabelByName(@PathVariable String labelName) {
        return labelRepo.findByName(labelName)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    // === CRUD for INDEX ===
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
        return indexRepo.save(index);
    }

    @GetMapping("/index")
    @Operation(summary = "Get all Classifications")
    public List<ClassificationEntity> getAll() {
        return indexRepo.findAll();
    }

    @GetMapping("/index/{id}")
    @Operation(summary = "Get Classification by ID")
    public ResponseEntity<ClassificationEntity> getIndexById(@PathVariable String id) {
        return indexRepo.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
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
            return ResponseEntity.ok(indexRepo.save(index));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/index/{id}")
    @Operation(summary = "Delete Classification")
    public ResponseEntity<Void> deleteIndex(@PathVariable String id) {
        if (indexRepo.existsById(id)){
            indexRepo.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/index/search")
    public List<ClassificationEntity> searchIndices(@RequestParam("q") String query) {
        return indexRepo.findByFieldNameContainingIgnoreCaseOrClassificationNameContainingIgnoreCaseOrApprovedByContainingIgnoreCase(query, query, query);
    }

    }
