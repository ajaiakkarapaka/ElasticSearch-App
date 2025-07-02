package com.example.demo.controllers;

import com.example.demo.services.ClassificationSyncService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

//HTTP REST controller for manual synchronization of classification data from mySQL to Elasticsearch.

@RestController
@RequestMapping("/api/sync")
@Tag(name = "Sync Management")
public class ClassificationSyncController {

    private static final Logger logger = LoggerFactory.getLogger(ClassificationSyncController.class);


    @Autowired
    private ClassificationSyncService syncService;

    @PostMapping("/elasticsearch")
    public String manualSync() {
        syncService.syncToElasticsearch();
        return "Synchronisation triggered";

    }
}