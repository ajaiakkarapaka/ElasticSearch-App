package com.example.demo.models;

import jakarta.validation.constraints.NotBlank;

public class ClassificationLabelsDTO {

    @NotBlank(message = "Name is mandatory")
    private String name;

    @NotBlank(message = "Description is mandatory")
    private String description;

    @NotBlank(message = "Persistent data is mandatory")
    private String persistentData;

    @NotBlank(message = "Created by is mandatory")
    private String createdBy;

    public @NotBlank(message = "Name is mandatory") String getName() {
        return name;
    }

    public void setName(@NotBlank(message = "Name is mandatory") String name) {
        this.name = name;
    }

    public @NotBlank(message = "Description is mandatory") String getDescription() {
        return description;
    }

    public void setDescription(@NotBlank(message = "Description is mandatory") String description) {
        this.description = description;
    }

    public @NotBlank(message = "Persistent data is mandatory") String getPersistentData() {
        return persistentData;
    }

    public void setPersistentData(@NotBlank(message = "Persistent data is mandatory") String persistentData) {
        this.persistentData = persistentData;
    }

    public @NotBlank(message = "Created by is mandatory") String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(@NotBlank(message = "Created by is mandatory") String createdBy) {
        this.createdBy = createdBy;
    }
}
