package com.example.demo.configs;

import com.example.demo.repositories.ClassificationLabelsRepository;
import com.example.demo.models.ClassificationLabels;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

//Data Initialization for Classification Labels only

@Component
public class LabelSeeder implements CommandLineRunner {
    private static final Logger logger = LoggerFactory.getLogger(LabelSeeder.class);

    private final ClassificationLabelsRepository labelRepo;

    public LabelSeeder(ClassificationLabelsRepository labelRepo) {
        this.labelRepo = labelRepo;
    }

    @Override
    public void run(String... args) {
        saveIfNotExists("Public", "Information that can be shared publicly");
        saveIfNotExists("Personal", "Personal sensitive information");
        saveIfNotExists("Confidential", "Business confidential information");
        saveIfNotExists("Restricted", "Highly sensitive restricted access data");
    }

    private void saveIfNotExists(String persistentData, String description) {
        Optional<ClassificationLabels> existing = labelRepo.findByPersistentData(persistentData);
        if (existing.isEmpty()) {
            ClassificationLabels label = new ClassificationLabels();
            label.setId(UUID.randomUUID().toString());
            label.setName(persistentData);
            label.setDescription(description);
            label.setPersistentData(persistentData);
            label.setCreatedBy("Admin");
            label.setCreatedAt(LocalDateTime.now());
            label.setUpdatedAt(LocalDateTime.now());
            labelRepo.save(label);
        }
        logger.info("Label created in Elasticsearch index: {}", persistentData);
    }
}