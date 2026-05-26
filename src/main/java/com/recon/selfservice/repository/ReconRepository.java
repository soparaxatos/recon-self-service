package com.recon.selfservice.repository;

import com.recon.selfservice.model.entity.Recon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReconRepository extends JpaRepository<Recon, Long> {
    List<Recon> findAllByOrderByNameAsc();
    List<Recon> findByNameContainingIgnoreCaseOrderByNameAsc(String name);
}
