package com.example.demo.repositories;
import com.example.demo.models.ClassificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ClassificationEntityRepository extends JpaRepository<ClassificationEntity,String> {
    List<ClassificationEntity> findByFieldNameContainingIgnoreCaseOrClassificationNameContainingIgnoreCaseOrApprovedByContainingIgnoreCase(String fieldName, String classificationName, String approvedBy);

}
