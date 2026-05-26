package com.recon.selfservice.model.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class ExecutionRequestDTO {
    private Long reconId;
    private LocalDate executionDate;
}
