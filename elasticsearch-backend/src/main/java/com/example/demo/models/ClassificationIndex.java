package com.example.demo.models;
import jakarta.persistence.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.time.LocalDateTime;

//ES Index for Classification for sync

@Document(indexName = "classification_index")
public class ClassificationIndex {
    @Id
    private String id;
    private String fieldName;
    private String classificationName;
    private String approvedBy;
    private String modifiedBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFieldName() {
        return fieldName;
    }
    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getClassificationName() {
        return classificationName;
    }
    public void setClassificationName(String classificationName) {
        this.classificationName = classificationName;
    }

    public String getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(String approvedBy) {
        this.approvedBy = approvedBy;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }



}
