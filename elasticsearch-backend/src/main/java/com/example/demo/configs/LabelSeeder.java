package com.example.demo.configs;

import com.example.demo.repositories.ClassificationLabelsRepository;
import com.example.demo.models.ClassificationLabels;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

//Data Initialization for Classification Labels only

@Component
public class DataSeeder implements CommandLineRunner {

    private final ClassificationLabelsRepository labelRepo;
   // private final ClassificationEntityRepository indexRepo;

    public DataSeeder(ClassificationLabelsRepository labelRepo) {
        this.labelRepo = labelRepo;
        //this.indexRepo = indexRepo;
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
    }
}