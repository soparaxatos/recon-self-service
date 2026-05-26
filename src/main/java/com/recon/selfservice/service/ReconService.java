package com.recon.selfservice.service;

import com.recon.selfservice.model.entity.Action;
import com.recon.selfservice.model.entity.Execution;
import com.recon.selfservice.model.entity.Recon;
import com.recon.selfservice.model.enums.ActionType;
import com.recon.selfservice.repository.ExecutionRepository;
import com.recon.selfservice.repository.ReconRepository;
import com.recon.selfservice.service.strategy.ActionStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReconService {

    private final ReconRepository reconRepository;
    private final ExecutionRepository executionRepository;
    private final List<ActionStrategy> strategies;

    public List<Recon> findAll() {
        return reconRepository.findAllByOrderByNameAsc();
    }

    public List<Recon> search(String query) {
        if (query == null || query.isBlank()) {
            return findAll();
        }
        return reconRepository.findByNameContainingIgnoreCaseOrderByNameAsc(query);
    }

    public Recon findById(Long id) {
        return reconRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Recon not found with id: " + id));
    }

    @Transactional
    public Recon save(Recon recon) {
        return reconRepository.save(recon);
    }

    @Transactional
    public void delete(Long id) {
        reconRepository.deleteById(id);
    }

    @Transactional
    public Execution executeRecon(Long reconId, LocalDate executionDate) {
        Recon recon = findById(reconId);
        Action action = recon.getAction();

        Execution execution = Execution.builder()
                .recon(recon)
                .executedAt(LocalDateTime.now())
                .userParameters("Date: " + executionDate.toString())
                .userName(getCurrentUser()) // Stub for future security
                .build();

        ActionStrategy strategy = strategies.stream()
                .filter(s -> s.getType().equals(action.getType()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No strategy found for action type: " + action.getType()));

        strategy.execute(action, execution, executionDate);

        return executionRepository.save(execution);
    }

    private String getCurrentUser() {
        // This will be replaced by Spring Security integration
        // return SecurityContextHolder.getContext().getAuthentication().getName();
        return "System User";
    }
}
