package com.recon.selfservice.service.strategy;

import com.recon.selfservice.model.entity.Action;
import com.recon.selfservice.model.entity.Execution;
import com.recon.selfservice.model.enums.ActionType;

import java.time.LocalDate;

public interface ActionStrategy {
    ActionType getType();
    void execute(Action action, Execution execution, LocalDate executionDate);
}
