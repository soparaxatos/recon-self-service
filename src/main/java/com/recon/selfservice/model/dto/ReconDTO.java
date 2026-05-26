package com.recon.selfservice.model.dto;

import com.recon.selfservice.model.enums.ActionType;
import lombok.Data;

@Data
public class ReconDTO {
    private Long id;
    private String name;
    private String description;
    private Long actionId;
    private String application;
    private ActionType actionType;
    private String parameters;
}
