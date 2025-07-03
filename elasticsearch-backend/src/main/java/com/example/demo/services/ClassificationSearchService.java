package com.example.demo.services;

import com.example.demo.models.ClassificationIndex;
import com.example.demo.repositories.ClassificationIndexRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ClassificationSearchService {
    private final ClassificationIndexRepository repository;

    public ClassificationSearchService(ClassificationIndexRepository repository) {
        this.repository = repository;
    }

    public List<ClassificationIndex> searchAll() {
        Iterable<ClassificationIndex> iterable = repository.findAll();
        return StreamSupport.stream(iterable.spliterator(), false)
                .collect(Collectors.toList());
    }

    public Optional<ClassificationIndex> findById(String id) {
        return repository.findById(id);
    }

    public List<ClassificationIndex> searchBy(String query) {
        return repository.findByFieldNameContainingIgnoreCaseOrClassificationNameContainingIgnoreCaseOrApprovedByContainingIgnoreCaseOrModifiedByContainingIgnoringCase(
                query, query, query, query);
    }
}