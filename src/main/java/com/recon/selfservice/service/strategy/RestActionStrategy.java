package com.recon.selfservice.service.strategy;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.recon.selfservice.integration.rest.DbExtractorClient;
import com.recon.selfservice.model.dto.DBSParameterDTO;
import com.recon.selfservice.model.entity.Action;
import com.recon.selfservice.model.entity.Execution;
import com.recon.selfservice.model.enums.ActionType;
import com.recon.selfservice.model.enums.ExecutionStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@Slf4j
@RequiredArgsConstructor
public class RestActionStrategy implements ActionStrategy {

    private final DbExtractorClient dbExtractorClient;
    private final ObjectMapper objectMapper;

    @Override
    public ActionType getType() {
        return ActionType.REST;
    }

    @Override
    public void execute(Action action, Execution execution, LocalDate executionDate) {
        try {
            log.info("Executing REST action for application: {}", action.getApplication());
            
            // In a real scenario, the token would be retrieved from configuration or security context
            String token = "Bearer <future-token>";
            
            if ("db-extractor".equals(action.getApplication())) {
                DBSParameterDTO params = objectMapper.readValue(action.getParameters(), DBSParameterDTO.class);
                
                // Example of using executionDate if needed in the payload
                // params.setSql(params.getSql().replace(":date", executionDate.toString()));

                ResponseEntity<DBSParameterDTO> response = dbExtractorClient.createParameter(params, token);
                
                if (response.getStatusCode().is2xxSuccessful()) {
                    execution.setStatus(ExecutionStatus.SUCCESS);
                    execution.setResponseMessage("REST call successful");
                    // Assuming the response body might contain data or we just log success
                    execution.setResponseData("Response received successfully".getBytes());
                    execution.setContentType("text/plain");
                } else {
                    execution.setStatus(ExecutionStatus.FAILED);
                    execution.setResponseMessage("REST call failed with status: " + response.getStatusCode());
                }
            } else {
                execution.setStatus(ExecutionStatus.FAILED);
                execution.setResponseMessage("Unsupported REST application: " + action.getApplication());
            }
        } catch (Exception e) {
            log.error("Error executing REST action", e);
            execution.setStatus(ExecutionStatus.FAILED);
            execution.setResponseMessage("Error: " + e.getMessage());
        }
    }
}
