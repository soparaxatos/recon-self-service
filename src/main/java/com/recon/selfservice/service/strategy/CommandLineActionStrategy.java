package com.recon.selfservice.service.strategy;

import com.recon.selfservice.model.entity.Action;
import com.recon.selfservice.model.entity.Execution;
import com.recon.selfservice.model.enums.ActionType;
import com.recon.selfservice.model.enums.ExecutionStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class CommandLineActionStrategy implements ActionStrategy {

    @Override
    public ActionType getType() {
        return ActionType.COMMAND_LINE;
    }

    @Override
    public void execute(Action action, Execution execution, LocalDate executionDate) {
        try {
            log.info("Executing Command Line action for application: {}", action.getApplication());
            
            String commandTemplate = action.getParameters();
            // Replace placeholder with actual date if exists in template
            String command = commandTemplate.replace("{date}", executionDate.toString());
            
            List<String> commandList = new ArrayList<>();
            commandList.addAll(Arrays.asList(command.split(" ")));

            ProcessBuilder processBuilder = new ProcessBuilder(commandList);
            processBuilder.redirectErrorStream(true);
            
            Process process = processBuilder.start();
            
            StringBuilder output = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    output.append(line).append("\n");
                }
            }
            
            int exitCode = process.waitFor();
            
            if (exitCode == 0) {
                execution.setStatus(ExecutionStatus.SUCCESS);
                execution.setResponseMessage("Command executed successfully");
                execution.setResponseData(output.toString().getBytes());
                execution.setContentType("text/plain");
            } else {
                execution.setStatus(ExecutionStatus.FAILED);
                execution.setResponseMessage("Command failed with exit code: " + exitCode);
                execution.setResponseData(output.toString().getBytes());
                execution.setContentType("text/plain");
            }
            
        } catch (Exception e) {
            log.error("Error executing Command Line action", e);
            execution.setStatus(ExecutionStatus.FAILED);
            execution.setResponseMessage("Error: " + e.getMessage());
        }
    }
}
