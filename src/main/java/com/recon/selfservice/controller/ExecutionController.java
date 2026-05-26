package com.recon.selfservice.controller;

import com.recon.selfservice.model.entity.Execution;
import com.recon.selfservice.service.ExecutionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/executions")
@RequiredArgsConstructor
public class ExecutionController {

    private final ExecutionService executionService;

    @GetMapping
    public String listExecutions(
            @RequestParam(value = "query", required = false) String query,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sort", defaultValue = "executedAt") String sort,
            @RequestParam(value = "dir", defaultValue = "DESC") String dir,
            Model model) {
        
        Sort sorting = dir.equalsIgnoreCase("ASC") ? Sort.by(sort).ascending() : Sort.by(sort).descending();
        Page<Execution> executions = executionService.findAll(query, PageRequest.of(page, size, sorting));
        
        model.addAttribute("executions", executions);
        model.addAttribute("query", query);
        model.addAttribute("currentPage", page);
        model.addAttribute("sort", sort);
        model.addAttribute("dir", dir);
        
        return "execution/list";
    }

    @GetMapping("/{id}")
    public String viewExecution(@PathVariable Long id, Model model) {
        model.addAttribute("execution", executionService.findById(id));
        return "execution/view";
    }

    @GetMapping("/{id}/download")
    public ResponseEntity<byte[]> downloadResult(@PathVariable Long id) {
        Execution execution = executionService.findById(id);
        
        if (execution.getResponseData() == null) {
            return ResponseEntity.notFound().build();
        }

        String fileName = "result_" + id;
        String contentType = execution.getContentType() != null ? execution.getContentType() : "application/octet-stream";
        
        if (contentType.contains("excel") || contentType.contains("spreadsheetml")) {
            fileName += ".xlsx";
        } else if (contentType.contains("csv")) {
            fileName += ".csv";
        } else {
            fileName += ".txt";
        }

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                .contentType(MediaType.parseMediaType(contentType))
                .body(execution.getResponseData());
    }
}
