package com.example.demo.controllers;

import com.example.demo.models.ClassificationLabels;
import com.example.demo.repositories.ClassificationLabelsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@RestController
@Tag(name = "Classification Label Management")
@RequestMapping("/api/classifications")
public class ClassificationLabelController {
    private static final Logger logger = LoggerFactory.getLogger(ClassificationLabelController.class);
@Autowired
    private ClassificationLabelsRepository labelRepo;

    // === READ ONLY for LABEL ===
    @GetMapping("/label")
    @Operation(summary = "Get all Classification Labels (read-only)")
    public Iterable<ClassificationLabels> getAllLabels() {
        logger.info("Fetching labels from Elasticsearch.");
        try {
            return labelRepo.findAll();
        } catch (Exception e) {
            logger.error("Error fetching classification labels from Elasticsearch ", e);
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



}
