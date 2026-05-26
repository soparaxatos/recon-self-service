package com.recon.selfservice.repository;

import com.recon.selfservice.model.entity.Execution;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExecutionRepository extends JpaRepository<Execution, Long> {
    Page<Execution> findByReconNameContainingIgnoreCaseOrderByExecutedAtDesc(String reconName, Pageable pageable);
    Page<Execution> findAllByOrderByExecutedAtDesc(Pageable pageable);
}
