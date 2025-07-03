package com.example.demo.repositories;
import com.example.demo.models.ClassificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClassificationEntityRepository extends JpaRepository<ClassificationEntity,String> {
}
