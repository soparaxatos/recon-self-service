package com.recon.selfservice.service;

import com.recon.selfservice.model.entity.Execution;
import com.recon.selfservice.repository.ExecutionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExecutionService {

    private final ExecutionRepository executionRepository;

    public Page<Execution> findAll(String query, Pageable pageable) {
        if (query != null && !query.isBlank()) {
            return executionRepository.findByReconNameContainingIgnoreCaseOrderByExecutedAtDesc(query, pageable);
        }
        return executionRepository.findAllByOrderByExecutedAtDesc(pageable);
    }

    public Execution findById(Long id) {
        return executionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Execution not found with id: " + id));
    }
}
