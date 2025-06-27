package com.example.demo.configs;
import com.example.demo.services.ClassificationSyncService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

// Quartz configuration for scheduling the synchronization of classification data from MySQL to Elasticsearch

@Configuration
@EnableScheduling
public class QuartzConfig {
    private static final Logger logger = LoggerFactory.getLogger(QuartzConfig.class);

    @Autowired
    private ClassificationSyncService syncService;

    @Scheduled(cron = "0 */5 * * * *") // Runs every 5 minutes
    public void scheduleClassificationSync() {
        logger.info("Scheduled classification sync started.");
        try {
            syncService.syncToElasticsearch();
            logger.info("Scheduled classification sync completed successfully.");
        } catch (Exception e) {
            logger.error("Error occurred during scheduled classification sync.", e);
        }
    }
}

