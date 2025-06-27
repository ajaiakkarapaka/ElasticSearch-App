package com.example.demo.models;

import jakarta.validation.constraints.NotBlank;

public class ClassificationEntityDTO {

    @NotBlank(message = "Field name is mandatory")
    private String fieldName;

    @NotBlank(message = "Classification is mandatory")
    private String classificationName;

    @NotBlank(message = "Approved by is mandatory")
    private String approvedBy;

    @NotBlank(message = "Modified by is mandatory")
    private String modifiedBy;

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
}