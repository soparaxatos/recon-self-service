package com.recon.selfservice.model.entity;

import com.recon.selfservice.model.enums.ExecutionStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "executions")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Execution {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime executedAt;

    @Column(columnDefinition = "TEXT")
    private String userParameters;

    private String userName;

    @Enumerated(EnumType.STRING)
    private ExecutionStatus status;

    @Column(columnDefinition = "TEXT")
    private String responseMessage;

    @Lob
    @Column(columnDefinition = "BLOB")
    private byte[] responseData;

    private String contentType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recon_id")
    private Recon recon;
}
